package com.ylhl.phlab.utils;

import javax.xml.soap.*;
import java.io.IOException;

/**
 * @author zhengyq
 * @date 2022/8/24 16:51
 */
public class SoapUtils {


    public static SOAPBody call(SOAPMessage message,String url) throws SOAPException, IOException {
        SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnFactory.createConnection();
//        message.writeTo(System.out);
//        System.out.println();
//        message.getMimeHeaders().removeAllHeaders();
        SOAPMessage reply = connection.call(message,url);
        reply.writeTo(System.out);
        System.out.println();
        return reply.getSOAPBody();
    }

}
