package com.safdesk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.safdesk.util.DB;
import com.safdesk.model.Client;


public class ClientDAO {

	private Connection dbConn;
	public ClientDAO() throws Exception{dbConn = DB.getConnection();}
	
	public void addClient(Client client){
		try {
			PreparedStatement addPS = dbConn.prepareStatement("INSERT INTO client (clientname, phonenumber, emailaddress) VALUES (?,?,?)");
			addPS.setString(1, client.getClientname());
			addPS.setString(2, client.getPhonenumber());
			addPS.setString(3, client.getEmailaddress());
			addPS.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}	
	}
	
	public void updateClient(Client client){
		try {
			PreparedStatement updPS = dbConn.prepareStatement("UPDATE client SET phonenumber=?, emailaddress=? WHERE idclient=?");
			updPS.setString(1, client.getPhonenumber());
			updPS.setString(2, client.getEmailaddress());
			updPS.setInt(3, client.getId());
			updPS.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public Client getClient(int id){
		Client client = new Client();
		try{
			PreparedStatement getPS = dbConn.prepareStatement("SELECT * FROM client where idclient=?");
			getPS.setInt(1, id);
			ResultSet rs = getPS.executeQuery();
			while(rs.next()){
				client.setId(rs.getInt("idclient"));
				client.setClientname(rs.getString("clientname"));
				client.setPhonenumber(rs.getString("phonenumber"));
				client.setEmailaddress(rs.getString("emailaddress"));
			}
		} catch (SQLException e) {e.printStackTrace();}
		return client;
	}
	
	public List<Client> getClients(){
		List<Client> clientList= new ArrayList<Client>();
		try{
			PreparedStatement getPS = dbConn.prepareStatement("SELECT * FROM client");
			ResultSet rs = getPS.executeQuery();
			while(rs.next()){
				Client client = new Client();
				client.setId(rs.getInt("idclient"));
				client.setClientname(rs.getString("clientname"));
				client.setPhonenumber(rs.getString("phonenumber"));
				client.setEmailaddress(rs.getString("emailaddress"));
				clientList.add(client);
			}
		} catch (SQLException e) {e.printStackTrace();}
		return clientList;
	}
}