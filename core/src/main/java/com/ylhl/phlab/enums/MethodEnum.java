package com.ylhl.phlab.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MethodEnum {
    CREATE("create","0","创建"),
    DELETE("delete","0","删除"),
    UPDATE("update","0","修改"),
    DISABLE("disable","0","冻结解冻"),
    UNKNOWN("UNKNOWN","UNKNOWN","未知操作");

    private final String code;
    private final String type;
    private final String msg;


    public boolean match(String code) {
        return this.code.equals(code);
    }

    public static MethodEnum getEnum(String code){
        for(MethodEnum bean: MethodEnum.values()){
            if( code.contains(bean.getCode())){
                return bean;
            }
        }
        return MethodEnum.UNKNOWN;
    }
}
