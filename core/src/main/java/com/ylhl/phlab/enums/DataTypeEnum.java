package com.ylhl.phlab.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DataTypeEnum {

    INT("int","Integer"),
    VARCHAR("varchar","String"),
    DATETIME("datetime","Date"),
    BLOB("blob","byte[]");

    private final String code;
    private final String type;


    public boolean match(String code) {
        return this.code.equals(code);
    }

    public static DataTypeEnum getEnum(String code){
        for(DataTypeEnum dataTypeEnum:DataTypeEnum.values()){
            if( dataTypeEnum.match(code)){
                return dataTypeEnum;
            }
        }
        return DataTypeEnum.VARCHAR;
    }
}
