package com.springboot.app.models.service;

import java.util.List;

import com.springboot.app.models.entity.Planificacione;
import com.springboot.app.models.entity.Producto;
import com.springboot.app.models.entity.Sala;
import com.springboot.app.models.entity.Usuario;

public interface IUsuariosService {
	
	public List<Usuario> findAll();
	
	public void save(Usuario usuario);
	
	public Usuario findOne(Long id);
	
	public void delete(Long id);

	public List<Producto> finByNombre(String term);
	
	public void savePlanificacione(Planificacione planificacione);
	
	public Producto findProductoById(Long id);
	
	public Sala findSalaById(Long id);
	
	public Planificacione findPlanificacioneById(Long id);
	
	public void deletePlanificacion(Long id);
	
	public List<Planificacione> findAll2();
	
}
