package com.springboot.app.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.app.models.entity.Planificacione;
import com.springboot.app.models.entity.Usuario;

@Repository
public interface IPlanificacionesDao extends CrudRepository<Planificacione, Long>{
	
	List<Planificacione> findAllByCreateAt(Date createAt);
	
	List<Planificacione> findByUsuario(Usuario usuario);
		
}
