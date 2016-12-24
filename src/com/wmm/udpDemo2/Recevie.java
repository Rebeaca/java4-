package com.wmm.udpDemo2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Recevie {
/*
 * 2.接收
 * 创建DatagramSocket，指定端口号
 * 创建DatagramPacket，指定数组，长度
 * 使用DatagramSocket接收DatagramPacket
 * 关闭DatagramSocket
 * 从DatagramPacket中读取数据
 *3. 接收方获取ip和端口号
 * 
 * */
	public static void main(String[] args) throws IOException {
		DatagramSocket socket = new DatagramSocket(12345);//创建socket，相当于创建码头
		DatagramPacket packet = new DatagramPacket(new byte[1024],1024);//创建package，相当于创建集装箱
		while(true) {
			socket.receive(packet);//接收获取
			byte[] arr = packet.getData();//从集装箱中获取数据
			int len = packet.getLength();//获取发送过来的有效字节个数
			String ip = packet.getAddress().getHostAddress();//获取ip地址
			int port = packet.getPort();//获取端口号
			System.out.println(ip + ":"+port + ":"+new String(arr,0,len));
			//socket.close();
		}
		
	}

}
