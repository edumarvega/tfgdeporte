package com.springboot.app.controllers;

import java.util.ArrayList;
import java.util.Date;
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
import com.springboot.app.models.entity.Usuario;
import com.springboot.app.models.service.IPlanificacionService;
import com.springboot.app.models.service.ISalasService;
import com.springboot.app.models.service.IUsuariosService;
import com.springboot.app.util.TfgUtil;

@Controller
@RequestMapping("/planificacione")
@SessionAttributes("planificacione")
public class PlanificacionesController {
	 
	@Autowired
	private IUsuariosService usuarioService;
	
	@Autowired
	private ISalasService salasService; 
	
	@Autowired
	private IPlanificacionService planificacionService;
	

	
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
		model.put("operation","create");
		
		return "planificacione/formf";
	}
	
	
	
	@GetMapping("/editarp/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Planificacione planificacione = usuarioService.findPlanificacioneById(id);
		
		if(planificacione==null) {
			flash.addFlashAttribute("error", "La planificacion no existe en la BBDD");
			return "redirect:/listaru";
		}
		
		//aqui calculo las diponibilidades de los items
		List<ItemPlanificacione> tmp = planificacione.getItems();
		
		if(!tmp.isEmpty()) {
			for (int i = 0; i < tmp.size(); i++) {
				Producto producto = tmp.get(i).getProducto();
				List<Producto> lista = this.calcularDisponibilidades(producto.getNombre(), null, planificacione.getCreateAt());
				if(!lista.isEmpty()) {
					tmp.get(i).setCantidadDisponible(lista.get(0).getCantidad());
				}
			}
			planificacione.setItems(new ArrayList<>());
			planificacione.getItems().addAll(tmp);
		}
		
		model.put("planificacione", planificacione);
		model.put("titulo", "Editar Planificacion");
		model.put("salas",salasService.findAll());
		model.put("operation","edit");
		
		
		return "planificacione/formf";
	}
	
	@GetMapping(value="/cargar-productos/{term}/{createAt}", produces= {"application/json"})
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term, @PathVariable String createAt){
		
		
		List<Producto> productos = this.calcularDisponibilidades(term, createAt, null);
		
		
		return productos;
    } 
	
	@PostMapping("/formf")
	public String guardar(Planificacione planificacione, BindingResult result, Model model,
			@RequestParam(name="item_id[]", required=false) Long[] itemId,
			@RequestParam(name="cantidad[]", required=false) Integer[] cantidad,		
			RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Planificacion");
			return "planificacione/formf";
		}
		
 
		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Planificacion");
			model.addAttribute("error", "Error: La Planificacion NO puede no tener líneas!");
			return "planificacione/formf";
		}
		
		if(!planificacione.getItems().isEmpty()) {
			planificacione.setItems(new ArrayList<>());
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
	
	
	private List<Producto> calcularDisponibilidades(String nombreProducto, String createAt, Date createAtDB){
		int acumulador = 0;
        Long idProducto = null;
		Date date = null;
        
		List<Producto> productos = usuarioService.finByNombre(nombreProducto);
	        
        if(createAtDB==null) {
			date = TfgUtil.convertirDateLocal(createAt);
		}else {
			date = createAtDB;
		}
		
		List<Planificacione> lista = new ArrayList<>();
		
		if(date!=null) {
			lista = this.planificacionService.findAllByCreateAt(date);
		}
				
		//identifico el producto que debo actualizar cantidad
        if(!productos.isEmpty()) {
        	Producto prod = productos.get(0);
        	idProducto = prod.getId();
        }
		
        //acumulo las cantidades planificadas para un mismo producto y lo decremento
        //del stock del producto
        if(!lista.isEmpty()) {
			for (Planificacione planificacione : lista) {
				if(!planificacione.getItems().isEmpty()) {
					for (ItemPlanificacione item : planificacione.getItems()) {
						if(item.getProducto().getId().equals(idProducto)) {
							acumulador = acumulador+item.getCantidad();
						}
						
					}
				}
			}
			//actualizo el stock a planifiacar
			if(acumulador!=0) {
				Producto prod = productos.get(0);
				int cantidadActual = prod.getCantidad();
				if(cantidadActual>=acumulador) {
					prod.setCantidad(cantidadActual-acumulador);
					productos.clear();
					productos.add(prod);
				}
			}
		}
        
        return productos;
	}
	 
	

}
