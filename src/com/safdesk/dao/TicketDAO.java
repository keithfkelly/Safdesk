package com.safdesk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.safdesk.model.Ticket;
import com.safdesk.util.DB;
import com.safdesk.comms.SendMail;


public class TicketDAO {

	private Connection dbConn;

	public TicketDAO() throws Exception{dbConn = DB.getConnection();}

	public void addTicket(Ticket ticket){
		try{
			SimpleDateFormat nowDate = new SimpleDateFormat();
			nowDate.applyPattern("yyyy-MM-dd");
			Date now = new Date();
			String dateOpen = nowDate.format(now);
			PreparedStatement addPS = dbConn.prepareStatement("INSERT into tickets(forclient, catagory, ticketdesc, ticketdata, ticketopendate) VALUES (?,?,?,?,?)");
			addPS.setString(1, DB.get("SELECT  idclient FROM client where clientname = \""+ticket.getClient()+"\""));
			addPS.setString(2, DB.get("SELECT  idcatagories FROM catagories where catagoryname = \""+ticket.getCat()+"\""));
			addPS.setString(3,ticket.getDesc());
			addPS.setString(4,ticket.getData());
			addPS.setString(5,dateOpen);
			addPS.executeUpdate();
			String body = ""+ticket.getClient()+",\n\nThank you for opening a ticket with Safdesk.\nWe Hope to Action your ticket as soon as possible!"
							+"\nYour Ticket Details are as Follows:"
							+"\nDescription:\n"+ticket.getDesc()
							+"\nDetails:\n"+ticket.getData()
							+"\n\nRegards,\nThe SafDesk Team";
			SendMail.send("SafDesk - New Ticket Created", 
						DB.get("SELECT  emailaddress FROM client where clientname = \""+ticket.getClient()+"\""),
						body);
		}catch(SQLException e){e.printStackTrace();} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	public void deleteTicket(int tickID){
		PreparedStatement delPS;
		try {
			delPS = dbConn.prepareStatement("DELETE FROM tickets where idtickets=?");
			delPS.setInt(1, tickID);
			delPS.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
	}

	public void updateTicket(Ticket ticket){
		PreparedStatement updPS;
		try{
		 updPS= dbConn.prepareStatement("UPDATE tickets SET ticketdata=? WHERE idtickets=?");
		updPS.setString(1, ticket.getData());
		updPS.setInt(2, ticket.getId());
		updPS.executeUpdate();
		}catch(SQLException e){e.printStackTrace();}
	}
	
	public void closeTicket(Ticket ticket){
		PreparedStatement closePS;
		SimpleDateFormat nowDate = new SimpleDateFormat();
		nowDate.applyPattern("yyyy-MM-dd");
		Date now = new Date();
		String dateClose = nowDate.format(now);
		try{
			closePS= dbConn.prepareStatement("UPDATE tickets SET ticketstatus=\"Closed\", ticketclosedate=? WHERE idtickets=?");
			closePS.setString(1, dateClose);
			closePS.setInt(2, ticket.getId());
			closePS.executeUpdate();
		}catch(SQLException e){e.printStackTrace();}
	}

	public List<Ticket> getTickets(){
		List<Ticket> tickList = new ArrayList<Ticket>();
		try{
			Statement s = dbConn.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM ticketview;");
			while(rs.next()){
				if(rs.getString("status").equalsIgnoreCase("open")){
				Ticket ticket = new Ticket();
				ticket.setId(rs.getInt("idtickets"));
				ticket.setCat(rs.getString("Cat"));
				ticket.setClient(rs.getString("Client"));
				ticket.setDesc(rs.getString("ticketdesc"));
				ticket.setData(rs.getString("ticketdata"));
				ticket.setDaysOpen(calcDaysOpen(DateTime.parse(rs.getString("ticketopendate"))));
				tickList.add(ticket);
				}
			}
		}catch(SQLException e){e.printStackTrace();}
		return tickList;	
	}

	public Ticket getTicket(int tickID){
		Ticket ticket = new Ticket();
		try{
			PreparedStatement getPS = dbConn.prepareStatement("SELECT * FROM ticketview WHERE idtickets=?");
			getPS.setInt(1, tickID);
			ResultSet rs = getPS.executeQuery();
			if(rs.next()){
				ticket.setId(rs.getInt("idtickets"));
				ticket.setCat(rs.getString("Cat"));
				ticket.setClient(rs.getString("Client"));
				ticket.setDesc(rs.getString("ticketdesc"));
				ticket.setData(rs.getString("ticketdata"));
				ticket.setDaysOpen(calcDaysOpen(DateTime.parse(rs.getString("ticketopendate"))));
			}
		}catch(SQLException e){e.printStackTrace();}
		return ticket;
	}
	private int calcDaysOpen(DateTime x){
		DateTime now = new DateTime();
		int daysOpen = Days.daysBetween(x, now).getDays();
		return daysOpen;
	}
}

