package com.safdesk.util;

import java.sql.ResultSet;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

public class Security {

	public static void encryptAll() {
		new DB();
		try {
			ResultSet rs = DB.getAll("users");
			while(rs.next()){
				String pass = rs.getString("Password");
				String newPass = encrypt(pass);
				String x = "UPDATE users SET Password='"+newPass+"' WHERE id='"+rs.getInt("id")+"'";
				DB.getConnection().prepareStatement(x).executeUpdate();
				
			}
			
		} catch (Exception e) {e.printStackTrace();}
		
	}

	public static String encrypt(String pass){
		ConfigurablePasswordEncryptor passE = new ConfigurablePasswordEncryptor();
		passE.setAlgorithm("SHA-512");
		passE.setPlainDigest(true);
		String encrypted = passE.encryptPassword(pass);
		return encrypted;
	}
}
