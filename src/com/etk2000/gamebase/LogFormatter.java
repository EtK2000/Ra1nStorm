package com.etk2000.gamebase;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogFormatter extends SimpleFormatter {
	public String format(LogRecord record) {
		Level lvl = record.getLevel();
		if (lvl == Level.INFO)
			return '[' + record.getLoggerName() + "] " + record.getMessage() + "\r\n";
		if (lvl == Level.FINE || lvl == Level.FINER || lvl == Level.FINEST)
			return super.format(record);
		return '[' + record.getLoggerName() + "][" + lvl + "] " + record.getMessage() + "\r\n";
	}
}
