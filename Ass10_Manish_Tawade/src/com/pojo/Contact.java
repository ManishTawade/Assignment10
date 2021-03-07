package com.pojo;

import java.io.Serializable;
import java.util.List;

public class Contact implements Serializable{
	private int contactID;
	private String ContactName;
	private String emailAddress;
	private List<String> contactNumber;
	public int getContactID() {
		return contactID;
	}
	public void setContactID(int contactID) {
		this.contactID = contactID;
	}
	public String getContactName() {
		return ContactName;
	}
	public void setContactName(String contactName) {
		ContactName = contactName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public List<String> getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(List<String> contactNumber) {
		this.contactNumber = contactNumber;
	}
	@Override
	public String toString() {
		return "Contact [contactID=" + contactID + ", ContactName=" + ContactName + ", emailAddress=" + emailAddress
				+ ", contactNumber=" + contactNumber + "]";
	}
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Contact(int contactID, String contactName, String emailAddress, List<String> contactNumber) {
		super();
		this.contactID = contactID;
		ContactName = contactName;
		this.emailAddress = emailAddress;
		this.contactNumber = contactNumber;
	}

	
}
