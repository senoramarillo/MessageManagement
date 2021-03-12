package com.messagemanagement.modal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternData {

	boolean regex;

	/**
	 * replacement ersetzt die ganze message, wenn true, sonst nur den Match
	 */
	boolean fullReplace;

	/**
	 * if regex == true : regular expression, else literal pattern.
	 */
	String patternString;

	Pattern pattern;

	/**
	 * Ersatz f�r den Text. Ist fullreplace=true, wird der volle Text ersetzt,
	 * andernfalls nur das Pattern. Handelt es sich um Regex, kann das Replacement
	 * �ber $n die n-te Group des Matches referenzieren.
	 */
	String replacement;

	public PatternData(boolean regexFlag, boolean fullReplaceFlag, String patternString, String replacement) {
		this.regex = regexFlag;
		this.fullReplace = fullReplaceFlag;
		this.patternString = patternString;
		this.replacement = replacement;

		if (regex) {
			pattern = Pattern.compile(patternString);
		}
	}

	public String applyPattern(String messageText) {
		if (isRegex()) {
			Matcher matcher = matcher(messageText);
			
			if (matcher.find()) {
				if (isDelete()) {
					messageText = null;
				} else if (isFullReplace()) {
					messageText = getReplacement();
				} else {
					messageText = matcher.replaceAll(getReplacement());
				}
			}
		} else if (messageText.contains(getPattern())) {
			if (isDelete()) {
				messageText = null;
			} else if (isFullReplace()) {
				messageText = getReplacement();
			} else {
				messageText = messageText.replace(getPattern(), getReplacement());
			}
		}
		return messageText;
	}

	public boolean isDelete() {
		return replacement == null;
	}

	public boolean isRegex() {
		return regex;
	}

	public void setRegex(boolean regex) {
		this.regex = regex;
	}

	public boolean isFullReplace() {
		return fullReplace;
	}

	public void setFullReplace(boolean fullReplace) {
		this.fullReplace = fullReplace;
	}

	public String getPattern() {
		return patternString;
	}

	public void setPattern(String pattern) {
		this.patternString = pattern;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	public Matcher matcher(String text) {
		return pattern == null ? null : pattern.matcher(text);
	}

}
