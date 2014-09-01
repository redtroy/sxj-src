package com.sxj.file.fastdfs;

import java.net.InetSocketAddress;

import org.csource.fastdfs.TrackerGroup;

public class FileGroup {

	public static TrackerGroup imgGroup;

	public static TrackerGroup fileGroup;

	static {
		imgGroup = new TrackerGroup(
				new InetSocketAddress[] { new InetSocketAddress(
						"192.168.1.13", 22122) });
	}

}
