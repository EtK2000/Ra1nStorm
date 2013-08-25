//this has been ported from my original Bukkit code on Friday August 2nd 3:06PM
package com.etk2000.saving;

import java.io.File;
import java.util.ArrayList;

import com.etk2000.gamebase.Constants;

public class DataDir {
	private File folder;
	private String path;

	public DataDir(String PATH) {
		path = PATH;
		folder = new File(path);
		if (!folder.exists()) {// doesn't exist
			Constants.logger.info("directory \"" + path + "\" doesn't exist, creating it!");
			new File(folder.getParent()).mkdirs();

			folder.mkdir();
		}
	}

	/** get the files in the directory, every file name for itself **/
	public ArrayList<String> getFileNames() {
		ArrayList<String> files = new ArrayList<String>();
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				files.add("f, " + listOfFiles[i].getName());
			}
			else if (listOfFiles[i].isDirectory()) {
				files.add("d, " + listOfFiles[i].getName());
			}
		}
		return files;
	}

	/** path getter **/
	public String getPath() {
		return path;
	}
}