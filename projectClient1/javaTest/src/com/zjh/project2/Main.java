package com.zjh.project2;

import java.awt.*;
import java.awt.event.*;
import java.text.MessageFormat;

import javax.swing.JOptionPane;

public class Main extends Frame{
	public static void main(String[] args){
		Frame frame=new Frame("登录");//框架
		Label userLabel=new Label("用户名");//用户名标签
		Label passwordLabel=new Label(" 密码   ");//密码标签
		Button sureButton=new Button("确定");//确定按钮
		sureButton.setPreferredSize(new Dimension(100,40));//设置按钮大小
		TextField userText=new TextField();//用户姓名输入
		userText.setPreferredSize(new Dimension(130,36));//设置输入框大小
		TextField passwordText=new TextField();//用户密码输入
		passwordText.setPreferredSize(new Dimension(130,36));//设置输入框大小
		Font font=new Font("宋体",Font.PLAIN,16);//设置字体为宋体，大小为16
		userLabel.setFont(font);//设置字体
		passwordLabel.setFont(font);//设置字体
		passwordText.setEchoChar('*');
		Panel topPanel=new Panel();//第一行集合
		topPanel.add(userLabel);//用户名标签加载
		topPanel.add(userText);//用户名输入框加载
		Panel centerPanel=new Panel();//第二行集合
		centerPanel.add(passwordLabel);//用户密码标签加载
		centerPanel.add(passwordText);//用户密码输入框加载
		Panel buttomPanel=new Panel();//第三行集合
		buttomPanel.add(sureButton);//确定按钮加载
		frame.setLayout(new GridLayout(3,1));//设置为网格布局，3行1列
		sureButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				if(passwordText.getText().equals("123456")){
					JOptionPane.showMessageDialog(null,"登录成功","提示",JOptionPane.PLAIN_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null,"登录失败","提示",JOptionPane.PLAIN_MESSAGE);
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		//加载至Frame中
		frame.add(topPanel);
		frame.add(centerPanel);
		frame.add(buttomPanel);
		frame.pack();//自适应大小
		frame.setVisible(true);//显示
	}
}
