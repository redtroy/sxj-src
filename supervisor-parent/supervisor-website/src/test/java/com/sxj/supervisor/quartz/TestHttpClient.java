package com.sxj.supervisor.quartz;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.member.MemberEntity;

public class TestHttpClient {
	
	public static void main(String[] args) {
		try {
			String loginUrl = "http://www.menchuang.org.cn:8080/supervisor-website/purchase/syncMember.htm";
			MemberEntity memberEntity = new MemberEntity();
			memberEntity.setName("南京市测试厂");
			memberEntity.setAddress("仙林大道");
			String json = JsonMapper.nonDefaultMapper().toJson(memberEntity);
			String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
			requestPost(loginUrl, encoderJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void requestPost(String url, String encoderJson) throws ClientProtocolException, IOException {

		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httppost = new HttpPost(url);
//		List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
//        nvps.add(new BasicNameValuePair("json", encoderJson));  
//        httppost.setEntity(new UrlEncodedFormEntity(nvps));  
		StringEntity se = new StringEntity(encoderJson);
		se.setContentType("text/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json"));
		httppost.setEntity(se);
		
//		 StringEntity entity = new StringEntity(encoderJson.toString(),"utf-8");//解决中文乱码问题    
//         entity.setContentEncoding("UTF-8");    
//         entity.setContentType("text/json");  
//         httppost.setEntity(entity);    
		CloseableHttpResponse response = httpclient.execute(httppost);
		System.out.println(response.toString());

		httppost.releaseConnection();
	}

}