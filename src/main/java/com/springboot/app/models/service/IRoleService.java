package com.springboot.app.models.service;

import java.util.List;

import com.springboot.app.models.entity.Role;

public interface IRoleService {
	
	public Role findById(Long id);
	
	public List<Role> findAll();


}
