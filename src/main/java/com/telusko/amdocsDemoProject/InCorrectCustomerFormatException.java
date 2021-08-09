package com.telusko.amdocsDemoProject;

/* Custom Exception */
public class InCorrectCustomerFormatException extends Exception {

	public InCorrectCustomerFormatException() {
		super("Invalid customer name. It should not contain & and % as special characters");
	}
	public InCorrectCustomerFormatException(String message) {
		super(message);
	}
}
