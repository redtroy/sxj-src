package com.sxj.file.fastdfs;

import java.net.InetSocketAddress;

import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;

public class UpLoadFactory {

	// public static Integer IMAGE_MAX_WIDTH;

	// public static Integer IMAGE_MAX_HEIGHT;

	private static FastDFSImpl fileUpload;

	private static IFileUpLoad imageUpload;

	// static {
	// IMAGE_MAX_WIDTH = new Integer(
	// MessageUtils.getMessage("image_max_width"));
	// IMAGE_MAX_HEIGHT = new Integer(
	// MessageUtils.getMessage("image_max_heigth"));
	//
	// }

	public static IFileUpLoad buildFileUpLoad() {
		if (fileUpload == null) {
			fileUpload = new FastDFSImpl();
			TrackerGroup group = new TrackerGroup(
					new InetSocketAddress[] { new InetSocketAddress(
							"192.168.1.13", 22122) });
			TrackerClient tracker = new TrackerClient(group);
		}
		return fileUpload;
	}

	//
	// public static IFileUpLoad buildImageUpLoad() {
	// if (imageUpload == null) {
	// imageUpload = new FastDFSImpl(FileGroup.imgGroup);
	// }
	// return imageUpload;
	// }

	// public static String getHost(String webUrl, String path, Integer width,
	// Integer height) {
	// // path=path.replace("/", "-");
	// String[] paths = path.split("[.]");
	// if (width == null || height == null) {
	// String host = webUrl + path;
	// return host;
	// }
	// if (width <= 0 || height <= 0) {
	// String host = webUrl + path;
	// return host;
	// }
	// String host = webUrl + path + width + "x" + height + "."
	// + paths[paths.length - 1];
	// return host;
	// }

	public static void main(String[] args) {
		// String aaa = UpLoadFactory.getHost("www.333.com",
		// "img/M00/00/00/wKgSdkxGh9MAAAAAAANQ8E7o-1c364.jpg", 100, 100);
		// System.out.println(aaa);
	}
}
