package com.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	static Connection c=null;
	static String dbURL="jdbc:oracle:thin:@localhost:1521:xe";
	static String username="db_user";
	static String password="db_user";
	
	private DBConnection() {
		
	}
	
	public static Connection getConnection() {
		if(c==null) {
			try {
				c=DriverManager.getConnection(dbURL,username,password);			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return c;
	}
	
	public static void closeConnection() {
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}