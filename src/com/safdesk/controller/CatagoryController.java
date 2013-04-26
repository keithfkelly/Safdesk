package com.safdesk.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safdesk.dao.CatagoryDAO;
import com.safdesk.model.Catagory;;

public class CatagoryController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private CatagoryDAO dao;
	
	public CatagoryController() throws Exception{
		super();
		dao = new CatagoryDAO();
	}
	
	public void doPost(HttpServletRequest catReq, HttpServletResponse catRes) throws ServletException, IOException{
		Catagory catagory = new Catagory();
		catagory.setCatagoryname(catReq.getParameter("catagoryname"));
		dao.addCat(catagory);
		RequestDispatcher rd = catReq.getRequestDispatcher("ticket.jsp");
		catReq.setAttribute("newCat", "yes");
		rd.forward(catReq, catRes);
	}
}
