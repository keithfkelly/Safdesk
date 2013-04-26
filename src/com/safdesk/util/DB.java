package com.safdesk.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DB {
	
	static Map<String, String> propMap = Props.get();
	private static String connDriver = propMap.get("dbDriver");
	private static String connURL = propMap.get("dbURL");
	private static String connUser = propMap.get("dbUser");
	private static String connPass = propMap.get("dbPass");

	private static Connection connection = null;

	public static Connection getConnection() throws Exception {
		if (connection != null)
			return connection;
		else {
			Class.forName(connDriver).newInstance();
			connection = DriverManager.getConnection(connURL,connUser,connPass);
			return connection;
		}
	}

	public static String get(String sql) throws Exception{
		Connection dbConn = getConnection();
		String ret = null;
		Statement s = dbConn.createStatement();
		ResultSet rs = s.executeQuery(sql);
		rs.first();
		ret = rs.getString(1);
		return ret;
	}
	
	public static void doU(String u) throws Exception{
		Connection dbConn = getConnection();
		Statement st= dbConn.createStatement();
		st.executeUpdate(u);
	}
	
	public static ResultSet getAll(String table){
		Connection dbConn;
		ResultSet rs = null;
		try {
			dbConn = DB.getConnection();
			Statement s = dbConn.createStatement();
			rs = s.executeQuery("Select * FROM "+table);
		}catch(SQLException e){e.printStackTrace();} catch (Exception e) {e.printStackTrace();}
		return rs;
	}
	
	public static ResultSet getRS(String q) throws Exception{
		Connection dbConn = getConnection();
		Statement st=dbConn.createStatement();
		ResultSet rs=st.executeQuery(q);
		return rs;
	}
	
	
}