package com.wmm.udpDemo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Send {
	/*
	 *发送 创建DatagramSocket，随机端口号
	 *创建DatagramPacket,指定数据，长度，地址，端口
	 *使用DatagramSocket发送 DatagramPacket
	 *关闭DatagramSocket
	 */
	public static void main(String[] args) throws IOException {
		String str = "你好啊";
		DatagramSocket socket = new DatagramSocket();
		DatagramPacket packet = new DatagramPacket(str.getBytes(), str.getBytes().length,
				InetAddress.getByName("192.168.1.111"), 12345);
		socket.send(packet);
		socket.close();

	}

}
