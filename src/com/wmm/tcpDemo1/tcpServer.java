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
	 * 2.�������� ����serversocket��ָ���˿ںţ�
	 * ����serversocket��accept()��������һ���ͻ������󣬵õ�һ��socket
	 * ����socket��getInputStream()��getOutputStream()������ȡ�Ϳͻ���������IO��
	 * ���������Զ�ȡ�ͻ��������д�������� ���������д�����ݵ��ͻ��˵�������
	 */
	public static void main(String[] args) throws IOException {
		//demo1();
		demo2();
		//���߳�
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

						ps.println("��ӭ��������");
						System.out.println(br.readLine());
						ps.println("������˼����Ա�Ѿ�������");
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

		ps.println("��ӭ��������");
		System.out.println(br.readLine());
		ps.println("������˼����Ա�Ѿ�������");
		System.out.println(br.readLine());
		
		socket.close();
	}

	private static void demo1() throws IOException {
		ServerSocket server = new ServerSocket(11111);
		Socket socket2 = server.accept();

		InputStream is = socket2.getInputStream();
		OutputStream os = socket2.getOutputStream();

		os.write("���Ư��".getBytes());

		byte[] arr = new byte[1024];
		int len = is.read(arr);
		System.out.println(new String(arr, 0, len));

		//server.close();
		socket2.close();
	}

}
