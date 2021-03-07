package com.Service;

public class ContactNotFoundException extends Exception {

	public ContactNotFoundException(String s) {
		super(s);
		System.out.println("Contact NOT FOUND");
	}

}
