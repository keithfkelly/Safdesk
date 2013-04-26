package com.safdesk.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safdesk.dao.TicketDAO;
import com.safdesk.model.Ticket;

public class TicketController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static String INSERT_OR_EDIT = "/ticket.jsp";
	private static String LIST_TICKET = "/listticket.jsp";
	private TicketDAO dao;

	public TicketController() throws Exception{
		super();
		dao = new TicketDAO();
	}

	@Override
	protected void doGet(HttpServletRequest tickReq, HttpServletResponse tickRes) throws ServletException, IOException {
		String forward="";
		String action = tickReq.getParameter("action");

		if (action.equalsIgnoreCase("delete")){
			int tickId = Integer.parseInt(tickReq.getParameter("idtickets"));
			dao.deleteTicket(tickId);
			forward = LIST_TICKET;
			tickReq.setAttribute("tickets", dao.getTickets());    
		} else if (action.equalsIgnoreCase("edit")){
			tickReq.setAttribute("edit", "edit");
			forward = INSERT_OR_EDIT;
			int tickId = Integer.parseInt(tickReq.getParameter("idtickets"));
			Ticket ticket = dao.getTicket(tickId);
			tickReq.setAttribute("ticket", ticket);
		} else if (action.equalsIgnoreCase("allTickets")){
			forward = LIST_TICKET;
			List<Ticket> tickList = dao.getTickets();
			Collections.sort(tickList, Sort);
			tickReq.setAttribute("tickets", tickList);
		} else if(action.equalsIgnoreCase("close")){
			int tickId = Integer.parseInt(tickReq.getParameter("idtickets"));
			Ticket ticket = dao.getTicket(tickId);
			dao.closeTicket(ticket);
			tickReq.setAttribute("tickets", dao.getTickets());
			forward = LIST_TICKET;
		}else {
			forward = INSERT_OR_EDIT;
		}

		RequestDispatcher view = tickReq.getRequestDispatcher(forward);
		view.forward(tickReq, tickRes);
	}

	@Override
	protected void doPost(HttpServletRequest tickReq, HttpServletResponse tickRes) throws ServletException, IOException {
		Ticket ticket = new Ticket();
		ticket.setData(tickReq.getParameter("ticketdata"));
		String id = tickReq.getParameter("idtickets"); 
		if(id==null||id.isEmpty()){
			ticket.setCat(tickReq.getParameter("ticketcat"));
			ticket.setClient(tickReq.getParameter("ticketclient"));
			ticket.setDesc(tickReq.getParameter("ticketdesc"));
			dao.addTicket(ticket);
		}else{
			ticket.setId(Integer.parseInt(id));
			dao.updateTicket(ticket);
		}
		RequestDispatcher rd = tickReq.getRequestDispatcher(LIST_TICKET);
		tickReq.setAttribute("tickets", dao.getTickets());
		rd.forward(tickReq, tickRes);
	}
	
    private static Comparator<Ticket> Sort = new Comparator<Ticket>()
    {
        public int compare(Ticket t1, Ticket t2)
        {
            return t1.getId() - t2.getId();
        }
    };

}
