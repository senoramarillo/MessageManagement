package com.messagemanagement.modal;

public enum StatusCode {

	OK, INFO, CONFIRMATION, WARN, ERROR, ABSOLUTION;
	
	@Override
	public String toString() {
		return this.name();
	}

	public boolean isAbsolution() {
		return false;
	}
	
	public boolean isConfirmation() {
		return false;
	}
}
