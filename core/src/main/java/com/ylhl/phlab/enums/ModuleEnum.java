package com.ylhl.phlab.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ModuleEnum {
    SYS_USER("sys","user","系统用户"),
    SYS_dict("sys","dict","系统字典"),
    SYS_file("sys","file","系统文件"),
    SYS_menu("sys","menu","系统菜单"),
    SYS_region("sys","region","系统区域"),
    SYS_role("sys","role","系统角色"),
    RAM_ORGAN("ram","organ","转介转养机构"),
    EWS_DEVICE("ews","device","智能设备"),
    EWS_REPAIR("ews","repair","智能设备维修"),
    EWS_RULE("ews","rule","预警规则"),
    EWS_USER("ews","user","预警人员"),
    EWS_WARN("ews","warn","预警信息"),
    ESR_USER("esr","user","电子津贴人员"),
    ESR_RECORD("esr","record","电子津贴使用记录"),
    CVE_USER("cve","user","关爱探访人员"),
    CVE_TASK("cve","task","关爱探访任务"),
    CVE_RECORD("cve","task","关爱探访记录"),
    CRC_ORGAN("crc","organ","日照中心机构"),
    CRC_OPERATE("crc","operate","日照中心运营资料"),
    CRC_FUND("crc","fund","日照中心资金"),
    CFR_USER("cfr","user","节日慰问金人员"),
    CFR_INVENTORY("cfr","inventory","节日慰问金清单"),
    UNKNOWN("UNKNOWN","UNKNOWN","未知模块");

    private final String code;
    private final String type;
    private final String msg;

    public static String getModule(String path){
        if(path.contains("/")){
            for(ModuleEnum bean: ModuleEnum.values()){
                if( bean.match(path.split("/")[1])){
                    return bean.msg;
                }
            }
        }
        return ModuleEnum.UNKNOWN.msg;
    }


    public boolean match(String code) {
        return this.code.equals(code);
    }

    public static ModuleEnum getEnum(String code){
        for(ModuleEnum bean: ModuleEnum.values()){
            if( bean.match(code)){
                return bean;
            }
        }
        return ModuleEnum.UNKNOWN;
    }
}
