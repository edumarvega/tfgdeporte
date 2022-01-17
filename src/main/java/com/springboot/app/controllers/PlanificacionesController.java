package com.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.app.models.entity.ItemPlanificacione;
import com.springboot.app.models.entity.Planificacione;
import com.springboot.app.models.entity.Producto;
import com.springboot.app.models.entity.Sala;
import com.springboot.app.models.entity.Usuario;
import com.springboot.app.models.service.ISalasService;
import com.springboot.app.models.service.IUsuariosService;

@Controller
@RequestMapping("/planificacione")
@SessionAttributes("planificacione")
public class PlanificacionesController {
	 
	@Autowired
	private IUsuariosService usuarioService;
	
	@Autowired
	private ISalasService salasService; 
	

	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {
		Planificacione planificacione = usuarioService.findPlanificacioneById(id);
		
		if(planificacione==null) {
			flash.addFlashAttribute("error", "La planificacion no existe en la BBDD");
			return "redirect:/listaru";
		}
		
		model.addAttribute("planificacione", planificacione);
		model.addAttribute("titulo", "Planificacion : ".concat(planificacione.getDescripcion()));
		
		return "planificacione/ver";
	}
	

	

	
	
	@GetMapping("formf/{usuarioId}")
	public String crear(@PathVariable(value="usuarioId") Long usuarioId, Map<String, Object> model, RedirectAttributes flash) {
		logger.info("Luis 1  ");
		Usuario usuario = usuarioService.findOne(usuarioId);
		
		if(usuario == null) {
			flash.addFlashAttribute("error", "El Usuario no existe en la BBDD");
			return "redirect:/listaru";
		}
		
		Planificacione planificacione = new Planificacione();
		planificacione.setUsuario(usuario);
		
		model.put("planificacione", planificacione);
		model.put("titulo", "Crear Planificaciones");
		
		model.put("salas",salasService.findAll());
		
		return "planificacione/formf";
	}
	
	@GetMapping(value="/cargar-productos/{term}", produces= {"application/json"})
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
		logger.info("Luis 2 ");
		return usuarioService.finByNombre(term);
    } 
	
	@PostMapping("/formf")
	public String guardar(Planificacione planificacione, BindingResult result, Model model,
			@RequestParam(name="item_id[]", required=false) Long[] itemId,
			@RequestParam(name="cantidad[]", required=false) Integer[] cantidad,		
			RedirectAttributes flash,
			SessionStatus status) {
		
		logger.info("Planificacione ID: " + planificacione.getId());
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Planificacion");
			return "planificacione/formf";
		}
		
 
		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Planificacion");
			model.addAttribute("error", "Error: La Planificacion NO puede no tener líneas!");
			return "planificacione/formf";
		}
		
		Producto producto = null;
		ItemPlanificacione linea = null;
		for(int i = 0; i < itemId.length; i++) {
			producto =  usuarioService.findProductoById(itemId[i]);
		
			linea = new ItemPlanificacione();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			
			planificacione.addItemPlanificacione(linea);
			
		}
			
		usuarioService.savePlanificacione(planificacione);
		
		status.setComplete();
		flash.addFlashAttribute("success", "Planificacion creada con exito");
		return "redirect:/veru/" + planificacione.getUsuario().getId();
	
	}
	
	@GetMapping("/eliminarp/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		Planificacione planificacione = usuarioService.findPlanificacioneById(id);
		
	    if(planificacione != null) {
	    	usuarioService.deletePlanificacion(id);
	    	flash.addFlashAttribute("success", "Planificacion eliminada con exito");
	    	return "redirect:/veru/" + planificacione.getUsuario().getId();
	    }
	    
    	flash.addFlashAttribute("error", "La Planificacion no existe en la BBDD, no se puedo eliminar");
    	return "redirect:/listaru"; 
	    
	}
	 
	

}
