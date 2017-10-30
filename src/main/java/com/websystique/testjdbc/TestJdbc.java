package com.websystique.testjdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

	public static void main(String[] args) {
		String jdbcUrl = "jdbc:mysql://localhost:3306/websystique?useSSL=false";
		String user = "hbstudent";
		String pass = "hbstudent";
		try {
			System.out.println("Connectiong to database: " + jdbcUrl);
			Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);
			myConn.close();
			System.out.println("Connection successful!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
