package com.springboot.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TfgUtil {
	
	public static Date convertirDateLocal(String valor) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = null;
        try {
			date = formatter.parse(valor);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        return date;
		
	}

}
