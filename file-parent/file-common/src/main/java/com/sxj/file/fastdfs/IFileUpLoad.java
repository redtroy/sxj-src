package com.sxj.file.fastdfs;

import java.io.File;

public interface IFileUpLoad {

	public static int LEVEL = 2;

	public static String CACHE_NAME = "FAST_DFS_CACHE";

	/**
	 * 上传文件
	 * 
	 * @param file_buff
	 *            :文件字节数组
	 * @param file_name
	 *            ：文件名
	 * @return file_Url ：文件Url
	 */
	public String uploadFile(byte[] file_buff, String file_ext_name);

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            ：文件
	 * @return file_Url ：文件Url
	 */
	public String uploadFile(File file);

	/**
	 * 修改服务器文件
	 * 
	 * @param oldfile_Url
	 *            ：原文件路径
	 * @param newfile_buff
	 *            ：新文件字节数组
	 * @param newfile_ext_name
	 *            ：新文件扩展名
	 * @return
	 */
	public String modfiyFile(String oldfile_Url, byte[] newfile_buff,
			String newfile_ext_name);

	/**
	 * 下载文件
	 * 
	 * @param file_Url
	 *            ：文件路径
	 * @return file_buff：文件字节数组
	 */
	public byte[] downloadFile(String file_Url);

	/**
	 * 获取缩略图
	 * 
	 * @param file_id
	 *            ：大图key
	 * @param width
	 *            ：宽度
	 * @param height
	 *            ：高度
	 * @return
	 */
	public byte[] getSmallImage(String file_id, int width, int height);

	/**
	 * 删除文件
	 * 
	 * @param file_Url
	 *            ：文件路径
	 * @return
	 */
	public boolean removeFile(String file_Url);

}
