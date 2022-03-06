package com.springboot.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Role;
import com.springboot.app.models.entity.Usuario;
import com.springboot.app.models.service.IRoleService;
import com.springboot.app.models.service.IUsuariosService;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {

	@Autowired
	private IUsuariosService usuarioService;
	
	@Autowired
	private IRoleService rolService;
		
	@RequestMapping(value = "/listaru", method = RequestMethod.GET)
	public String listar(Model model) {
		List<Usuario> usuarios = new ArrayList<>();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println("usuario logueado: "+currentPrincipalName);
		
		Usuario usuario = this.usuarioService.findByUsuario(currentPrincipalName);
		if(usuario!=null) {
			String roleAdmin = "ADMIN";
			Role role =usuario.getRole();
			if(role!=null) {
				if(roleAdmin.equals(role.getRole())) {
					usuarios = usuarioService.findAll();
				} else {
					usuarios.add(usuario);
				}
			} 
		}
		
		model.addAttribute("titulo", "Listado de Usuarios");
		model.addAttribute("usuarios", usuarios);
		return "listaru";
	}

	@RequestMapping(value = "/formu")
	public String crear(Map<String, Object> model) {

		Usuario usuario = new Usuario();
		model.put("usuario", usuario);
		model.put("titulo", "Formulario de Usuario");
		model.put("roles", this.rolService.findAll());
		model.put("action", "no_readonly");
		
		return "formu";

	}

	@RequestMapping(value = "/formu", method = RequestMethod.POST)
	public String guardar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Usuarios");
			return "formu";
		}
		
		Usuario usuarioBD = this.usuarioService.findByUsuario(usuario.getUsuario());
		if (usuarioBD != null) {
			model.addAttribute("error", "El Usuario ya existe, elija otro nombre de usuario!!");
			model.addAttribute("usuario", usuario);
			model.addAttribute("titulo", "Formulario de Usuario");
			model.addAttribute("roles", this.rolService.findAll());
			//si es alta de usuario
			if (usuario.getId() == null) {
					return "formu";

			} else {
				if (usuario.getId().longValue() != usuarioBD.getId().longValue()) {
					return "formu";
				}
			}
		}
								
		usuarioService.save(usuario);
		
		status.setComplete();
		
		String mensajeFlash = (usuario.getId() != null)? "Usuario editado con éxito" : "Usuario creado con éxito";

		flash.addFlashAttribute("success", mensajeFlash);		
		return "redirect:listaru";
	}

	@RequestMapping(value = "/formu/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Usuario usuario = null;

		if (id > 0) {
			usuario = usuarioService.findOne(id);
			if(usuario == null) {
				flash.addFlashAttribute("error", "El Usuario no existe en la BBDD!");
				return "redirect:/listaru";
			}			
		} else {
			flash.addFlashAttribute("error", "El Usuario creado no puede ser cero!");
			return "redirect:/listaru";
		}
		
		//blanqueo el password
		usuario.setPassword("");
			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<SimpleGrantedAuthority> lista = (List<SimpleGrantedAuthority>)authentication.getAuthorities();
		String rol = lista.get(0).getAuthority();
		
		String action = "readonly";
		if(rol.equals("ADMIN")) {
			action = "no_readonly";
		}
				
		model.put("usuario", usuario);
		model.put("titulo", "Editar Usuario");
		model.put("roles", this.rolService.findAll());
		model.put("action", action);
		
		return "formu";
	}

	@RequestMapping(value = "/eliminaru/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			usuarioService.delete(id);
			flash.addFlashAttribute("success", "Usuario eliminado con éxito!");			
		}
		return "redirect:/listaru";

	}
	
	@GetMapping(value="/veru/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Usuario usuario = usuarioService.findOne(id);
		if(usuario==null) {
			flash.addFlashAttribute("error","El Usuario no existe en la BBDD");
			return "redirect:/listaru";
		}
		
		model.put("usuario", usuario);
		model.put("titulo", "Detalle Usuario: " + usuario.getUsuario());
		return "veru";
	}	

}
