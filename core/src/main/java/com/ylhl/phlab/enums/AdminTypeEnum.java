package com.ylhl.phlab.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminTypeEnum {
    ADMIN("ADMIN","机构管理员"),
    MZADMIN("MZADMIN","区域管理员"),
    REGION("REGION","街道村社管理员"),
    RAM("RAM","机构管理员"),
    CRC("CRC","日照中心管理员"),
    UNKNOWN("UNKNOWN","未知模块");

    private final String type;
    private final String msg;


    public boolean match(String type) {
        return this.type.equals(type);
    }

    public static AdminTypeEnum getEnum(String type){
        for(AdminTypeEnum bean: AdminTypeEnum.values()){
            if( bean.match(type)){
                return bean;
            }
        }
        return AdminTypeEnum.UNKNOWN;
    }
}
