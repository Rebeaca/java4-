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

//为了接下来写代码方便，在这先继承Frame类
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
		init();// 初始化
		southPanel();
		centerPanel();
		event();
	}

	private class Recevie extends Thread {
		@Override
		public void run() {
			while (true) {
				try {

					// 创建对象
					DatagramSocket socket = new DatagramSocket(10009);
					DatagramPacket packet = new DatagramPacket(new byte[8192], 8192);
					socket.receive(packet);// 接收信息
					byte[] arr = packet.getData();// 获取信息
					int len = packet.getLength();// 获取长度
					//判断
					if(arr[0] == -1 && len == 1) {
						shakeFile();
						continue;//终止本次循环
					}
					String msg = new String(arr, 0, len);// 获取信息
					String time = getCurrentTime();// 获取当前系统时间
					String ip = packet.getAddress().getHostName();// 获取ip
					// 更新UI
					String str = time + " " + ip + " 对我说：" + "\r\n" + msg + "\r\n";
					viewText.append(str);
					bw.write(str);
					// socket.close();// 关闭

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
		// 给窗体添加事件
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
				System.exit(0);// 关闭窗体
			}
		});
		// 添加监听事件
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
		//清屏按钮监听事件
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				viewText.setText("");
				
			}
		});
		//聊天记录功能
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
		//震动功能
		shake.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					/*shakeFile();*/
					send(new byte[] {-1},tf.getText());//当时，给对方发送震动，但是-1是无法创建的，这代表的是一个字节，对方在自己的接收方法中调用自己的shake方法
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		//文本添加键盘监听事件
		sendText.addKeyListener(new KeyAdapter() {
			//重写keyReleased()方法
			@Override
			public void keyReleased(KeyEvent e) {
				//判断是否按下了回车
				if(e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {//【回车+ctrl】
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
		//拿到窗体位置
		int x = this.getLocation().x;
		int y = this.getLocation().y;		
		//改变位置
		for(int i =0; i < 10;i++) {
			this.setLocation(x+20, y+20);
			Thread.sleep(30);
			this.setLocation(x+20, y-20);
			Thread.sleep(30);
			this.setLocation(x-20, y+20);
			Thread.sleep(30);
			this.setLocation(x-20, y-20);
			Thread.sleep(30);
			//还原位置
			this.setLocation(x, y);
		}	
	}
	private void logFile() throws IOException {
		//将聊天记录生成一个文件，在init()函数中
		bw.flush();//刷新缓冲区
		//加载聊天记录文件
		FileInputStream fis = new FileInputStream("config.txt");//从配置文件中读取聊天记录
		ByteArrayOutputStream baos = new ByteArrayOutputStream();//创建内存输出流
		byte[] arr = new byte[8192];
		int len;
		while((len = fis.read(arr))!= -1) {//将文件上的数据读到数组中
			baos.write(arr,0,len);//将数组中的字节数据写到缓存中
		}
		fis.close();//关闭流
		
	}
	//定义一个send的重载函数,发送一个固定信息，当接到这个信息的时候就执行震动效果
	private void send(byte[] arr,String ip) throws IOException {
		DatagramPacket packet = new DatagramPacket(arr, arr.length,InetAddress.getByName(ip), 10009);
		socket.send(packet);
		
	}
	private void send() throws IOException {
		// 获取输入信息
		// 发送到聊天记录面板
		// 发送给指定ip地址
		String ip = tf.getText();
		//ip输入框所有人接受
		ip = ip.trim().length() == 0 ? "255.255.255.255" :ip;
		String message = sendText.getText();

/*		DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length,InetAddress.getByName(ip), 10009);
		socket.send(packet);
		*/
		send(message.getBytes(),ip);
		String time = getCurrentTime();
		String str = time + " 我对  " + (ip.equals("255.255.255.255")?" 所有人 ":ip) + "说：\r\n" + message + "\r\n";
		viewText.append(str);
		bw.write(str);//写入数据库文件
		sendText.setText("");

	}

	// 自定义一个方法，返回当前时间
	private String getCurrentTime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
		return sdf.format(d);
	}

	private void centerPanel() {
		// 创建中间的，默认流式布局，两个应该是上下关系
		Panel center = new Panel();
		viewText = new TextArea();
		sendText = new TextArea(5, 1);
		center.setLayout(new BorderLayout());// 改变布局管理器

		viewText.setEditable(false);// 设置成不可写
		viewText.setBackground(Color.white);// 设置背景颜色
		viewText.setFont(new Font("x", Font.PLAIN, 15));// 设置字体
		sendText.setFont(new Font("x", Font.PLAIN, 20));// 设置字体

		center.add(sendText, BorderLayout.SOUTH);
		center.add(viewText, BorderLayout.CENTER);

		this.add(center, BorderLayout.CENTER);

	}

	private void southPanel() {
		// 下方的panel,创建南边的面板
		Panel south = new Panel();
		tf = new TextField(15);
		tf.setText("127.0.0.1");
		send = new Button("发送");
		clear = new Button("清屏");
		log = new Button("记录");
		shake = new Button("震动");

		// 填进去,默认的是流式布局
		south.add(tf);
		south.add(send);
		south.add(clear);
		south.add(log);
		south.add(shake);

		this.add(south, BorderLayout.SOUTH);

	}

	private void init() throws IOException {
		this.setSize(400, 600);// 设置大小
		this.setLocation(500, 50);
		new Recevie().start();// 开启接收线程
		socket = new DatagramSocket();
		bw = new BufferedWriter(new FileWriter("config.txt",true));//true是为了在以后调用的时候追加，就不会每次覆盖一次了
		this.setVisible(true);// 显示窗体
	}

	public static void main(String[] args) throws IOException {
		// 创建一个文本区域，因为继承了类
		new GUI_Chat();

	}

}
