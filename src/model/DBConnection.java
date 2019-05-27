package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection createConnection() {
		try {
			String username = "sa";
			String password = "123";
			String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433; databaseName = pro104";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		
		return null;
		
	}
}
