package com.mnasser;

public class DirUtils {
	
	static private final boolean isMac;
	static private final String macDir = "/Users/mnasser/Documents/workspace/personal-scrap/src/main/resources/";
	static private final String pcDir  = "/home/mnasser/Downloads/";
	static private final String dir;
	
	static {
		isMac = (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0)? true: false;
		dir = (isMac)? macDir : pcDir;
		
		System.out.println("OS = " + System.getProperty("os.name") + " Is Mac ? " + isMac);
	}

	public static String getWorkDir(){
		return dir;
	}
	
	public static void main(String[] args) {
		System.out.println("hello world");
	}
}
