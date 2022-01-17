package com.springboot.app.models.dao;


import org.springframework.data.repository.CrudRepository;

import com.springboot.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{


}
