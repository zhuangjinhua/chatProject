package com.zjh.project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;

public class Main {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\clientFile\\Hello.txt"))));
//		PrintStream ps=new PrintStream(new File("D:\\clientFile\\test1.txt"));
//		ps.println("���ͣ��������");
		String tmp;
		while((tmp=reader.readLine())!=null)
			System.out.println(tmp);
		System.out.println("over");
	}

}
