package com.ylhl.phlab.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatusEnum {
    DISABLE("0","冻结"),
    ABLE("1","解冻");

    private final String code;
    private final String type;


    public boolean match(String code) {
        return this.code.equals(code);
    }

    public static UserStatusEnum getEnum(String code){
        for(UserStatusEnum userStatusEnum:UserStatusEnum.values()){
            if( userStatusEnum.match(code)){
                return userStatusEnum;
            }
        }
        return UserStatusEnum.DISABLE;
    }
}
