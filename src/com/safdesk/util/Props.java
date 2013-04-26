package com.safdesk.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Props {

	public static Map<String, String> get() {
		Map<String, String> props = new HashMap<String, String>();
		try {

			InputStream i = Props.class.getClassLoader().getResourceAsStream("safProp.properties");
			BufferedReader r = new BufferedReader(new InputStreamReader(i));
			String l;
			while((l = r.readLine()) != null) {
				String[] x = l.split("=");
				props.put(x[0], x[1]);
			} 
			i.close();
		} 
		catch(Exception e) {
			System.out.println(e);
		}
		return props;
	}
}


