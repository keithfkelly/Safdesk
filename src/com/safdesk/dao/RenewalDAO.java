package com.safdesk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.safdesk.comms.SendMail;

import com.safdesk.util.DB;
import com.safdesk.model.Renewal;

public class RenewalDAO {
	private Connection dbConn;

	public RenewalDAO() throws Exception{dbConn = DB.getConnection();}

	public void addRenewal(Renewal renewal){
		try {
			PreparedStatement addPS = dbConn.prepareStatement("INSERT INTO renewals (forclient, catagory, renewaldue, alerttype) VALUES(?,?,?,?)");
			addPS.setString(1, DB.get("SELECT idclient FROM client where clientname = \""+renewal.getClient()+"\""));
			addPS.setString(2, DB.get("SELECT idcatagories FROM catagories where catagoryname = \""+renewal.getCatagory()+"\""));
			addPS.setString(3, renewal.getDateDue());
			addPS.setInt(4, renewal.getAlertType());
			addPS.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();} catch (Exception e) {e.printStackTrace();}
	}
	
	public void editRenewal(Renewal renewal){
		try{
			PreparedStatement editPS = dbConn.prepareStatement("UPDATE renewals SET renewaldue=?, alerttype=? WHERE idrenewals=?");
			editPS.setString(1, renewal.getDateDue());
			editPS.setInt(2, renewal.getAlertType());
			editPS.setInt(3, renewal.getId());
			editPS.executeUpdate();
			int clientId = Integer.parseInt(DB.get("SELECT idclient FROM client where clientname = \""+renewal.getClient()+"\""));
			String subject="Renewal: "+renewal.getCatagory()+" - Date Change Notification";
			String to = DB.get("select emailaddress from client where idclient='"+clientId+"'");
			String body = "Hello "+renewal.getClient()+",\n\nYour "+renewal.getCatagory()+" renewal has been changed to occour on:\n\n"+renewal.getDateDue()
							+"\n\nThank you for Using Safdesk\n\nThe SafDesk Team.";
			SendMail.send(subject, to, body);
		}catch(SQLException e){e.printStackTrace();} catch (NumberFormatException e) {e.printStackTrace();} catch (Exception e) {e.printStackTrace();}
	}

	public void delRenewal(int renewalID){
		PreparedStatement delPS;
		try {
			delPS = dbConn.prepareStatement("DELETE FROM renewals where idrenewals=?");
			delPS.setInt(1, renewalID);
			delPS.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
	}

	public List<Renewal> getRenewals(){
		List<Renewal> renewList = new ArrayList<Renewal>();
		try{
			Statement s = dbConn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM renewalview;");
			while(rs.next()){
				Renewal renewal = new Renewal();
				renewal.setId(rs.getInt("ID"));
				renewal.setCatagory(rs.getString("Catagory"));
				renewal.setClient(rs.getString("Client"));
				renewal.setDateDue(rs.getString("DateDue"));
				renewal.setAlertType(rs.getInt("Alert"));
				renewList.add(renewal);
			}
		}catch(SQLException e){e.printStackTrace();}
		return renewList;
	}

	public Renewal getRenewalbyID(int id){
		Renewal renewal = new Renewal();
		try{
			PreparedStatement getPS = dbConn.prepareStatement("SELECT * FROM renewalview where ID=?");
			getPS.setInt(1, id);
			ResultSet rs = getPS.executeQuery();
			if(rs.next()){
				renewal.setId(rs.getInt("ID"));
				renewal.setCatagory(rs.getString("Catagory"));
				renewal.setClient(rs.getString("Client"));
				renewal.setDateDue(rs.getString("DateDue"));
				renewal.setAlertType(rs.getInt("Alert"));
			}
		}catch(SQLException e){e.printStackTrace();}
		return renewal;
	}
}
