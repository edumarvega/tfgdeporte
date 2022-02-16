package com.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import com.springboot.app.models.entity.Planificacione;
import com.springboot.app.models.entity.Usuario;

public interface IPlanificacionService {

	public List<Planificacione> findAll();
	
	public List<Planificacione> findAllByCreateAt(Date createAt);
	
	public List<Planificacione> findByUsuario(Usuario usuario);
}
