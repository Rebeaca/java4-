package com.wmm.tcpDemo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class tcpClient {
	/*
	 * 1.客户端 
	 *创建socket连接服务器端（指定ip，端口号）通过ip地址找对应服务器
	 *调用socket的getInputStream()和getOutputStream()方法获取和客户端相连的IO流
	 *输入流可以读取客户端输出流写出的数据
	 *输出流可以写出数据到客户端的输入流
	 */
	public static void main(String[] args) throws IOException, IOException {
		//demo1();
		Socket socket = new Socket("192.168.1.105", 11111);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream ps = new PrintStream(socket.getOutputStream());

		System.out.println(br.readLine());
		ps.println("我想报名就业班");
		System.out.println(br.readLine());
		ps.println("算了，不学了");
		
		socket.close();
	}

	private static void demo1() throws UnknownHostException, IOException {
		Socket socket = new Socket("192.168.1.105", 11111);
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();

		byte[] arr = new byte[1024];
		int len = is.read(arr);
		System.out.println(new String(arr, 0, len));

		os.write("哈哈哈哈竟说实话".getBytes());

		socket.close();
	}

}
