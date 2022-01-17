package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.dao.IPlanificacionesDao;
import com.springboot.app.models.entity.Planificacione;

@Service
public class PlanificacionServiceImpl implements IPlanificacionService{

	@Autowired
	private IPlanificacionesDao planiDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Planificacione> findAll() {
		// TODO Auto-generated method stub
		return (List<Planificacione>) planiDao.findAll();
	}

}
