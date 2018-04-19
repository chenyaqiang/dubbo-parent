package com.dubbo.api.auth.constant;

/**
 * @Description:用户状态
 * @Author: wb
 * @CreateDate: 2018-04-18 10:06
 * @version:
 **/
public enum UserStat {

    // 未完善
    IMPERFECTION(0),
    // 冻结
    FREEZE(1),
    // 激活
    ACTIVE(2);

    private Integer stat;

    /**
     * @param stat
     */
    private UserStat(Integer stat){
        this.stat = stat;
    }

    public Integer getStat() {
        return stat;
    }
}
