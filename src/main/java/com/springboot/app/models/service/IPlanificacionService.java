package com.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import com.springboot.app.models.entity.Planificacione;

public interface IPlanificacionService {

	public List<Planificacione> findAll();
	
	public List<Planificacione> findAllByCreateAt(Date createAt);
}
