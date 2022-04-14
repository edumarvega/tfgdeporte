package com.springboot.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.dao.IPlanificacionesDao;
import com.springboot.app.models.entity.Planificacione;
import com.springboot.app.models.entity.Usuario;

@Service
public class PlanificacionServiceImpl implements IPlanificacionService{

	@Autowired
	private IPlanificacionesDao planiDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Planificacione> findAll() {
		return (List<Planificacione>) planiDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Planificacione> findAllByCreateAt(Date createAt) {
		return (List<Planificacione>) this.planiDao.findAllByCreateAt(createAt);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Planificacione> findByUsuario(Usuario usuario) {
		
		return (List<Planificacione>) this.planiDao.findByUsuario(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Planificacione finById(Long id) {
		return this.planiDao.findById(id).orElse(null);
	}

	@Override
	public Planificacione update(Planificacione planificacione) {
		return this.planiDao.save(planificacione);
	}

}
