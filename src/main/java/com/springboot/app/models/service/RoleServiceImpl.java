package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.models.dao.IRoleDao;
import com.springboot.app.models.entity.Role;

@Service
public class RoleServiceImpl implements IRoleService{
	
	@Autowired
	private IRoleDao roleDao;

	@Override
	public Role findById(Long id) {
		
		return this.roleDao.findById(id).orElse(null);
	}

	@Override
	public List<Role> findAll() {
		
		return (List<Role>) this.roleDao.findAll();
	}

}
