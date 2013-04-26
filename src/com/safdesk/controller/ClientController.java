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

import com.safdesk.dao.ClientDAO;
import com.safdesk.model.Client;

public class ClientController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static String INSERT_OR_EDIT = "/client.jsp";
	private static String LIST_CLIENT = "/listclients.jsp";
	private ClientDAO dao;

	public ClientController() throws Exception{
		super();
		dao = new ClientDAO();
	}

	@Override
	protected void doGet(HttpServletRequest clientReq, HttpServletResponse clientRes) throws ServletException, IOException {
		String forward="";
		String action = clientReq.getParameter("action");

		if (action.equalsIgnoreCase("edit")){
			clientReq.setAttribute("edit", "edit");
			forward = INSERT_OR_EDIT;
			int clientId = Integer.parseInt(clientReq.getParameter("idclient"));
			Client client = dao.getClient(clientId);
			clientReq.setAttribute("client", client);
		} else if (action.equalsIgnoreCase("allClients")){
			forward = LIST_CLIENT;
			List<Client> clientList = dao.getClients();
			Collections.sort(clientList, Sort);
			clientReq.setAttribute("clients", clientList);
		}else {
			forward = INSERT_OR_EDIT;
		}

		RequestDispatcher view = clientReq.getRequestDispatcher(forward);
		view.forward(clientReq, clientRes);
	}

	@Override
	protected void doPost(HttpServletRequest clientReq, HttpServletResponse clientRes) throws ServletException, IOException {
		Client client = new Client();
		String id = clientReq.getParameter("idclient"); 
		client.setEmailaddress(clientReq.getParameter("email"));
		client.setPhonenumber(clientReq.getParameter("phone"));
		if(id==null||id.isEmpty()){
			client.setClientname(clientReq.getParameter("name"));
			dao.addClient(client);
		}else{
			client.setId(Integer.parseInt(id));
			dao.updateClient(client);
		}
		RequestDispatcher rd = clientReq.getRequestDispatcher(LIST_CLIENT);
		List<Client> clientList = dao.getClients();
		Collections.sort(clientList, Sort);
		clientReq.setAttribute("clients", clientList);
		rd.forward(clientReq, clientRes);
	}
	
    private static Comparator<Client> Sort = new Comparator<Client>()
    {
        public int compare(Client c1, Client c2)
        {
            return c1.getClientname().compareTo(c2.getClientname());
        }
    };

}
