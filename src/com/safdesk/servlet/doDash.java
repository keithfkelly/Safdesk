package com.safdesk.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.safdesk.dao.RenewalDAO;
import com.safdesk.dao.TicketDAO;
import com.safdesk.model.Renewal;
import com.safdesk.model.Ticket;

@SuppressWarnings("serial")
public class doDash extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		TicketDAO tickDao = null;
		RenewalDAO renewDao = null;
		try {
			tickDao = new TicketDAO();
			renewDao = new RenewalDAO();
		} catch (Exception e) {e.printStackTrace();}
		List<Ticket> tickList = tickDao.getTickets();
		List<Renewal> renewList = renewDao.getRenewals();
		request.setAttribute("tickets", tickList);
		request.setAttribute("renewals", renewList);
		request.getRequestDispatcher("/dash.jsp").forward(request, response);
	}
}


