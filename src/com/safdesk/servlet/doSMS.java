package com.safdesk.servlet;

import java.io.IOException;

import javax.servlet.http.*;

import com.safdesk.util.DB;


@SuppressWarnings("serial")
public  class doSMS extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	 
	    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    
	    	String smsFrom = request.getParameter("From");
	    	String smsBody = request.getParameter("Body");
	    	String desc = null;
	    	String data = null;
	    	StringBuffer smsBuffer = new StringBuffer();
	    	smsBuffer.append(smsBody);
	    	for(int i=0;i<smsBuffer.length();i++){
	    		if(smsBuffer.charAt(i)=='.'){
	    			desc = smsBuffer.substring(0, i);
	    			data = smsBuffer.substring(i);
	    		}
	    	}
	    	String x = "CALL smsInsert('"+smsFrom+"','"+desc+"','"+data+"')";
	    	new DB();
	    	try {
				DB.doU(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}
