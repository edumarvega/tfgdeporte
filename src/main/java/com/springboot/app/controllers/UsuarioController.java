package com.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.springboot.app.models.dao.IUsuarioDao;
import com.springboot.app.models.entity.Producto;
import com.springboot.app.models.entity.Usuario;
import com.springboot.app.models.service.IUsuariosService;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {

	@Autowired
	private IUsuariosService usuarioService;

	@RequestMapping(value = "/listaru", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Usuarios");
		model.addAttribute("usuarios", usuarioService.findAll());
		return "listaru";
	}

	@RequestMapping(value = "/formu")
	public String crear(Map<String, Object> model) {

		Usuario usuario = new Usuario();
		model.put("usuario", usuario);
		model.put("titulo", "Formulario de Usuario");
		return "formu";

	}

	@RequestMapping(value = "/formu", method = RequestMethod.POST)
	public String guardar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Usuarios");
			return "formu";
		}
		
		String mensajeFlash = (usuario.getId() != null)? "Usuario editado con éxito" : "Usuario creado con éxito";	
		usuarioService.save(usuario);
		status.setComplete();

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
		model.put("usuario", usuario);
		model.put("titulo", "Editar Usuario");
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
