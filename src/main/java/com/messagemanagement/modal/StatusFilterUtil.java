package com.messagemanagement.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class StatusFilterUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private static StatusFilterUtil instance;
	private final transient ResourceBundle bundle;

	private static final String CONTAINS_EXTENSION = ".contains";
	private static final String DELETE_EXTENSION = ".delete";
	private static final String REGEX_EXTENSION = ".regexp";
	private static final String REPLACE_EXTENSION = ".replace";
	private static final String REPLACE_REF_EXTENSION = ".replaceRef";

	public StatusFilterUtil() {
		this("response-filter");
	}

	public StatusFilterUtil(String bundleName) {
		bundle = ResourceBundle.getBundle(bundleName);
	}

	public static StatusFilterUtil getInstance() {
		if (instance == null) {
			instance = new StatusFilterUtil();
		}
		return instance;
	}

	public void filterInterneInformationen(Status statusVO) {
		List<String> patternList = createPatternList();
		HashMap<String, PatternData> patternMap = createPatternValueMap(patternList);
		Iterator<Message> messageIterator = statusVO.getMessages().iterator();

		while (messageIterator.hasNext()) {
			Message message = messageIterator.next();
			String messageText = message.getText();

			if (messageText != null) {
				for (String patternName : patternList) {
					PatternData patternData = patternMap.get(patternName);
					messageText = patternData.applyPattern(messageText);
					message.setText(messageText);
				}
			} else {
				messageIterator.remove();
			}
		}
		System.out.println(statusVO);
	}

	private List<String> createPatternList() {
		List<String> arrayList = new ArrayList<>();

		for (String key : bundle.keySet()) {
			if (key.contains("patterns")) {
				arrayList = Arrays.asList(bundle.getString(key).split(","));
			}
		}
		return arrayList;
	}

	private String getString(String key) {
		if (bundle.containsKey(key)) {
			return bundle.getString(key);
		}
		return null;
	}

	private HashMap<String, PatternData> createPatternValueMap(List<String> patternList) {
		HashMap<String, PatternData> map = new HashMap<>();

		for (String patternName : patternList) {
			String contains = getString(patternName + CONTAINS_EXTENSION);
			String regexp = getString(patternName + REGEX_EXTENSION);
			String replaceRef = getString(patternName + REPLACE_REF_EXTENSION);
			String replacement = getString(patternName + REPLACE_EXTENSION);
			boolean delete = StringUtil.asBoolean(getString(patternName + DELETE_EXTENSION));
			
			if (replacement == null && !StringUtil.isEmpty(replaceRef)) {
				replacement = getString(replaceRef);
			}

			PatternData patternData = null;
			if (!StringUtil.isEmpty(regexp)) {
				patternData = new PatternData(true, false, regexp, delete ? null : replacement);
			} else if (!StringUtil.isEmpty(contains)) {
				patternData = new PatternData(false, true, contains, delete ? null : replacement);
			}
			if (patternData != null) {
				map.put(patternName, patternData);
			}
		}

		return map;
	}

}
