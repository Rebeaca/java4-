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
	 * ���� ����DatagramSocket������˿ں� ����DatagramPacket,ָ�����ݣ����ȣ���ַ���˿�
	 * ʹ��DatagramSocket���� DatagramPacket �ر�DatagramSocket
	 */
	public static void main(String[] args) throws IOException {
		// String str = "��ð�";
		DatagramSocket socket = new DatagramSocket();
		// ����¼��
		Scanner sc = new Scanner(System.in);
		//���Ϸ�����Ϣ
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
