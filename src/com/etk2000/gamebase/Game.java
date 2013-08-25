package com.etk2000.gamebase;

import java.util.logging.Logger;

public class Game {
	private String salt;
	private Logger logger = Constants.logger;

	public Game(String SALT) {
		salt = SALT;
		int error = 0;

		if (salt.equals("offline")) {
			logger.warning("running in offline mode!");
		}
		else if (!salt.contains(":")) {// this runs if invalid info is sent to the game
			error = Constants.error_invalidInfo;
			logger.severe("Invalid Login Info!");
			logger.info("Shutting Down With Error Code " + error + '!');
			this.closeGame(error);
		}
		
		

		this.freeMemory();
		this.closeGame(error);
	}

	public void closeGame(int... errorCode) {
		salt = null;

		Runtime.getRuntime().freeMemory();
		if (errorCode.length == 1)
			Runtime.getRuntime().exit(errorCode[0]);
		Runtime.getRuntime().exit(0);// so java closes after running
	}

	public void freeMemory() {
		logger.info("free: " + Runtime.getRuntime().freeMemory());
	}
}
