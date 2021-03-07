package com.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.DatabaseConnection.DBConnection;
import com.pojo.Contact;

public class ContactService {
	
	public void addContact(Contact contact,List<Contact> contacts) {
		contacts.add(contact);
	}
	
	public void removeContact(Contact contact, List<Contact> contacts) throws ContactNotFoundException{
		Iterator<Contact> itr=contacts.iterator();
		boolean flag=false;
		while(itr.hasNext()) {
			Contact r=itr.next();
			if(r.getContactID()==contact.getContactID()) {
				itr.remove();
				flag=true;
			}
		}

		if(flag==false)
			new ContactNotFoundException("No such Contact found in the List");
	}
	
	public Contact searchContactByName(String name, List<Contact> contact) throws ContactNotFoundException{
		Contact c = null;
		boolean flag=false;
		for (Contact contact2 : contact) {
			if(contact2.getContactName().equals(name)) {
				c=contact2;
				flag=true;
			}
		}
		if(flag==false)
			new ContactNotFoundException("No such Contact found in the List");
		return c;
	}
	
	public List<Contact> SearchContactByNumber(String number, List<Contact> contact) throws ContactNotFoundException{
		List<Contact> ls=new ArrayList<>();
		boolean flag=false;
		
		Iterator<Contact> itr=contact.iterator();
		while(itr.hasNext()) {
			Contact c=itr.next();
			List<String> l=c.getContactNumber();
			int n=0;
			if(l!=null) {
				for (String s : l) {
					if(s.contains(number)) {
						n++;
						if(n==1)
							addContact(c,ls);
						flag=true;
					}
				}
			}
		}

//		for (Contact contact2 : contact) {
//			List<String> l=contact2.getContactNumber();
//			for (String s : l) {
//				if(s.contains(number)) {
//					ls.add(contact2);
//					flag=true;
//				}
//			}
//		}
		if(flag==false)
			new ContactNotFoundException("No such Contact found in the List");
		
		return ls;
	}
	
	public void addContactNumber(int contactId, String contactNo, List<Contact> contacts) {
		Iterator<Contact> itr=contacts.iterator();
		while(itr.hasNext()) {
			Contact c=itr.next();
			if(c.getContactID()==contactId) {
				List<String> l=c.getContactNumber();
				if(l==null)
					l=new ArrayList<>();
				l.add(contactNo);
				c.setContactNumber(l);
			}
		}
	}
	
	public void sortContactsByName(List<Contact> contacts) {
		class SortByName implements Comparator<Contact>{

			public int compare(Contact o1, Contact o2) {
				return(o1.getContactName().compareToIgnoreCase(o2.getContactName()));
			}

		}
		Collections.sort(contacts,new SortByName());
	}
	
	public void readContactsFromFile(List<Contact> contacts, String fileNAme) {
		File file=new File(fileNAme);
		try(Scanner sc=new Scanner(file)) {
			while(sc.hasNext()) {
				Contact c=new Contact();
				String[] s=sc.nextLine().split(",");
				c.setContactID(Integer.parseInt(s[0]));
				c.setContactName(s[1]);
				c.setEmailAddress(s[2]);
				if(s.length==3)
					c.setContactNumber(null);
				else {
					List<String> l=new ArrayList<>();
					for(int i=3;i<s.length;i++) {			
						l.add(s[i]);
					}
					c.setContactNumber(l);
				}	
				contacts.add(c);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void serializeContactDetails(List<Contact> contacts, String fileName) {
		File file = new File(fileName);
		
		try (FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream out = new ObjectOutputStream(fos)){
			
			out.writeObject(contacts);
			System.out.println("\nSerialization Complete...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Contact> deserializeContactDetails(String fileName) {
		File file = new File(fileName);
		List<Contact> cont = new ArrayList<>();
		
		try(FileInputStream fis = new FileInputStream(file);
				ObjectInputStream in = new ObjectInputStream(fis)) {
	
			cont = (ArrayList<Contact>) in.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\nDeserialization Complete...");
		return cont;
	}
	
	public Set<Contact> populateContactFromDb(){
		Connection cn=DBConnection.getConnection();
		Set<Contact> ls=new HashSet<>();
		Statement stmt;
		try {
			stmt = cn.createStatement();
			String sq = "select * from contact_tbl";
			ResultSet rs = stmt.executeQuery(sq);
			Contact m=null;
			while(rs.next()) {
				m=new Contact();
				m.setContactID(rs.getInt(1));
				m.setContactName(rs.getString(2));
				m.setEmailAddress(rs.getString(3));
				if(rs.getString(4)==null)
					m.setContactNumber(null);
				else
					m.setContactNumber(StringToList(rs.getString(4)));
				ls.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ls;
		
	}
	
	public List<String> StringToList(String s) {
		List<String> l=new ArrayList<>();
		String[] ss=s.split(",");
		for (String string : ss) {
			l.add(string);
		}
		return l;
	}
	
	public boolean addContacts(List<Contact> existingContact,Set<Contact> newContacts) {
		if(existingContact.addAll(newContacts))
			return true;
		return false;
		
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		DBConnection.closeConnection();
		super.finalize();
	}
}
