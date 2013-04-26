package com.safdesk.model;

public class Client {

	public Client(){}
	
	private int id=0;
	private String clientname = null;
	private String phonenumber = null;
	String emailaddress  = null;
	
	public int getId() {
		return id;
	}
	public String getClientname() {
		return clientname;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public String getEmailaddress() {
		return emailaddress;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	
}
