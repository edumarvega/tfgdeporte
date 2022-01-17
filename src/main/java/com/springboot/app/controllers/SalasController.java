package com.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Sala;
import com.springboot.app.models.service.ISalasService;

@Controller
public class SalasController {
	
	@Autowired
	private ISalasService salasService; 
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Salas");
		model.addAttribute("salas", salasService.findAll());
		return "listar";
	}
	
	@RequestMapping(value="/form")
	public String crear(Map<String, Object> model) {
		
		Sala sala = new Sala();
		model.put("sala",sala);
		model.put("titulo", "Formulario de Sala");
		return "form";
	}
	
	@RequestMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Sala sala = null;
		

		if(id>0) {
			sala = salasService.findOne(id);
			if(sala == null) {
				flash.addFlashAttribute("error", "La Sala no existe en la BBDD!");
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error", "La Sala creada no puede ser cero!");
			return "redirect:/listar";
		}
		model.put("sala",sala);
		model.put("titulo", "Editar Sala");
		return "form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(@Valid Sala sala, BindingResult result, Model model,RedirectAttributes flash) {
		
		
		String mensajeFlash = (sala.getId() != null)? "Sala editada con éxito" : "Sala creada con éxito"; 
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Sala");
			return "form";
		}
		
		
		salasService.save(sala);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		if(id>0) {
			salasService.delete(id);
			flash.addFlashAttribute("success", "Sala eliminada con éxito!");
		}
		return "redirect:/listar";
		
	}

}
