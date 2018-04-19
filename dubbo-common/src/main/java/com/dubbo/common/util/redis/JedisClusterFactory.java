package com.dubbo.common.util.redis;

import com.dubbo.common.util.StringUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-17 20:05
 * @version:
 **/
public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean {

    public static final Logger logger = LoggerFactory.getLogger(JedisClusterFactory.class);

    private String redisNodes;                            // redis节点地址
    private JedisCluster jedisCluster;
    private Integer timeout;
    private Integer maxRedirections;
    private GenericObjectPoolConfig genericObjectPoolConfig;

    @Override
    public JedisCluster getObject() throws Exception {
        return jedisCluster;
    }

    @Override
    public Class<?> getObjectType() {
        return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<HostAndPort> haps = this.parseHostAndPort();
        logger.debug("初始化所有redis===================================");
        jedisCluster = new JedisCluster(haps, timeout, maxRedirections, genericObjectPoolConfig);
    }


    private Set<HostAndPort> parseHostAndPort() throws Exception {
        try {
            if (StringUtil.isBlank(redisNodes)) {
                throw new Exception("redis初始化异常，redisNodes不能为空！");
            }
            Set<HostAndPort> haps = new HashSet<>();
            String[] nodes = redisNodes.split(";");
            for (String node : nodes) {
                String[] ipAndPort = node.split(":");
                HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
                haps.add(hap);
            }
            return haps;
        }catch (IllegalArgumentException e) {
            throw e;
        }catch (Exception e){
            throw new Exception("解析 jedis 配置文件失败", e);
        }
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setMaxRedirections(int maxRedirections) {
        this.maxRedirections = maxRedirections;
    }

    public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
        this.genericObjectPoolConfig = genericObjectPoolConfig;
    }

    public void setRedisNodes(String redisNodes) {
        this.redisNodes = redisNodes;
    }

}
