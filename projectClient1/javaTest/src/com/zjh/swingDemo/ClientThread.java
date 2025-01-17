package com.zjh.swingDemo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JTextArea;
//接收信息
//D:\clientFile\Hello.txt
public class ClientThread extends Thread{
	private Socket socket=null;//Socket对象
	private BufferedInputStream reader=null;//缓冲流读取信息
	private String threadName;
	private JTextArea showText;//显示框
	//构造函数
	public ClientThread(String threadName,Socket socket,JTextArea showText) throws IOException{
		super(threadName);//为线程创建名字threadName
		this.threadName=threadName;//线程的名字
		this.socket=socket;
		//初始化reader对象
		reader=new BufferedInputStream(this.socket.getInputStream());
		this.showText=showText;
	}
	//重写多线程run方法,用于监听来自服务器的消息
	@Override
	public void run() {
		String tmp;//用于保存读取的信息
		byte[] data=new byte[1024];
		int sum;
		try{
			while(true){
				//接收到内容
				if((sum=reader.read(data))>0){
					tmp=new String(data,0,sum);
					//内容为文件
					if(tmp.startsWith("file")){
						InputStream inputStream=socket.getInputStream();
						File dir=new File("D:\\"+threadName);
						dir.mkdir();//创建目录  ：  D：\线程名字
						File file=new File("D:\\"+threadName+"\\"+tmp.substring(tmp.indexOf(":")+1));
						//保存文件
						OutputStream output=new FileOutputStream(file);
						while((sum=reader.read(data))>0){
							tmp=new String(data,0,sum);
							output.write(data,0,sum);
							System.out.println(tmp);
							System.out.println(sum);
							if(sum<1024)
								break;
						}
					//内容为消息
					}else{
						showText.append(tmp);
					}
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	
}