package com.sxj.supervisor.quartz;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.member.MemberEntity;

public class TestHttpClient {
	public static void main(String[] args) {
		try {
			String loginUrl = "http://www.menchuang.org.cn/purchase/syncMember.htm";
			MemberEntity memberEntity = new MemberEntity();
			memberEntity.setName("南京市测试厂");
			memberEntity.setAddress("仙林大道");
			String json = JsonMapper.nonDefaultMapper().toJson(memberEntity);
			String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
			requestPost(loginUrl, encoderJson);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void requestPost(String url, String encoderJson)
			throws ClientProtocolException, IOException {

		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		HttpPost httppost = new HttpPost(url);
		StringEntity se = new StringEntity(encoderJson);
		se.setContentType("text/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json"));
		httppost.setEntity(se);
		CloseableHttpResponse response = httpclient.execute(httppost);
		System.out.println(response.toString());

		HttpEntity entity = response.getEntity();
		String jsonStr = EntityUtils.toString(entity, "utf-8");
		System.out.println(jsonStr);

		httppost.releaseConnection();
	}

}