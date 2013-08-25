package com.etk2000.gamebase;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Logger;

import com.etk2000.osClasses.OS;
import com.etk2000.saving.DataDir;

public class Constants {
	/** this is only used to initialize the constants **/
	public static boolean setup = setup();

	/** the actual constants **/
	public static final String dir = new File(StartGame.class.getProtectionDomain().getCodeSource().getLocation()
			.getPath().substring(1)).getParent() + '\\';
	public static final Logger logger = Logger.getLogger("Ra1n");
	public static final DataDir dataDir = new DataDir(OS.appData() + "\\.Ra1nStorm");
	public static final int minUNLength = 5;
	public static final int maxUNLength = 15;
	public static final int minPWLength = 5;
	public static final int maxPWLength = 15;

	/** debugging info **/
	public static final float javaVersion = Float.parseFloat(System.getProperty("java.specification.version"));
	public static final int javaArch = Integer.parseInt(System.getProperty("sun.arch.data.model"));
	public static final int processors = Runtime.getRuntime().availableProcessors();
	public static final String arch = System.getProperty("os.arch");
	public static final String lang = System.getProperty("user.language");
	public static final String os = OS.getOS().toString();
	public static final String temp = System.getProperty("java.io.tmpdir");

	/** Game Error Codes **/
	public static final int error_invalidInfo = -1;

	public static boolean setup() {
		/** setup all loggers **/
		Handler[] handlers = Logger.getLogger("").getHandlers();
		for (Handler h : handlers) {
			h.setFormatter(new LogFormatter());
		}

		return true;
	}
}
