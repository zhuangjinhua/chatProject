package com.zjh.swingDemo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
//		D:\clientFile\Hello.txt
//发送信息
public class ChatClient {
	public static final String ip="127.0.0.1";//服务器的ip地址
	public static final int port=30000;//服务器的端口号
	private Socket socket;//创建与服务器连接的Socket
	private ClientThread client;
	JTextArea showText;//显示信息窗口
	JLabel userName;//用户名
	JTextField inputText;//输入框
	JButton sendInfo;//发送信息按钮
	JButton sendFile;//发送文件按钮
	JFrame frame;//用户界面
	JPanel panel;//最后一行集合
//	PrintStream printer;
	OutputStream output;
	JScrollPane js;
	public void init(String name)throws Exception{
		socket=new Socket(ip,port);
		showText=new JTextArea();//显示窗口
		//开启客户端线程，用于监听服务器信息
		client=new ClientThread(name,socket,showText);
		client.start();
		frame=new JFrame();
		userName=new JLabel();//用户名
		userName.setText(name);//设置名字
		inputText=new JTextField();//输入窗口
		sendInfo=new JButton("发送信息");//发送信息按钮
		sendFile=new JButton("发送文件");//发送信息按钮
		panel=new JPanel();
		output = socket.getOutputStream();
		js=new JScrollPane(showText);
		//点击事件
		sendInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String info=inputText.getText();
				sendInfo(info);
			}
		});
		sendFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String path=inputText.getText();
				sendFile(path);
			}
		});
		inputText.setPreferredSize(new Dimension(180,30));
		panel.add(inputText);
		panel.add(sendInfo);
		panel.add(sendFile);
		BorderLayout layout=new BorderLayout();
		frame.setLayout(layout);
		frame.add(userName,BorderLayout.NORTH);
		js.setPreferredSize(new Dimension(300,200));
		showText.setEditable(false);//设置不可编辑
		showText.setLineWrap(true);
		frame.add(js,BorderLayout.CENTER);
		frame.add(panel,BorderLayout.SOUTH);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);
	}
	//以下用于发送信息到server
	public void sendInfo(String info){
		if(info==null||info=="")
			return ;
		if(info.startsWith("C:\\")||info.startsWith("D:\\")||info.startsWith("F:\\"))
			return ;
		try {
			output.write((client.getName()+":"+info).getBytes());//输出信息到server端
			inputText.setText("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//发送文件
	public void sendFile(String path){
		//判断非法情况
		if(path==null||path=="")
			return ;
		if(path.startsWith("C:\\")||path.startsWith("D:\\")||path.startsWith("F:\\")){
			sendInfo("- - - - - - - - - - - -传输了一份文件....");
			try {
				File file=new File(path);
				String fileName=new String("file:"+file.getName());
				output.write(fileName.getBytes());
				output.flush();
				Thread.sleep(500);
				InputStream inputStream=new FileInputStream(file);
				BufferedInputStream reader=new BufferedInputStream(inputStream);
				String tmp;
				byte[] datas=new byte[1024];
				int sum;
				//读取文件的内容并发送
				while((sum=reader.read(datas))>0){
					output.write(datas,0,sum);
					if(sum<1024)
						break;
				}
				
				inputStream.close();
				inputText.setText("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
