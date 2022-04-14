package com.springboot.app.models.service;

import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

@Service
public class GoogleCalendarServiceImpl implements IGoogleCalendarService{

	@Override
	public Event get(Calendar client, String eventId) throws IOException {
		Event event = client.events().get("primary", eventId).execute();
		System.out.println(event.getSummary());
		return event;
	}

	@Override
	public Event insert(Calendar client,String usuario, 
			String titulo, String observacion,Date fecha) throws IOException {
		Event event = new Event().setSummary(usuario+"-"+titulo)
				.setDescription(observacion);

		//DateTime startDateTime = new DateTime("2022-04-08T09:00:00-07:00");
		DateTime startDateTime = new DateTime(fecha);
		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("Europe/Budapest");
		event.setStart(start);

		//DateTime endDateTime = new DateTime("2022-04-08T10:00:00-07:00");
		//se agrega una hora mas al date
		java.util.Calendar calendar = java.util.Calendar.getInstance();
	    calendar.setTime(fecha);
	    calendar.add(java.util.Calendar.HOUR_OF_DAY, 1);
		DateTime endDateTime = new DateTime(calendar.getTime());
		EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("Europe/Budapest");
		event.setEnd(end);

		event = client.events().insert("primary", event).execute();
		System.out.printf("Event created: %s\n", event.getId());
		return event;
	}

	@Override
	public void delete(Calendar client, String eventId) throws IOException {
		client.events().delete("primary", eventId).execute();
		
	}

	@Override
	public Event update(Calendar client,String usuario, 
			String titulo, String observacion, Date fecha, Event eventUpdate) throws IOException {
		
		eventUpdate.setSummary(usuario+"-"+titulo)
		.setDescription(observacion);

		DateTime startDateTime = new DateTime(fecha);
		EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("Europe/Budapest");
		eventUpdate.setStart(start);
		
		//se agrega una hora mas al date
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(java.util.Calendar.HOUR_OF_DAY, 1);
		DateTime endDateTime = new DateTime(calendar.getTime());
		EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("Europe/Budapest");
		eventUpdate.setEnd(end);

		Event updatedEvent = client.events().update("primary", eventUpdate.getId(), eventUpdate).execute();
		System.out.println(updatedEvent.getUpdated());
		return updatedEvent;
	}

}
