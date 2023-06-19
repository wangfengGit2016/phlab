package com.ylhl.phlab.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeviceStatusEnum {
    OUTLINE("0","冻结"),
    ONLINE("1","解冻");

    private final String code;
    private final String type;


    public boolean match(String code) {
        return this.code.equals(code);
    }

    public static DeviceStatusEnum getEnum(String code){
        for(DeviceStatusEnum DeviceStatusEnum:DeviceStatusEnum.values()){
            if( DeviceStatusEnum.match(code)){
                return DeviceStatusEnum;
            }
        }
        return DeviceStatusEnum.OUTLINE;
    }

}
