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
//������Ϣ
public class ChatClient {
	public static final String ip="127.0.0.1";//��������ip��ַ
	public static final int port=30000;//�������Ķ˿ں�
	private Socket socket;//��������������ӵ�Socket
	private ClientThread client;
	JTextArea showText;//��ʾ��Ϣ����
	JLabel userName;//�û���
	JTextField inputText;//�����
	JButton sendInfo;//������Ϣ��ť
	JButton sendFile;//�����ļ���ť
	JFrame frame;//�û�����
	JPanel panel;//���һ�м���
//	PrintStream printer;
	OutputStream output;
	JScrollPane js;
	public void init(String name)throws Exception{
		socket=new Socket(ip,port);
		showText=new JTextArea();//��ʾ����
		//�����ͻ����̣߳����ڼ�����������Ϣ
		client=new ClientThread(name,socket,showText);
		client.start();
		frame=new JFrame();
		userName=new JLabel();//�û���
		userName.setText(name);//��������
		inputText=new JTextField();//���봰��
		sendInfo=new JButton("������Ϣ");//������Ϣ��ť
		sendFile=new JButton("�����ļ�");//������Ϣ��ť
		panel=new JPanel();
		output = socket.getOutputStream();
		js=new JScrollPane(showText);
		//����¼�
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
		showText.setEditable(false);//���ò��ɱ༭
		showText.setLineWrap(true);
		frame.add(js,BorderLayout.CENTER);
		frame.add(panel,BorderLayout.SOUTH);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);
	}
	//�������ڷ�����Ϣ��server
	public void sendInfo(String info){
		if(info==null||info=="")
			return ;
		if(info.startsWith("C:\\")||info.startsWith("D:\\")||info.startsWith("F:\\"))
			return ;
		try {
			output.write((client.getName()+":"+info).getBytes());//�����Ϣ��server��
			inputText.setText("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//�����ļ�
	public void sendFile(String path){
		//�жϷǷ����
		if(path==null||path=="")
			return ;
		if(path.startsWith("C:\\")||path.startsWith("D:\\")||path.startsWith("F:\\")){
			sendInfo("- - - - - - - - - - - -������һ���ļ�....");
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
				//��ȡ�ļ������ݲ�����
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