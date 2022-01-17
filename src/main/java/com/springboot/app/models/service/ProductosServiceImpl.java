package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.dao.IProductosDao;
import com.springboot.app.models.entity.Producto;

@Service
public class ProductosServiceImpl implements IProductosService{
	
	@Autowired
	private IProductosDao productoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		// TODO Auto-generated method stub
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional
	public void save(Producto producto) {
		// TODO Auto-generated method stub
		productoDao.save(producto);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findOne(Long id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		productoDao.deleteById(id);
		
	}

}
