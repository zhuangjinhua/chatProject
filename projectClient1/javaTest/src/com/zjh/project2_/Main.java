package com.zjh.project2_;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File dir=new File("D:\\test");
		dir.mkdir();
		File file=new File("D:\\test\\test.txt");
		file.createNewFile();
	}

}
