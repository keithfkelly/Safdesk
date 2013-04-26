package com.safdesk.dao;

import java.sql.*;

import com.safdesk.model.Catagory;
import com.safdesk.util.DB;

public class CatagoryDAO {
	
	private Connection dbConn;
	
	public CatagoryDAO() throws Exception{new DB(); dbConn = DB.getConnection(); }
	
	public void addCat(Catagory catagory){
		try {
			PreparedStatement addPS = dbConn.prepareStatement("INSERT INTO catagories(catagoryname) VALUES(?)");
			addPS.setString(1, catagory.getCatagoryname());
			addPS.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
	}
}
