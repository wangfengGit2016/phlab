package com.ylhl.phlab.utils;

import cn.hutool.json.XML;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import javax.xml.soap.*;
import java.net.URL;

/**
 * @author zhengyq
 * @date 2022/3/29 14:45
 * 华为短信
 *
 */

@Slf4j
public class SmsHuaWeiUtils {

    /**
     * 业务执行
     * @param param 业务参数
     * @return 业务数据
     */
    public static JSONObject runService(JSONObject param) {
        JSONObject res =new JSONObject();
        try {
            SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = soapConnFactory.createConnection();
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage();
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("ser","http://server.webservice.service.mgw.mascloud.umpay.com/");
            SOAPBody body = envelope.getBody();
            SOAPElement bodyElement = body.addChildElement("sendSms","ser");
            JSONObject req = new JSONObject();
            req.put("apId","SSZFB");
            req.put("secretKey","elkj@ZFB123");
            req.put("ecName","杭州市第三人民医院");
            JSONObject data = new JSONObject();
            data.put("string",JsonUtils.getList(param,"list"));
            req.put("mobiles",data);
            req.put("content",param.getString("content"));
            req.put("sign","iHMYCk1dN");
            req.put("addSerial","");
            req.put("mac", SignUtil.smsSign(req));
            String xml = XmlUtils.toXml(req,"WsSubmitReq");
            bodyElement.addChildElement("arg0").setValue("<![CDATA["+xml+"]]>");
            message.getMimeHeaders().removeAllHeaders();
            URL url = new URL("http://112.35.10.201:1999/smsservice");
            SOAPMessage reply = connection.call(message, url);
            SOAPBody ycBody = reply.getSOAPBody();
            log.info(ycBody.getTextContent());
            log.info(String.valueOf(XML.toJSONObject(ycBody.getTextContent(),true)));
            res.putAll(XML.toJSONObject(ycBody.getTextContent(),true));
            log.info(res.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        return res;
    }
}
