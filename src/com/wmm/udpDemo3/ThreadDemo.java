package com.wmm.udpDemo3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ThreadDemo {
	// 多线程
	public static void main(String[] args) throws InterruptedException {
		// 定义两个线程实现接收和发送的内容
		// 启动两个线程
		new SendThread().start();
		Thread.sleep(1000);
		new Recevie().start();
	}
}
class Recevie extends Thread {
	// 重写run方法
	@Override
	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket(22345);// 创建socket，相当于创建码头
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);// 创建package，相当于创建集装箱
			while (true) {
				socket.receive(packet);// 接收获取
				byte[] arr = packet.getData();// 从集装箱中获取数据
				int len = packet.getLength();// 获取发送过来的有效字节个数
				String ip = packet.getAddress().getHostAddress();// 获取ip地址
				int port = packet.getPort();// 获取端口号
				System.out.println(ip + ":" + port + ":" + new String(arr, 0, len));
				// socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class SendThread extends Thread {
	// 重写run方法
	@Override
	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket();
			// 键盘录入
			Scanner sc = new Scanner(System.in);
			// 不断发送信息
			while (true) {
				String line = sc.nextLine();
				if ("quit".equals(line)) {
					break;
				}
				DatagramPacket packet = new DatagramPacket(line.getBytes(), line.getBytes().length,
						InetAddress.getByName("192.168.1.111"), 22345);
				socket.send(packet);
				// socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
