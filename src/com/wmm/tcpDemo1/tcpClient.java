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
	 * 1.�ͻ��� 
	 *����socket���ӷ������ˣ�ָ��ip���˿ںţ�ͨ��ip��ַ�Ҷ�Ӧ������
	 *����socket��getInputStream()��getOutputStream()������ȡ�Ϳͻ���������IO��
	 *���������Զ�ȡ�ͻ��������д��������
	 *���������д�����ݵ��ͻ��˵�������
	 */
	public static void main(String[] args) throws IOException, IOException {
		//demo1();
		Socket socket = new Socket("192.168.1.105", 11111);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream ps = new PrintStream(socket.getOutputStream());

		System.out.println(br.readLine());
		ps.println("���뱨����ҵ��");
		System.out.println(br.readLine());
		ps.println("���ˣ���ѧ��");
		
		socket.close();
	}

	private static void demo1() throws UnknownHostException, IOException {
		Socket socket = new Socket("192.168.1.105", 11111);
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();

		byte[] arr = new byte[1024];
		int len = is.read(arr);
		System.out.println(new String(arr, 0, len));

		os.write("����������˵ʵ��".getBytes());

		socket.close();
	}

}
