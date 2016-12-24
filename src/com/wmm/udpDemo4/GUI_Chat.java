package com.wmm.udpDemo4;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Ϊ�˽�����д���뷽�㣬�����ȼ̳�Frame��
public class GUI_Chat extends Frame {

	private TextArea viewText;
	private TextArea sendText;
	private Button send;
	private Button log;
	private Button shake;
	private TextField tf;
	private DatagramSocket socket;
	private Button clear;
	private BufferedWriter bw;

	public GUI_Chat() throws IOException {
		init();// ��ʼ��
		southPanel();
		centerPanel();
		event();
	}

	private class Recevie extends Thread {
		@Override
		public void run() {
			while (true) {
				try {

					// ��������
					DatagramSocket socket = new DatagramSocket(10009);
					DatagramPacket packet = new DatagramPacket(new byte[8192], 8192);
					socket.receive(packet);// ������Ϣ
					byte[] arr = packet.getData();// ��ȡ��Ϣ
					int len = packet.getLength();// ��ȡ����
					//�ж�
					if(arr[0] == -1 && len == 1) {
						shakeFile();
						continue;//��ֹ����ѭ��
					}
					String msg = new String(arr, 0, len);// ��ȡ��Ϣ
					String time = getCurrentTime();// ��ȡ��ǰϵͳʱ��
					String ip = packet.getAddress().getHostName();// ��ȡip
					// ����UI
					String str = time + " " + ip + " ����˵��" + "\r\n" + msg + "\r\n";
					viewText.append(str);
					bw.write(str);
					// socket.close();// �ر�

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void event() {
		// ����������¼�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				socket.close();
				try {
					bw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);// �رմ���
			}
		});
		// ��Ӽ����¼�
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					send();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		//������ť�����¼�
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				viewText.setText("");
				
			}
		});
		//�����¼����
		log.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					logFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		//�𶯹���
		shake.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					/*shakeFile();*/
					send(new byte[] {-1},tf.getText());//��ʱ�����Է������𶯣�����-1���޷������ģ���������һ���ֽڣ��Է����Լ��Ľ��շ����е����Լ���shake����
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		//�ı���Ӽ��̼����¼�
		sendText.addKeyListener(new KeyAdapter() {
			//��дkeyReleased()����
			@Override
			public void keyReleased(KeyEvent e) {
				//�ж��Ƿ����˻س�
				if(e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {//���س�+ctrl��
					try {
						send();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				super.keyReleased(e);
			}
		});
	}

	private void shakeFile() throws InterruptedException {
		//�õ�����λ��
		int x = this.getLocation().x;
		int y = this.getLocation().y;		
		//�ı�λ��
		for(int i =0; i < 10;i++) {
			this.setLocation(x+20, y+20);
			Thread.sleep(30);
			this.setLocation(x+20, y-20);
			Thread.sleep(30);
			this.setLocation(x-20, y+20);
			Thread.sleep(30);
			this.setLocation(x-20, y-20);
			Thread.sleep(30);
			//��ԭλ��
			this.setLocation(x, y);
		}	
	}
	private void logFile() throws IOException {
		//�������¼����һ���ļ�����init()������
		bw.flush();//ˢ�»�����
		//���������¼�ļ�
		FileInputStream fis = new FileInputStream("config.txt");//�������ļ��ж�ȡ�����¼
		ByteArrayOutputStream baos = new ByteArrayOutputStream();//�����ڴ������
		byte[] arr = new byte[8192];
		int len;
		while((len = fis.read(arr))!= -1) {//���ļ��ϵ����ݶ���������
			baos.write(arr,0,len);//�������е��ֽ�����д��������
		}
		fis.close();//�ر���
		
	}
	//����һ��send�����غ���,����һ���̶���Ϣ�����ӵ������Ϣ��ʱ���ִ����Ч��
	private void send(byte[] arr,String ip) throws IOException {
		DatagramPacket packet = new DatagramPacket(arr, arr.length,InetAddress.getByName(ip), 10009);
		socket.send(packet);
		
	}
	private void send() throws IOException {
		// ��ȡ������Ϣ
		// ���͵������¼���
		// ���͸�ָ��ip��ַ
		String ip = tf.getText();
		//ip����������˽���
		ip = ip.trim().length() == 0 ? "255.255.255.255" :ip;
		String message = sendText.getText();

/*		DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length,InetAddress.getByName(ip), 10009);
		socket.send(packet);
		*/
		send(message.getBytes(),ip);
		String time = getCurrentTime();
		String str = time + " �Ҷ�  " + (ip.equals("255.255.255.255")?" ������ ":ip) + "˵��\r\n" + message + "\r\n";
		viewText.append(str);
		bw.write(str);//д�����ݿ��ļ�
		sendText.setText("");

	}

	// �Զ���һ�����������ص�ǰʱ��
	private String getCurrentTime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��HH:mm:ss");
		return sdf.format(d);
	}

	private void centerPanel() {
		// �����м�ģ�Ĭ����ʽ���֣�����Ӧ�������¹�ϵ
		Panel center = new Panel();
		viewText = new TextArea();
		sendText = new TextArea(5, 1);
		center.setLayout(new BorderLayout());// �ı䲼�ֹ�����

		viewText.setEditable(false);// ���óɲ���д
		viewText.setBackground(Color.white);// ���ñ�����ɫ
		viewText.setFont(new Font("x", Font.PLAIN, 15));// ��������
		sendText.setFont(new Font("x", Font.PLAIN, 20));// ��������

		center.add(sendText, BorderLayout.SOUTH);
		center.add(viewText, BorderLayout.CENTER);

		this.add(center, BorderLayout.CENTER);

	}

	private void southPanel() {
		// �·���panel,�����ϱߵ����
		Panel south = new Panel();
		tf = new TextField(15);
		tf.setText("127.0.0.1");
		send = new Button("����");
		clear = new Button("����");
		log = new Button("��¼");
		shake = new Button("��");

		// ���ȥ,Ĭ�ϵ�����ʽ����
		south.add(tf);
		south.add(send);
		south.add(clear);
		south.add(log);
		south.add(shake);

		this.add(south, BorderLayout.SOUTH);

	}

	private void init() throws IOException {
		this.setSize(400, 600);// ���ô�С
		this.setLocation(500, 50);
		new Recevie().start();// ���������߳�
		socket = new DatagramSocket();
		bw = new BufferedWriter(new FileWriter("config.txt",true));//true��Ϊ�����Ժ���õ�ʱ��׷�ӣ��Ͳ���ÿ�θ���һ����
		this.setVisible(true);// ��ʾ����
	}

	public static void main(String[] args) throws IOException {
		// ����һ���ı�������Ϊ�̳�����
		new GUI_Chat();

	}

}
