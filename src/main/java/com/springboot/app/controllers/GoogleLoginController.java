package com.springboot.app.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.google.api.services.calendar.model.Event;
import com.springboot.app.GoogleCalendarClient;
import com.springboot.app.models.entity.Planificacione;
import com.springboot.app.models.service.IGoogleCalendarService;
import com.springboot.app.models.service.IUsuariosService;

@Controller
public class GoogleLoginController {
	
	private final static Log logger = LogFactory.getLog(GoogleLoginController.class);
	@Autowired
	private GoogleCalendarClient googleCalendarClient;
	@Autowired
	private IGoogleCalendarService googleCalendarService;
	@Autowired
	private IUsuariosService usuarioService;
	private static com.google.api.services.calendar.Calendar client;
	private Long idUsuario;
	private Long idPlanificacion;
	private String usuario;
	private String descripcion;
	private String observacion;
	private Date fecha;
	private String eventId;
	private String operacion;
		

	@RequestMapping(value = "/login/google", method = RequestMethod.GET)
	public RedirectView googleConnectionStatus(HttpServletRequest request) throws Exception {
		
		idUsuario = (Long) request.getSession().getAttribute("idUsuario");
		idPlanificacion = (Long) request.getSession().getAttribute("idPlanificacion");
		usuario = (String) request.getSession().getAttribute("usuario");
		descripcion = (String) request.getSession().getAttribute("descripcion");
		observacion = (String) request.getSession().getAttribute("observacion");
		fecha = (Date) request.getSession().getAttribute("fecha");
		operacion = (String) request.getSession().getAttribute("operacion");
		eventId = (String) request.getSession().getAttribute("eventId");
		
		return new RedirectView(this.googleCalendarClient.authorize());
	}

	@RequestMapping(value = "/login/google", method = RequestMethod.GET, params = "code")
	public String oauth2Callback(@RequestParam(value = "code") String code) {
		try {
			
			client = googleCalendarClient.getClient(code);
			System.out.println(client.getApplicationName());
			
			if("insert".equals(operacion)) {
				//creo un nuevo evento en el calendario
				Event eventoInsert = this.googleCalendarService.insert(client, usuario, 
						descripcion, observacion, fecha);
				
				Planificacione planiInsert = this.usuarioService.findPlanificacioneById(idPlanificacion);
				planiInsert.setEventId(eventoInsert.getId());
				usuarioService.savePlanificacione(planiInsert);
	
			} else if("delete".equals(operacion)) {
				//elimino el evento del calendario en la nube
				this.googleCalendarService.delete(client, eventId);
			
			} else if("update".equals(operacion)) {
				//traigo el evento en el calendario desde la nube
				//modifico el titulo(va con el usuario logueado - titulo), la descripcion y las fecha
				//guardo el eventId haciendo un update de la planificacion
				Event eventUpdate = this.googleCalendarService.get(client, eventId);
				
				eventUpdate = this.googleCalendarService.update(client, usuario, 
						descripcion, observacion, fecha, eventUpdate);
				
				Planificacione planiUpdate = this.usuarioService.findPlanificacioneById(idPlanificacion);
				planiUpdate.setEventId(eventUpdate.getId());
				usuarioService.savePlanificacione(planiUpdate);
								
			} 
						
		} catch (Exception e) {
			logger.warn("Error al creer un evento en google calendar (" + e.getMessage() + ")."
					+ " Re dirigiendo a la lista de planificaciones.");
		}
		return "redirect:/veru/"+idUsuario;
	}

}
