//this has been ported from my original Bukkit code on Friday August 2nd 3:06PM
package com.etk2000.saving;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataFile {
	private File file;
	protected String path;

	/** first @param is location, second is file **/
	public DataFile(String... params) {
		if (params.length > 1) {
			path = params[0] + '\\';
			for (int i = 1; i < params.length; i++) {
				path += params[i];
				if (i + 1 < params.length) {
					path += File.separator;
				}
			}
		}
		else {
			path = params[0];
		}
		file = new File(path);
		if (!file.exists()) {// doesn't exist
			new File(file.getParent()).mkdirs();

			try {
				file.createNewFile();
				// file.setExecutable(true);
				// file.setReadable(true);
				// file.setWritable(true);
			}
			catch (IOException e) {
			}
		}
	}

	/** every line for itself **/
	public ArrayList<String> load() {
		try {
			ArrayList<String> text = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {// read a line
				text.add(line);
			}
			br.close();
			return text;// if skips the while, should be null!
		}
		catch (IOException e) {
		}
		return null;
	}

	/** all the strings together **/
	public void save(String[] text) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < text.length; i++) {
				bw.write(text[i]);
				if (i + 1 < text.length) {
					bw.newLine();
				}
			}
			bw.flush();// just to be safe
			bw.close();
		}
		catch (IOException e) {
		}
	}

	/** path getter **/
	public String getPath() {
		return path;
	}

	public boolean isEmpty() {
		return file.length() == 0;
	}

	public void close() {
		file = null;
		path = null;
	}

	/** this is the same as the constructor **/
	public void open(String... params) {
		if (params.length > 1) {
			path = params[0] + '\\';
			for (int i = 1; i < params.length; i++) {
				path += params[i];
				if (i + 1 < params.length) {
					path += File.separator;
				}
			}
		}
		else {
			path = params[0];
		}
		file = new File(path);
		if (!file.exists()) {// doesn't exist
			new File(file.getParent()).mkdirs();

			try {
				file.createNewFile();
				// file.setExecutable(true);
				// file.setReadable(true);
				// file.setWritable(true);
			}
			catch (IOException e) {
			}
		}
	}
}