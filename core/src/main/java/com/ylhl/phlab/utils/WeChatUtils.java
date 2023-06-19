package com.ylhl.phlab.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class WeChatUtils {


	public static String SNS_TOKEN="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";
	public static String SNS_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	public static String CGI_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static String CGI_USERINFO = "https://api.weixin.qq.com/cgi-bin/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	public static String CGI_BINDTAG = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=";
	//获取openID
	public static String getOpenId(String appId,String secret,String code) throws Exception {
		String url=SNS_TOKEN.replaceAll("APPID", appId)
							.replaceAll("APPSECRET", secret)
							.replaceAll("CODE", code);
		return HttpUtils.doGet(url);
	}

	//获取令牌
	public static String getAccessToken(String appId,String appSecret) throws Exception {
		String url=SNS_TOKEN.replaceAll("APPID", appId).replaceAll("APPSECRET", appSecret);
		return HttpUtils.doGet(url);
	}
	//获取用户信息
	public static String getUserInfo(String accessToken,String openId) throws Exception {
		String url=CGI_USERINFO.replaceAll("ACCESS_TOKEN", accessToken).replaceAll("OPENID", openId);
		return HttpUtils.doGet(url);
	}

	//绑定标签
	public static void bindTag(String accessToken,String tagId,List<String> userList) {
		JSONObject data =new JSONObject();
		data.put("tagId", tagId);
		data.put("openid_list", userList);
	}
	//解绑标签
	public static void unbindTag(String accessToken,String tagId,List<String> userList) {
		JSONObject data =new JSONObject();
		data.put("tagId", tagId);
		data.put("openid_list", userList);
	}

}
