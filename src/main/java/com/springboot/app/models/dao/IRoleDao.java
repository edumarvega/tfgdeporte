package com.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.Role;

public interface IRoleDao extends CrudRepository<Role, Long>{

}
