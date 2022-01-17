package com.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.Planificacione;

public interface IPlanificacionesDao extends CrudRepository<Planificacione, Long>{
	
	

}
