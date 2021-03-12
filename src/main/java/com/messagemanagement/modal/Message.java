package com.messagemanagement.modal;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String CODE_FIELD = "code";
	public static final String NUMMER_FIELD = "nummer";
	public static final String TEXT_FIELD = "text";
	public static final String TIP_FIELD = "tip";

	private StatusCode code;
	private String nummer;
	private String text;
	private String tip;

	public Message() {
		
	}

	public Message(Enum<?> aEnum, StatusCode statusCode) {
		setCode(statusCode);
		setNummer(aEnum.name());
		setText(aEnum);
	}

	public String getText() {
		return text;
	}

	public void setText(String aText) {
		text = aText;
	}
	
	public void setText(Enum<?> aEnum) {
		text = aEnum.toString();
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String aNummer) {
		nummer = aNummer;
	}

	public StatusCode getCode() {
		return code;
	}

	public void setCode(StatusCode aCode) {
		code = aCode;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String aTip) {
		tip = aTip;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(100);
		sb.append(code).append(": ").append(text);

		if (nummer != null && !nummer.trim().isEmpty()) {
			sb.append(" <").append(nummer).append(">");
		}

		return sb.toString();
	}

}
