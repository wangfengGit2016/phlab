package com.ylhl.phlab.utils;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

/**
 * 签名
 * @author zhengyq
 */
@Slf4j
public class SignUtil {

    /**
     * 开放平台 签名
     * @param obj 签名对象
     * @return
     */
    public static String sign(Object obj,String secretKey){
        JSONObject data = (JSONObject) JSONObject.toJSON(obj);
        data.remove("sign");
        StringBuilder signStr = new StringBuilder();
        data.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e->{
            if(e.getValue()==null||"".equals(e.getValue())){
                return;
            }
            signStr.append(e.getKey());
            signStr.append("=");
            signStr.append(e.getValue());
            signStr.append("&");
        });
        signStr.append("key=");
        signStr.append(secretKey);
        log.info(signStr.toString());
        log.info(SecureUtil.md5(signStr.toString()));
        return SecureUtil.md5(signStr.toString());
    }

    public static String smsSign( JSONObject data){
        data.remove("mac");
        StringBuilder signStr = new StringBuilder();
        signStr.append(data.getString("ecName"));
        signStr.append(data.getString("apId"));
        signStr.append(data.getString("secretKey"));
        data.getJSONObject("mobiles").getJSONArray("string").forEach(signStr::append);
        signStr.append(data.getString("content"));
        signStr.append(data.getString("sign"));
        signStr.append(data.getString("addSerial"));
        return SecureUtil.md5(signStr.toString());
    }

    public static  String signV3(JSONObject data, PrivateKey secretKey) {
        try {
            StringBuilder builder = new StringBuilder();
            data.forEach((key, value) -> builder.append(value).append("\\n"));
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(secretKey);
            sign.update(builder.toString().getBytes());
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey loadPrivateKey(String privateKey) {
        privateKey = privateKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }
}
