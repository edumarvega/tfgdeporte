package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.models.dao.IRolDao;
import com.springboot.app.models.entity.Rol;

@Service
public class RolServiceImpl implements IRolService{
	
	@Autowired
	private IRolDao rolDao;

	@Override
	public Rol findById(Long id) {
		
		return this.rolDao.findById(id).orElse(null);
	}

	@Override
	public List<Rol> findAll() {
		
		return (List<Rol>) this.rolDao.findAll();
	}

}
