package com.messagemanagement.modal;

import java.util.Arrays;
import java.util.List;

public class StringUtil {

	private StringUtil() {
		
	}

	public static final List<String> FALSE_CODES = Arrays.asList("0", "n", "nein", "no", "non", "f", "false", "-",
			"off");

	public static boolean isEmpty(String str, boolean ignoreWhiteSpace) {
		return str == null || str.length() == 0 || (ignoreWhiteSpace && str.trim().length() == 0);
	}

	public static boolean isEmpty(String str) {
		return isEmpty(str, false);
	}

	public static boolean asBoolean(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		}
		return !FALSE_CODES.contains(str.toLowerCase());
	}

}