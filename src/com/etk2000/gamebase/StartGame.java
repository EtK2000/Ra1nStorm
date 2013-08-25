/** push to https://github.com/EtK2000/Ra1nStorm.git **/
package com.etk2000.gamebase;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.etk2000.gui.LoginGUI;
import com.etk2000.saving.OptionsFile;

public class StartGame {
	private static Logger logger;
	private static OptionsFile options = new OptionsFile();
	public static String username;
	public static String password;

	public static Game Ra1n = null;

	public static void main(String[] arg) {
		logger = Constants.logger;
		/** JIC **/
		if (!Constants.setup) {
			return;
		}

		logger.info(Constants.os + " Environment");

		if (options.isEmpty()) {
			logger.info("no options set, defaulting!");
			options.save(new String[] { "hi0", "hi1", "hi2", "hi3", "hi4" });
		}
		else {
			Constants.logger.info("loading config...");
			String opts = new String();
			for (int i = 0; i < options.data.length; i++) {
				opts += options.data[i] + ", ";
			}
			logger.info(opts);
		}

		options.close();

		ArrayList<String> args = Functions.setupArgs(arg);

		try {
			if (args.contains("login")) {
				int pos = args.indexOf("login");
				username = args.get(++pos);
				password = args.get(++pos);
			}
		}
		catch (IndexOutOfBoundsException e) {// not enough arguments are given
			logger.warning("not enough arguments are given for argument \"login\"");
		}
		if (username == null || password == null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					LoginGUI.createAndShowGUI();
				}
			});
			while (username == null || password == null) {
				System.out.print("");// needed so java doesn't get stuck at the while
			}
		}
		String salt = authenticate(username, password);
		Ra1n = new Game(salt);
		// Runtime.getRuntime().addShutdownHook(myShutdownHook);
	}

	public static String authenticate(String user, String pass, boolean... log) {
		boolean doLog = true;
		if (log.length > 0) {
			doLog = log[0];
		}
		if (doLog)
			logger.info("trying to authenticate: " + username + ", " + password);

		try {
			String parameters = "http://login.minecraft.net/?user=" + URLEncoder.encode(username, "UTF-8")
					+ "&password=" + URLEncoder.encode(password, "UTF-8") + "&version=" + 13;
			String result = openUrl(parameters);

			if (result == null) {
				if (doLog)
					StartGame.logger.warning("Could Not Connect!");
				return "offline";
			}

			if (!result.contains(":")) {
				if (doLog)
					StartGame.logger.warning("Login Failed: " + result);
				return "incorrect_info";
			}

			return result;
		}
		catch (UnsupportedEncodingException e) {
			return "offline";// TODO: change
		}
		catch (NullPointerException e) {
			return "offline";// TODO: change
		}
	}

	private static String openUrl(String addr) {
		try {
			URL url = new URL(addr);
			java.io.InputStream is;
			is = url.openConnection().getInputStream();
			java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
			String buf = "";
			String line = null;

			while ((line = reader.readLine()) != null) {
				buf += "\n" + line;
			}

			reader.close();
			return buf;
		}
		catch (Exception e) {
		}

		return null;
	}
}