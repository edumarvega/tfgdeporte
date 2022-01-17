package com.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.app.models.entity.Planificacione;

@Repository
public interface IPlanificacionesDao extends CrudRepository<Planificacione, Long>{
	
	

}
