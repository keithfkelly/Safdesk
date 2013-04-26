package com.safdesk.model;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Renewal {

	public Renewal(){}
	
	private int id=0;
	private String client = null;
	private String catagory = null;
	private String dateDue  = null;
	int alertType = 0;
	
	public int getId() {
		return id;
	}
	public String getClient() {
		return client;
	}
	public String getCatagory() {
		return catagory;
	}
	public String getDateDue() {
		return dateDue;
	}
	public int getAlertType() {
		return alertType;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}
	public void setDateDue(String dateDue) {
		this.dateDue = dateDue;
	}
	public void setAlertType(int alertType) {
		this.alertType = alertType;
	}
	
	public String toString(){
		return "Renewal [ID: "+id+" For Client: "+client+" Catagory: "+catagory+" Due Date: "+dateDue+"]";
	}
	
	public int getDaysUntilDue(){
		DateTimeFormatter format =DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime opened = format.parseDateTime(dateDue);
		DateTime now = new DateTime();
		int x = Days.daysBetween(now.toDateMidnight(), opened.toDateMidnight()).getDays();
		return x;
	}
}
