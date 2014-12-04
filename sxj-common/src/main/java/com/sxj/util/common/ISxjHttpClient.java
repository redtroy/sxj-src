package com.sxj.util.common;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public interface ISxjHttpClient {

	/**
	 * HTTP Get
	 * <p/>
	 * 响应内容实体采用<code>UTF-8</code>字符集
	 * 
	 * @param url
	 *            请求url
	 * @return 响应内容实体
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String get(String url) throws ClientProtocolException, IOException;

	/**
	 * HTTP Post
	 * 
	 * @param url
	 *            请求url
	 * @param params
	 *            请求参数
	 * @return 响应内容实体
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String post(String url, Map<String, String> params)
			throws ClientProtocolException, IOException;

	/**
	 * HTTP Post XML（使用默认字符集）
	 * 
	 * @param url
	 *            请求的URL
	 * @param xml
	 *            XML格式请求内容
	 * @return 响应内容实体
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String postXml(String url, String xml)
			throws ClientProtocolException, IOException;

	/**
	 * HTTP Post JSON（使用默认字符集）
	 * 
	 * @param url
	 *            请求的URL
	 * @param json
	 *            JSON格式请求内容
	 * @return 响应内容实体
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String postJson(String url, String json)
			throws ClientProtocolException, IOException;

	/**
	 * SSLGET
	 * 
	 * @param url
	 * @param params
	 * @param keyType
	 * @param keyPath
	 * @param keyPassword
	 * @param sslport
	 * @param authString
	 * @return
	 * @throws Exception
	 */

	public String sslGet(String url, String authString) throws Exception;

	/**
	 * SSLPOST
	 * 
	 * @param url
	 * @param params
	 * @param authString
	 * @return
	 * @throws Exception
	 */
	public String sslPost(String url, Map<String, String> params,
			String authString) throws Exception;

	/**
	 * SSL Post XML（使用默认字符集）
	 * 
	 * @param url
	 *            请求的URL
	 * @param xml
	 *            XML格式请求内容
	 * @param keyType
	 *            密钥类型
	 * @param keyPath
	 *            密钥文件路径
	 * @param keyPassword
	 *            密钥文件密码
	 * @param sslPort
	 *            SSL端口
	 * @param authString
	 *            头部认证信息
	 * @return 响应内容实体
	 * @throws Exception
	 */
	public String sslPostXml(String url, String xml, String authString)
			throws Exception;

	/**
	 * SSL Post JSON（使用默认字符集）
	 * 
	 * @param url
	 *            请求的URL
	 * @param json
	 *            JSON格式请求内容
	 * @param keyType
	 *            密钥类型
	 * @param keyPath
	 *            密钥文件路径
	 * @param keyPassword
	 *            密钥文件密码
	 * @param sslPort
	 *            SSL端口
	 * @param authString
	 *            头部认证信息
	 * @return 响应内容实体
	 * @throws Exception
	 */
	public String sslPostJson(String url, String json, String authString)
			throws Exception;
}
