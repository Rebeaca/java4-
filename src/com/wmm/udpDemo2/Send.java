package com.wmm.udpDemo2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Send {
	/*
	 * 发送 创建DatagramSocket，随机端口号 创建DatagramPacket,指定数据，长度，地址，端口
	 * 使用DatagramSocket发送 DatagramPacket 关闭DatagramSocket
	 */
	public static void main(String[] args) throws IOException {
		// String str = "你好啊";
		DatagramSocket socket = new DatagramSocket();
		// 键盘录入
		Scanner sc = new Scanner(System.in);
		//不断发送信息
		while (true) {
			String line = sc.nextLine();
			if ("quit".equals(line)) {
				break;
			}
			DatagramPacket packet = new DatagramPacket(line.getBytes(), line.getBytes().length,
					InetAddress.getByName("192.168.1.111"), 12345);
			socket.send(packet);
			//socket.close();
		}

	}

}
