package com.springboot.app.models.service;

import java.util.List;

import com.springboot.app.models.entity.Planificacione;

public interface IPlanificacionService {

	public List<Planificacione> findAll();
}
