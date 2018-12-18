package com.example.test.util;


import jcifs.util.LogStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PingUtils {
	private static LogStream log = LogStream.getInstance();  //打印日志

	public static void ping(String ip) {

		// 方法一 最常用的 PING 方法
		Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
		Process process = null; //声明处理类对象
		String line = null; //返回行信息
		InputStream is = null; //输入流
		InputStreamReader isr = null;// 字节流
		BufferedReader br = null;

		boolean res = false;// 结果
		try {
			process = runtime.exec("ping " + ip); // PING

			is = process.getInputStream(); // 实例化输入流
			isr = new InputStreamReader(is);// 把输入流转换成字节流
			br = new BufferedReader(isr);// 从字节中读取文本
			while ((line = br.readLine()) != null) {
				if (line.contains("TTL")) {
					res = true;
					break;
				}
			}
			is.close();
			isr.close();
			br.close();
			if (res) {
				System.out.println("1-ping success  ...");
				log.println("ping success  ...");
			} else {
				System.out.println("2-ping failed...");
				log.println("ping failed  ...");
			}
		} catch (IOException e) {
			System.out.println(e);
			runtime.exit(1);
		}
	}

}
