package com.springboot.app.models.service;

import java.util.List;

import com.springboot.app.models.entity.Sala;

public interface ISalasService {
	
	public List<Sala> findAll();
	
	public void save(Sala sala);
	
	public Sala findOne(Long id);
	
	public void delete(Long id);

}
