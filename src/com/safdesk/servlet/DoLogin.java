package com.safdesk.servlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.safdesk.util.DB;
import com.safdesk.util.Security;

@SuppressWarnings("serial")
public class DoLogin extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet { 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		new DB();
		new Security();
		String userLevel = null;
		int count=0;
		String un = null;

		try{
			response.setContentType("text/html");
			String user=request.getParameter("user");
			String pass=Security.encrypt(request.getParameter("pass"));
			ResultSet rs = DB.getRS("select * from users where username='"+user+"'");

			while(rs.next())
			{
				if(pass.equals(rs.getString("Password"))){
					un = rs.getString("UserName");
					int x = rs.getInt("UserLevel");
					userLevel = ""+x;
					count++;
				}

			}
			if(count>0){
				Cookie sun = new Cookie("User",user);
				Cookie sul = new Cookie("Status", userLevel);
				sun.setMaxAge(6000);
				sul.setMaxAge(6000);
				request.setAttribute("user", un);
				System.out.print(un);
				response.addCookie(sun);
				response.addCookie(sul);
				response.sendRedirect("dashboard");
			}
			else
			{ 	
				Cookie sul = new Cookie("Status", "failed");
				response.addCookie(sul);
				response.sendRedirect("home.jsp");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}