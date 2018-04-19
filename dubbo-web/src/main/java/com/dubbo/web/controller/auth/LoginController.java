package com.dubbo.web.controller.auth;

import com.dubbo.api.auth.constant.UserStat;
import com.dubbo.api.auth.dto.SysUserDto;
import com.dubbo.api.auth.service.SysUserService;
import com.dubbo.api.constant.ReturnCode;
import com.dubbo.common.dto.BaseReturnDto;
import com.dubbo.common.util.MD5Util;
import com.dubbo.common.util.StringUtil;
import com.dubbo.web.util.WebUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 15:52
 * @version:
 **/

@Controller
@RequestMapping("/auth")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    @Lazy
    private SysUserService sysUserService;

    /**
     * 系统登陆
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam(value = "loginName") String loginName,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "authCode", required = false) String authCode,
                        HttpServletRequest request) {

        BaseReturnDto<SysUserDto> brd = null;
        UsernamePasswordToken token = null;
        Subject currentUser = SecurityUtils.getSubject();
        try {
            String sessionAuthCode = (String) SecurityUtils.getSubject().getSession().getAttribute("authCode");
            SysUserDto user = WebUtil.getLoginUser();
            // 校验验证码
            logger.info("------shiro timeout------- " + sessionAuthCode + authCode);
            if (StringUtil.isBlank(sessionAuthCode) || !sessionAuthCode.equalsIgnoreCase(authCode)) {
                brd = new BaseReturnDto<>(ReturnCode.ACCOUNT.getCode(), "密码或验证码不正确，请重新输入!");// 账号问题,验证码错误
                return brd;
            }

            token = new UsernamePasswordToken(loginName.trim(), MD5Util.encryptionStr(password.trim(), true));

            // 需要重新登陆情况
            if (!currentUser.isAuthenticated() || null == user || !loginName.equals(user.getLoginName())) {
                currentUser.login(token);
                user = sysUserService.getSysUserByLoginName(loginName);
                logger.info("------isAuthenticated------- " + user);
            }

            logger.info("------shiro timeout------- " + currentUser.getSession().getTimeout());
            if (UserStat.FREEZE.getStat().equals(user.getState())) {// 用户账号冻结中
                brd = new BaseReturnDto<>(ReturnCode.ACCOUNT_FREEZE.getCode(), "账号被冻结，无法登陆!");// 账号问题,账号被冻结
                currentUser.logout();// 移除已认证通过的session
                return brd;
            }

            String requestIp = WebUtil.getRequestIp(request);
            user.setLastLoginIp(requestIp);
            user.setLastLoginTime(new Date());
            user.setState(UserStat.ACTIVE.getStat());
            sysUserService.updateLoginInfo(user);
            WebUtil.setSessionUser(user);
            brd = new BaseReturnDto<>(ReturnCode.SUCCESS.getCode(), "login success");
            user.setPassword(null);
            brd.setData(user);
            logger.info("success login with loginName:{}", loginName);

        } catch (AuthenticationException e) {
            brd = new BaseReturnDto<>(ReturnCode.ACCOUNT.getCode(), "密码或验证码不正确，请重新输入!");
            logger.error("user doesn't exist with loginName:{}", loginName, e);
        } catch (Exception e) {
            brd = new BaseReturnDto<>(ReturnCode.ACCOUNT.getCode(), "登录失败！");
            logger.error(MessageFormat.format("fail login with loginName:{0}", loginName), e);
            if (currentUser.isAuthenticated()) {
                currentUser.logout();
            }

        }
        return brd;
    }


    /**
     * 系统登出
     *
     * @return
     */
    @RequestMapping(value = "/loginOut.do", method = RequestMethod.POST)
    @ResponseBody
    public Object loginOut() {
        BaseReturnDto<Void> brd = null;
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (null != currentUser) {
                currentUser.logout();
            }
            brd = new BaseReturnDto<>(ReturnCode.SUCCESS.getCode(), "loginOut success");
            logger.info("success login with dto : {}", "");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            brd = new BaseReturnDto<>(ReturnCode.ACCOUNT.getCode(), "loginOut fail");
        }
        return brd;
    }

    /**
     * 生成验证码
     */
    @RequestMapping(value = "/authCode.do", method = RequestMethod.GET)
    @ResponseBody
    public void getAuthCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int width = 63;
        int height = 37;
        char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9'};
        Random random = new Random();
        // 设置response头信息
        // 禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        // 产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        // Graphics类的样式
        g.setColor(this.getRandColor(200, 250));
        g.setFont(new Font("Times New Roman", 0, 28));
        g.fillRect(0, 0, width, height);
        // 绘制干扰线
        for (int i = 0; i < 40; i++) {
            g.setColor(this.getRandColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        // 绘制字符
        StringBuilder authCode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(codeSequence[random.nextInt(32)]);
            authCode = authCode.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 28);
        }
        logger.info("------before authCode------- " + SecurityUtils.getSubject().getSession().getAttribute("authCode")
                + SecurityUtils.getSubject().getSession().getAttribute("authCode"));

        // 将字符保存到session中用于前端的验证
        SecurityUtils.getSubject().getSession().setAttribute("authCode", authCode.toString());

        logger.info("------end authCode------- " + authCode + authCode);

        g.dispose();

        ImageIO.write(image, "JPEG", response.getOutputStream());
        response.getOutputStream().flush();

    }

    // 创建颜色
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
