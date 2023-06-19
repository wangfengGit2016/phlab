package com.ylhl.phlab.utils;

import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.XML;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.exception.ServiceException;
import io.netty.util.internal.ResourcesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyStore;

@Slf4j
public class HttpUtils {

	public static URI getUri(String url,JSONObject data) throws Exception {
		URIBuilder builder = new URIBuilder(url);
		data.forEach((key, value) -> builder.addParameter(key, (String) value));
		return builder.build();
	}

	public static String doGet(String url) throws Exception {
		URL httpUrl = new URL(url);
		URLConnection open = httpUrl.openConnection();
		InputStream input = open.getInputStream();
		BufferedReader reader =new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = reader.readLine()) != null) {
			sb.append(s);
		}
		reader.close();
		return sb.toString();
	}

	public static String doPost(String url,JSONObject header,String content){
		try {
			HttpClientBuilder httpClientBuilder=HttpClientBuilder.create();
			httpClientBuilder.setConnectionManager(null);
			HttpClient httpClient = httpClientBuilder.build();
			HttpPost httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
			httpPost.setConfig(requestConfig);
			StringEntity postEntity = new StringEntity(content, "UTF-8");
			if(content.startsWith("{")){
				httpPost.addHeader("Content-Type", "application/json");
			}
			if(content.startsWith("<")){
				httpPost.addHeader("Content-Type", "text/xml");
			}
			header.forEach((k,v)->httpPost.addHeader(k, (String) v));
			httpPost.setEntity(postEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			String result = EntityUtils.toString(httpEntity, "UTF-8");
			log.info("出参:\n"+result);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			log.info("出参:\n"+e.getMessage());
		}
		return null;
	}



	public static JSONObject getJSON(String url,JSONObject data){
		try {
			String authorization = data.getString("Authorization");
			data.remove("Authorization");
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet httpGet = new HttpGet(getUri(url,data));
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
			httpGet.setConfig(requestConfig);
			httpGet.addHeader("Accept", "application/json");
			httpGet.addHeader("Content-Type", "application/json");
			httpGet.addHeader("User-Agent", "JAVA");
			httpGet.addHeader("Authorization", authorization);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String result =EntityUtils.toString(httpEntity, "UTF-8");
			log.info("出参:\n"+result);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}

	public static BasicHttpClientConnectionManager connectionManager(String mchId, String path) throws Exception {
		if(StringUtils.isNotBlank(mchId)){
			InputStream inputStream;
			if(path.startsWith("http")){
				URL url = new URL(path);
				inputStream = url.openStream();
			}else {
				inputStream = Files.newInputStream(ResourcesUtil.getFile(HttpUtils.class, path).toPath());
			}
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(inputStream, mchId.toCharArray());
			SSLSocketFactory sslSocketFactory = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build().getSocketFactory();
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslSocketFactory,new DefaultHostnameVerifier());
			RegistryBuilder<ConnectionSocketFactory> registryBuilder=RegistryBuilder.<ConnectionSocketFactory>create();
			registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory());
			registryBuilder.register("https", sslConnectionSocketFactory);
			return new BasicHttpClientConnectionManager(registryBuilder.build());
		}
		return null;
	}
	public static BasicHttpClientConnectionManager connectionManager(String mchId, byte[] cert) throws Exception {
		if(StringUtils.isNotBlank(mchId)){
			InputStream inputStream = new ByteArrayInputStream(cert);
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(inputStream, mchId.toCharArray());
			SSLSocketFactory sslSocketFactory = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build().getSocketFactory();
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslSocketFactory,new DefaultHostnameVerifier());
			RegistryBuilder<ConnectionSocketFactory> registryBuilder=RegistryBuilder.<ConnectionSocketFactory>create();
			registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory());
			registryBuilder.register("https", sslConnectionSocketFactory);
			return new BasicHttpClientConnectionManager(registryBuilder.build());
		}
		return null;
	}


	public static JSONObject postXML(String url,JSONObject data){
		return postXML(url,data,null);
	}

	public static JSONObject postXML(String url,JSONObject data,Object obj){
		try {
			if(!url.startsWith("http")){
				url="https://api.mch.weixin.qq.com"+url;
			}
			HttpClientConnectionManager connectionManager;
			if(obj instanceof byte[]){
				connectionManager = connectionManager(data.getString("mch_id"),(byte[])obj);
			}else if(obj instanceof String){
				connectionManager = connectionManager(data.getString("mch_id"),(String)obj);
			}else {
				connectionManager=null;
			}
			return JSONObject.parseObject(XML.toJSONObject(postXMLStr(url,data,connectionManager)).getStr("xml"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

	}

	public static String postXMLStr(String url,JSONObject data,HttpClientConnectionManager connManager){
		try {
			HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();
			HttpPost httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
			httpPost.setConfig(requestConfig);
			String xml = XmlUtil.mapToXmlStr(data,"xml");
			log.info("入参:\n"+xml);
			StringEntity postEntity = new StringEntity(xml, "UTF-8");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(postEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			return  EntityUtils.toString(httpEntity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	//获取IP地址
	public static String getIp(HttpServletRequest request) {
		try {
			String ip = request.getHeader("X-Forwarded-For");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
				ip = InetAddress.getLocalHost().getHostAddress();
			}
			return ip.split(",")[0];
		} catch (Exception e) {
			return "";
		}
	}


}
