package com.springboot.app.models.service;
import java.io.IOException;
import java.util.Date;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;


public interface IGoogleCalendarService {
	
	public Event get(Calendar client, String eventId) throws IOException;
	public Event insert(Calendar client,String usuario, 
			String titulo, String observacion,Date fecha) throws IOException;
	public void delete(Calendar client, String eventId) throws IOException;
	public Event update(Calendar client,String usuario, 
			String titulo, String observacion,Date fecha, Event event) throws IOException;

}
