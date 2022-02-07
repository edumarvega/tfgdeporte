package com.springboot.app.models.service;

import java.util.List;

import com.springboot.app.models.entity.Rol;

public interface IRolService {
	
	public Rol findById(Long id);
	
	public List<Rol> findAll();


}
