package com.wmm.tcpDemo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class tcpServer {
	/*
	 * 2.服务器端 创建serversocket（指定端口号）
	 * 调用serversocket的accept()方法接收一个客户端请求，得到一个socket
	 * 调用socket的getInputStream()和getOutputStream()方法获取和客户端相连的IO流
	 * 输入流可以读取客户端输出流写出的数据 输出流可以写出数据到客户端的输入流
	 */
	public static void main(String[] args) throws IOException {
		//demo1();
		demo2();
		//多线程
		ServerSocket server = new ServerSocket(11111);
		while(true) {
			Socket socket = server.accept();
			new Thread(){
				PrintStream ps;
				BufferedReader br;
				public void run() {
					try {
						br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						ps = new PrintStream(socket.getOutputStream());

						ps.println("欢迎来到这里");
						System.out.println(br.readLine());
						ps.println("不好意思，人员已经满了里");
						System.out.println(br.readLine());
						
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				};
			}.start();
		}
	}

	private static void demo2() throws IOException {
		ServerSocket server = new ServerSocket(11111);
		Socket socket = server.accept();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream ps = new PrintStream(socket.getOutputStream());

		ps.println("欢迎来到这里");
		System.out.println(br.readLine());
		ps.println("不好意思，人员已经满了里");
		System.out.println(br.readLine());
		
		socket.close();
	}

	private static void demo1() throws IOException {
		ServerSocket server = new ServerSocket(11111);
		Socket socket2 = server.accept();

		InputStream is = socket2.getInputStream();
		OutputStream os = socket2.getOutputStream();

		os.write("你好漂亮".getBytes());

		byte[] arr = new byte[1024];
		int len = is.read(arr);
		System.out.println(new String(arr, 0, len));

		//server.close();
		socket2.close();
	}

}
