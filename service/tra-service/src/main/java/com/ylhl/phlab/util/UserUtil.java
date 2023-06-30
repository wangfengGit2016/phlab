package com.ylhl.phlab.util;

import cn.dev33.satoken.stp.StpUtil;
import com.ylhl.phlab.domain.SysUserInfo;
import com.ylhl.phlab.mapper.CoreBuilder;

public class UserUtil {
    private static String userId;
    public static String userId() {
        if (userId == null) {
            userId = (String) StpUtil.getLoginId();
        }
        return userId;
    }
    public static String userName() {
        SysUserInfo userInfo = CoreBuilder.select().eq("id", userId()).oneT(SysUserInfo.class);
        return userInfo.getRealName();
    }
}
