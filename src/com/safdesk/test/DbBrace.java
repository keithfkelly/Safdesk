package com.safdesk.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.joda.time.*;

import com.safdesk.dao.*;
import com.safdesk.model.*;


public class DbBrace {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception{
		
		RenewalDAO dao = new RenewalDAO();
		Renewal renewal = dao.getRenewalbyID(24);
		System.out.println(renewal.getDaysUntilDue());
		
		
		
		
		/*TicketDAO dao = new TicketDAO();
		Ticket ticket = dao.getTicket(41);
		System.out.println(ticket.getData());*/
		
		
		/*RenewalDAO dao = new RenewalDAO();
		Renewal renewal = dao.getRenewalbyID(24);
		int clientId = Integer.parseInt(DB.get("SELECT idclient FROM client where clientname = \""+renewal.getClient()+"\""));
		System.out.println(clientId);*/
		
		
		
		
		/*RenewalDAO dao = new RenewalDAO();
		Renewal myRenewal = new Renewal();
		myRenewal.setAlertType(1);
		
		int x = Integer.parseInt(DB.get("SELECT idalert FROM alert where alertname=\"SMS\""));
		System.out.print(x);*/
		
		
		/*SimpleDateFormat nowDate = new SimpleDateFormat();
		nowDate.applyPattern("yyyy-MM-dd");
		Date now = new Date();
		String dateClose = nowDate.format(now);
		System.out.println(dateClose);
		TicketDAO tick = new TicketDAO();
		Ticket x = tick.getTicket(4);
		tick.closeTicket(x);*/
		
		
		/*new DB();
		new Security();
		ResultSet rs = DB.getAll("users");
		String password1 = "Password";
		rs.next();
		if(rs.getString("Password").equals(Security.encrypt(password1))){
			System.out.println("Passwords Match");
		}else{
			System.out.println("Passwords Don't Match");
		}*/


	}

	public static void daoTest() throws Exception{
		TicketDAO dao = new TicketDAO();
		List<Ticket> tickList = dao.getTickets();
		for(int i=0;i<tickList.size();i++){
			System.out.println("ID: "+tickList.get(i).getId()+" Desc: "+tickList.get(i).getDesc());
		}
	}
}
