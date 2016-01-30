package de.dinkov.vlsapp.samples.helpclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {

	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "dls";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "";

	public DBConnection() {
		// TODO Auto-generated constructor stub
	}

	public int validateLogin(String username, String userpass) {
		Connection conn = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			PreparedStatement st = conn.prepareStatement("SELECT * FROM  user where username=? AND password=?");
			st.setString(1, username);
			st.setString(2, userpass);

			ResultSet res = st.executeQuery();

			res.last();
			if (res.getRow() == 1) {
				int userID = res.getInt(1);
				conn.close();
				return userID;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
