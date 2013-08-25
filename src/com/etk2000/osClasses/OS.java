package com.etk2000.osClasses;

public enum OS {
	Windows, Mac, Linux, Other;

	static String os = System.getProperty("os.name").toUpperCase();

	public static OS getOS() {
		if (os.contains("WIN")) {// Windows
			return Windows;
		}
		else if (os.contains("MAC")) {// Mac
			return Mac;
		}
		else if (os.contains("NUX")) {// Linux, etc.
			return Linux;
		}
		return Other;
	}

	public static String appData() {
		if (os.contains("WIN")) {// Windows
			return System.getenv("APPDATA");
		}
		else if (os.contains("MAC")) {// Mac
			return System.getProperty("user.home") + "/Library/Application Support";
		}
		else if (os.contains("NUX")) {// Linux, etc.
			return System.getProperty("user.home");
		}
		return System.getProperty("user.dir");
	}
}