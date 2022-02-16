package com.springboot.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.app.models.entity.Planificacione;
import com.springboot.app.models.entity.Role;
import com.springboot.app.models.entity.Usuario;
import com.springboot.app.models.service.IPlanificacionService;
import com.springboot.app.models.service.IUsuariosService;

@Controller
public class PlaniController {
	
	@Autowired
	private IPlanificacionService planiService;
	
	@Autowired
	private IUsuariosService usuarioService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@RequestMapping(value="/listarpla", method=RequestMethod.GET)
	public String listarp(Model model) {
		List<Planificacione> plani = new ArrayList<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println("usuario logueado: "+currentPrincipalName);
		
		Usuario usuario = this.usuarioService.findByUsuario(currentPrincipalName);
		if(usuario!=null) {
			String roleAdmin = "ADMIN";
			Role role =usuario.getRole();
			if(role!=null) {
				if(roleAdmin.equals(role.getRole())) {
					plani = planiService.findAll();
				} else {
					plani = planiService.findByUsuario(usuario);
				}
			} 
		}
		
		model.addAttribute("titulo", "Listado de Planificaciones");
		model.addAttribute("planificaciones", plani);
			
		return "listarpla";
	}	

}
