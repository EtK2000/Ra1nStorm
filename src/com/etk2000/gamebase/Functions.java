package com.etk2000.gamebase;

import java.util.ArrayList;

public class Functions {
	public static String[] ArrayListToString(ArrayList<String> al) {
		String[] string = new String[al.size()];
		for (int i = 0; i < al.size(); i++) {
			string[i] = al.get(i);
		}
		return string;
	}
	
	public static ArrayList<String> setupArgs(String[] arg) {
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < arg.length; i++) {
			if (!al.contains(arg[i])) {
				al.add(arg[i]);
			}
		}
		return al;
	}
}
