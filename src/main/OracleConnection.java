package main;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.jdbc.pool.OracleDataSource;


public class OracleConnection {
	static Connection connection;
	String driver = "ojdbc5";
	
	// TODO UPDATE VARIABLES BELOW FOR "REAL" DATABASE----------------------
	static String url = "jdbc:oracle:thin:@oracledb.cfps2jr8efah.us-west-2.rds.amazonaws.com:1521:oracledb";
	static String username = "oracleUser";
	static String password = "password";
	static String tableName = "STUDENTS";
	static String cwidColumn = "cwid";
	static String firstNameColumn = "firstName";
	static String lastNameColumn = "lastName";
	static String emailColumn = "email";
	static String departmentCodeColumn = "departmentCode";
	// -----------------------------------------------------------------
	
	OracleConnection() throws SQLException {
		connection = null;
	}
	
	public void getConnection() throws SQLException{
        OracleDataSource ds;
        ds = new OracleDataSource();
        ds.setURL(url);
        connection = ds.getConnection(username, password);
    }
	
	public void close() throws SQLException {
		connection.close();
	}
	
	public ArrayList<String> select(String searchStr) throws SQLException {
		ArrayList<String> results = new ArrayList<String>();
		
		String sql = "SELECT " + cwidColumn + ", " + firstNameColumn + ", " + lastNameColumn + ", " +
				emailColumn + ", " + departmentCodeColumn + " FROM " + tableName + " WHERE " + 
				cwidColumn + "=" + searchStr;
		Statement statement = connection.createStatement();
		ResultSet rows = statement.executeQuery(sql);

		if (rows.next()) {
			String cwid = rows.getString(cwidColumn);
			String firstName = rows.getString(firstNameColumn);
			String lastName = rows.getString(lastNameColumn);
			String email = rows.getString(emailColumn);
			String departmentCode = rows.getString(departmentCodeColumn);
			
			results.add(cwid);
			results.add(firstName);
			results.add(lastName);
			results.add(email);
			results.add(departmentCode);
			
			System.out.println("CWID: " + cwid);
		}
		
		System.out.println(results);
		
		return results;
	}
}