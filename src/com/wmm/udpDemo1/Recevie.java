package com.wmm.udpDemo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Recevie {
/*
 * 2.����
 * ����DatagramSocket��ָ���˿ں�
 * ����DatagramPacket��ָ�����飬����
 * ʹ��DatagramSocket����DatagramPacket
 * �ر�DatagramSocket
 * ��DatagramPacket�ж�ȡ����
 *3. ���շ���ȡip�Ͷ˿ں�
 * 
 * */
	public static void main(String[] args) throws IOException {
		DatagramSocket socket = new DatagramSocket(12345);//����socket���൱�ڴ�����ͷ
		DatagramPacket packet = new DatagramPacket(new byte[1024],1024);//����package���൱�ڴ�����װ��
		socket.receive(packet);//���ջ�ȡ
		
		byte[] arr = packet.getData();//�Ӽ�װ���л�ȡ����
		//��ȡ���͹�������Ч�ֽڸ���
		int len = packet.getLength();
		System.out.println(new String(arr,0,len));
		
		socket.close();
		
		
	}

}
