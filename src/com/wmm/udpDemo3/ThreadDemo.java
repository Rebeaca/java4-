package com.wmm.udpDemo3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ThreadDemo {
	// ���߳�
	public static void main(String[] args) throws InterruptedException {
		// ���������߳�ʵ�ֽ��պͷ��͵�����
		// ���������߳�
		new SendThread().start();
		Thread.sleep(1000);
		new Recevie().start();
	}
}
class Recevie extends Thread {
	// ��дrun����
	@Override
	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket(22345);// ����socket���൱�ڴ�����ͷ
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);// ����package���൱�ڴ�����װ��
			while (true) {
				socket.receive(packet);// ���ջ�ȡ
				byte[] arr = packet.getData();// �Ӽ�װ���л�ȡ����
				int len = packet.getLength();// ��ȡ���͹�������Ч�ֽڸ���
				String ip = packet.getAddress().getHostAddress();// ��ȡip��ַ
				int port = packet.getPort();// ��ȡ�˿ں�
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
	// ��дrun����
	@Override
	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket();
			// ����¼��
			Scanner sc = new Scanner(System.in);
			// ���Ϸ�����Ϣ
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
