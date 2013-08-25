package com.etk2000.saving;

import com.etk2000.gamebase.Constants;
import com.etk2000.gamebase.Functions;

public class OptionsFile extends DataFile {
	public String[] data;
	
	public OptionsFile() {
		super(Constants.dataDir.getPath(), "options.txt");
		loadOptions();
	}
	
	public void loadOptions() {
		data = Functions.ArrayListToString(this.load());
	}
}
