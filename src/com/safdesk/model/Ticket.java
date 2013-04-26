package com.safdesk.model;

@SuppressWarnings("serial")
public class Ticket implements java.io.Serializable {
	
	private int id = 0;
	private String desc=null;
	private String cat = null;
	private String data = null;
	private int daysOpen = 0;
	private String client = null;
	
	public Ticket(){
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getData() {
		return data.replace(System.getProperty("line.separator"), "").trim();
	}

	public void setData(String data) {
		this.data = data;
	}


	public int getDaysOpen() {
		return daysOpen;
	}

	public void setDaysOpen(int daysOpen) {
		this.daysOpen = daysOpen;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	public String toString(){
		return "Ticket [ID: "+id+"For Client: "+client+"Catagory: "+cat+", Description: "+desc+", Days Open: "+daysOpen+", Data: "+data+"]";
		
	}
}
