package com.wmm.udpDemo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Send {
	/*
	 *���� ����DatagramSocket������˿ں�
	 *����DatagramPacket,ָ�����ݣ����ȣ���ַ���˿�
	 *ʹ��DatagramSocket���� DatagramPacket
	 *�ر�DatagramSocket
	 */
	public static void main(String[] args) throws IOException {
		String str = "��ð�";
		DatagramSocket socket = new DatagramSocket();
		DatagramPacket packet = new DatagramPacket(str.getBytes(), str.getBytes().length,
				InetAddress.getByName("192.168.1.111"), 12345);
		socket.send(packet);
		socket.close();

	}

}
