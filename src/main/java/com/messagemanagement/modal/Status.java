package com.messagemanagement.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Status implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String CODE_FIELD = "code";
	public static final String MESSAGES_FIELD = "messages";
	public static final String HTTP_STATUS_CODE_FIELD = "httpStatusCode";
	
	private StatusCode code;
	private int httpStatusCode = 200;
	private List<Message> messages = new ArrayList<Message>();

	public Status() {
		setCode(StatusCode.OK);
	}

	public boolean isOK() {
		return StatusCode.OK.equals(getCode());
	}

	public boolean isWarning() {
		return StatusCode.WARN.equals(getCode());
	}

	public boolean isInfo() {
		return StatusCode.INFO.equals(getCode());
	}

	public boolean isConfirmation() {
		return getCode() != null && getCode().isConfirmation();
	}

	public boolean isError() {
		return StatusCode.ERROR.equals(getCode());
	}

	public StatusCode getCode() {
		return code;
	}

	public void setCode(StatusCode aCode) {
		code = aCode;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> aMessages) {
		if (messages == null) {
			messages = new ArrayList<Message>();
		}
		messages = aMessages;
		computeCode();
	}

	/**
	 * If a message with code ABSOLUTION is added and the overall code is error then it goes down to warn.
	 */
	public void computeCode() {
		StatusCode tempWorstCode = StatusCode.OK;
		for (Message tempMessage : getMessages()) {
			StatusCode msgCode = tempMessage.getCode();
			if (msgCode != null && msgCode.isAbsolution()) {
				if (tempWorstCode == StatusCode.ERROR) {
					tempWorstCode = StatusCode.WARN;
				}
			} else if (msgCode.ordinal() > tempWorstCode.ordinal()) {
				tempWorstCode = msgCode;
			}
		}
		if (tempWorstCode == StatusCode.ABSOLUTION) {
			tempWorstCode = StatusCode.OK;
		}
		setCode(tempWorstCode);
	}


	public Message addMessage(String message, StatusCode aCode) {
		return addMessage(message, aCode, null);
	}

	public Message addMessage(String message, StatusCode code, String number) {
		Message tempMessage = new Message();
		tempMessage.setCode(code);
		tempMessage.setText(message);
		tempMessage.setNummer(number);
		return addMessage(tempMessage);
	}

	public Message addMessage(Enum<?> aEnum, StatusCode code) {
		return addMessage(aEnum.toString(), code, aEnum.name());
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		computeCode();
		return message;
	}

	public void addMessages(List<Message> messageList) {
		getMessages().addAll(messageList);
		computeCode();
	}

	public void filterDuplicateContent() {
		if (getMessages() == null || getMessages().isEmpty()) {
			return;
		}
		
		Iterator<Message> tempIterator = getMessages().iterator();
		while (tempIterator.hasNext()) {
			Message next = tempIterator.next();
			int counter = 0;
			for (Message msg : getMessages()) {
				if (next.getText() != null && msg.getText() != null && next.getText().equals(msg.getText())) {
					++counter;
					if (counter > 1) {
						tempIterator.remove();
						break;
					}
				}
			}
		}
	}

	public StringBuilder messagesFor(StatusCode aCode) {
		StringBuilder tempResult = new StringBuilder();
		String tempDelimiter = "";
		
		for (Message tempMessage : getMessages()) {
			if (aCode == null || aCode.equals(tempMessage.getCode())) {
				tempResult.append(tempDelimiter);
				tempResult.append(tempMessage.getText());
				tempDelimiter = ";";
			}
		}
		return tempResult;
	}

	public StringBuilder messagesFor(StatusCode code, String postFix) {
		StringBuilder tempMessages = messagesFor(code);
		String tempPostFix = postFix == null ? "" : postFix;
		
		if (tempMessages.length() > 0 && tempPostFix.length() > 0) {
			tempMessages.append(";");
		}
		
		tempMessages.append(tempPostFix);
		return tempMessages;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(100);

		result.append("< ");
		result.append("code: " + getCode());
		result.append(", ");
		result.append("messages: " + getMessages());
		result.append(" >");

		return result.toString();
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

}

