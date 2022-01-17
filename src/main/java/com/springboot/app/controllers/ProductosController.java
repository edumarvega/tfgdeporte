package com.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.tomcat.jni.File;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.Producto;
import com.springboot.app.models.service.IProductosService;

@Controller
public class ProductosController {
	
	@Autowired
	private IProductosService productosService;
	
	
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Producto producto = productosService.findOne(id);
		if(producto==null) {
			flash.addFlashAttribute("error","El material no existe en la BBDD");
			return "redirect:/listarp";
		}
		
		model.put("producto", producto);
		model.put("titulo", "Detalle Material: " + producto.getNombre());
		return "ver";
	}
	
	@RequestMapping(value="/listarp", method=RequestMethod.GET)
	public String listarp(Model model) {
		
		model.addAttribute("titulo", "Listado de Materiales");
		model.addAttribute("productos", productosService.findAll());
		return "listarp";
	}
	
	
	@RequestMapping(value="/formp")
	public String crear(Map<String, Object> model) {
		
		Producto producto = new Producto();
		model.put("producto",producto);
		model.put("titulo", "Formulario de Material");
		return "formp";
	}

	
	@RequestMapping(value="/formp/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Producto producto = null;
		

		if(id>0) {
			producto = productosService.findOne(id);
			if(producto == null) {
				flash.addFlashAttribute("error", "El Material no existe en la BBDD!");
				return "redirect:/listarp";
			}
		}else {
			flash.addFlashAttribute("error", "El Material creado no puede ser cero!");
			return "redirect:/listarp";
		}
		model.put("producto",producto);
		model.put("titulo", "Editar Material");
		return "formp";
	}
	
	@RequestMapping(value="/formp", method=RequestMethod.POST)
	public String guardar(@Valid Producto producto, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash) {
		
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Material de Producto");
			return "formp";
		}
		
		if(!foto.isEmpty()) {
			
			String uniqueFilename = UUID.randomUUID().toString() + " " + foto.getOriginalFilename();

			Path rootPath = Paths.get("uploads").resolve(uniqueFilename);
			
			Path rootAbsolutePath = rootPath.toAbsolutePath();
			
			try {
                
				Files.copy(foto.getInputStream(), rootAbsolutePath);
				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
				
				producto.setFoto(uniqueFilename);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String mensajeFlash = (producto.getId() != null)? "Material editado con éxito" : "Material creado con éxito";		
		productosService.save(producto);
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listarp";
	}
	
	@RequestMapping(value="/eliminarp/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		
		if(id>0) {
			
			productosService.delete(id);
			flash.addFlashAttribute("success", "Material eliminado con éxito!");
			
		}
		return "redirect:/listarp";
		
	}
	
}
