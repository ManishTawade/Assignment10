package com.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.Service.ContactNotFoundException;
import com.Service.ContactService;
import com.pojo.Contact;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ContactService cs=new ContactService();
		List<Contact> ls=new ArrayList<>();
		cs.readContactsFromFile(ls,"Contact.txt");
		
		for (Contact contact : ls) {
			System.out.println(contact.toString());
		}
		
		
		List<String> l=new ArrayList<>();
		l.add("9125348947");
		l.add("457821");
		Contact s=new Contact(4,"Suresh","suresh@gmail.com",l);
		cs.addContact(s, ls);
		System.out.println("\nNew Contact Added:\n");
		for (Contact contact : ls) {
			System.out.println(contact.toString());
		}
		
		
		System.out.println("\nContact Removed:\n");
	
		try {
			cs.removeContact(s, ls);
		} catch (ContactNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (Contact contact : ls) {
			System.out.println(contact.toString());
		}
		
		System.out.println("\nSearch Contact Radha:\n");
		Contact m = null;
		try {
			m = cs.searchContactByName("Radha", ls);
			
		} catch (ContactNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(m.toString());
		
		System.out.println("\nSearch ContactNumber 234:\n");
		List<Contact> nn = null;
		try {
			nn = cs.SearchContactByNumber("234", ls);
		} catch (ContactNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Contact contact : nn) {
			System.out.println(contact.toString());
		}
		
		System.out.println("\nAdded Contact Number 1234 to ID-3:\n");
		cs.addContactNumber(3,"1234",ls);
		for (Contact contact : ls) {
			System.out.println(contact.toString());
		}
		
		System.out.println("\nContacts Sorted By Name:\n");
		cs.sortContactsByName(ls);
		for (Contact contact : ls) {
			System.out.println(contact.toString());
		}
		
		
		System.out.println("\nSerializaton:");
		cs.serializeContactDetails(ls, "ContactDetails");
		System.out.println("\nDeserializaton:");
		nn=null;
		nn=cs.deserializeContactDetails("ContactDetails");
		System.out.println("\nDeserialized Data:\n");
		for (Contact contact : nn) {
			System.out.println(contact.toString());
		}
		
		System.out.println("\nAdd Data from Database:\n");
		Set<Contact> st=cs.populateContactFromDb();
		for (Contact contact : st) {
			System.out.println(contact.toString());
		}
		
		System.out.println("\nAdd new Data to Existing Data:\n");
		cs.addContacts(ls, st);
		for (Contact contact : ls) {
			System.out.println(contact.toString());
		}
		
		
	}

}
