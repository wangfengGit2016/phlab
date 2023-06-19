package com.ylhl.phlab.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageEnum {
    CRC_ORGAN("CRC","ORGAN","新增日照机构"),
    CRC_OPERATE("CRC","OPERATE","新增运营资料"),
    CRC_FUND("CRC","FUND","新增资金申请"),
    EWS_WARN("EWS","WARN","新增设备预警信息"),
    CFR_EXCEPTION("CRF","EXCEPTION","新增节日慰问金异常信息"),
    UNKNOWN("UNKNOWN","UNKNOWN","未知模块");

    private final String code;
    private final String type;
    private final String msg;


    public boolean match(String type) {
        return this.type.equals(type);
    }

    public static MessageEnum getEnum(String type){
        for(MessageEnum bean: MessageEnum.values()){
            if( bean.match(type)){
                return bean;
            }
        }
        return MessageEnum.UNKNOWN;
    }
}
