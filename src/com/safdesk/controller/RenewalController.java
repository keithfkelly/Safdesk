package com.safdesk.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safdesk.dao.RenewalDAO;
import com.safdesk.model.Renewal;

public class RenewalController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static String INSERT_OR_EDIT = "/renewal.jsp";
	private static String LIST_RENEWAL = "/listrenewal.jsp";
	private RenewalDAO dao;
	
	public RenewalController() throws Exception{
		super();
		dao = new RenewalDAO();
	}
	
	public void doGet(HttpServletRequest renewReq, HttpServletResponse renewRes) throws ServletException, IOException{
		String forward="";
		String action = renewReq.getParameter("action");
		
		if(action.equalsIgnoreCase("delete")){
			int renewalID = Integer.parseInt(renewReq.getParameter("idrenewals"));
			dao.delRenewal(renewalID);
			forward = LIST_RENEWAL;
			renewReq.setAttribute("renewals", dao.getRenewals());
		}else if(action.equalsIgnoreCase("allRenewals")){
			forward = LIST_RENEWAL;
			renewReq.setAttribute("renewals", dao.getRenewals());	
		}else if(action.equalsIgnoreCase("edit")){
			forward = INSERT_OR_EDIT;
			renewReq.setAttribute("edit", "edit");
			int renewalID = Integer.parseInt(renewReq.getParameter("idrenewal"));
			Renewal renewal = dao.getRenewalbyID(renewalID);
			renewReq.setAttribute("renewal", renewal);
		}else{
			forward=INSERT_OR_EDIT;
		}
		RequestDispatcher view = renewReq.getRequestDispatcher(forward);
		view.forward(renewReq, renewRes);
	}
	
	public void doPost(HttpServletRequest renewReq, HttpServletResponse renewRes) throws ServletException, IOException{
		Renewal renewal = new Renewal();
		String id = renewReq.getParameter("idrenewal");
		renewal.setDateDue(renewReq.getParameter("renewDue"));
		renewal.setAlertType(Integer.parseInt(renewReq.getParameter("renewAlert")));
		renewal.setCatagory(renewReq.getParameter("renewcat"));
		renewal.setClient(renewReq.getParameter("renewclient"));
		if(id==null||id.isEmpty()){
			dao.addRenewal(renewal);
		}else{
			renewal.setId(Integer.parseInt(id));
			dao.editRenewal(renewal);
		}
		RequestDispatcher view = renewReq.getRequestDispatcher(LIST_RENEWAL);
		renewReq.setAttribute("renewals", dao.getRenewals());
		view.forward(renewReq, renewRes);
	}
	
	
}
