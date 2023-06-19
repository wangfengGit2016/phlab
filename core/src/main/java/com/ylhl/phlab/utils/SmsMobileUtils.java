package com.ylhl.phlab.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * @author zhengyq
 * @date 2022/3/29 14:45
 * 中国移动短信
 *
 */

@Slf4j
public class SmsMobileUtils {

    public static JSONObject doPhoneList(List<JSONObject> list){
        JSONObject res =new JSONObject();
        JSONObject header =new JSONObject();
        header.put("X-Trans-Id","ZJLP_XFMZ"+ IdUtil.objectId());
        header.put("X-Channel-Id","ZJLP_XFMZ");
        header.put("Accept-Charset","UTF-8");
        JSONObject content = new JSONObject();
        content.put("sysId","zjytck");
        content.put("provinceId","00030023");
        content.put("campaignId","23061418114602979512");
        list.forEach(item->{
            JSONObject dto =new JSONObject();
            dto.put("phoneNum",item.getString("phone"));
            dto.put("fileHead","号码,工单号,模板ID,播放内容");
            dto.put("attr",item.getString("phone")+","+item.getString("taskId")+","+item.getString("smsId")+","+item.getString("content"));
            dto.put("returnField","~ZJLPXFMZ@"+item.getString("taskId"));
            dto.put("expireTime","");
            item.clear();
            item.putAll(dto);
        });
        content.put("beans",list);
        content.put("object",null);
        JSONObject params = new JSONObject();
        params.put("params",content);
        System.out.println(params.toJSONString());
        String result=HttpUtils.doPost("http://111.2.189.55:8000/ngcrmpfcore_zj/csf/business/sampleImport",header,params.toString());
        res.putAll(JSONObject.parseObject(result));
        return res;
    }

    public static JSONObject doPhone(JSONObject param){
        return doPhoneList(Lists.newArrayList(param));
    }

    /**
     * 业务执行
     * @param param 业务参数
     * @return 业务数据
     */
    public static JSONObject runService(JSONObject param) {
        JSONObject header =new JSONObject();
        header.put("Accept-Charset","UTF-8");
        JSONObject content = new JSONObject();
        content.put("ecName","杭州临平大数据经营有限公司");
        content.put("apId","LPMZYL");
        //手机号，多个以逗号分隔实现单对多
        content.put("mobiles",param.getString("phone"));
        //文本内容，以JSON{key：value}实现多对多模式
        content.put("content",param.getString("content"));
        content.put("sign","KQ2lUwP7V");
        content.put("addSerial","");
        StringBuilder signStr  =new StringBuilder();
        signStr.append(content.getString("ecName"));
        signStr.append(content.getString("apId"));
        signStr.append("Elkj@2021");
//        signStr.append("MS@w@9Z5");
        signStr.append(content.getString("mobiles"));
        signStr.append(content.getString("content"));
        signStr.append(content.getString("sign"));
        signStr.append(content.getString("addSerial"));
        content.put("mac", SecureUtil.md5(signStr.toString()).toLowerCase(Locale.ROOT));
        String retStr = HttpUtils.doPost("http://112.33.46.17:37891/sms/norsubmit",header,Base64.encode(content.toJSONString().getBytes(StandardCharsets.UTF_8)));
        JSONObject res =new JSONObject();
        if(StringUtils.isNotBlank(retStr)&&retStr.startsWith("{")){
            res.putAll(JSONObject.parseObject(retStr));
        }
        return res;
    }
}
