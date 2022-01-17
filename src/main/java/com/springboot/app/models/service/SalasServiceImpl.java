package com.springboot.app.models.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.dao.ISalasDao;
import com.springboot.app.models.entity.Sala;



@Service
public class SalasServiceImpl implements ISalasService{

	@Autowired
	private ISalasDao salaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Sala> findAll() {
		// TODO Auto-generated method stub
		return (List<Sala>) salaDao.findAll();
	}

	@Override
	@Transactional
	public void save(Sala sala) {
		// TODO Auto-generated method stub
		salaDao.save(sala);
	}

	@Override
	@Transactional(readOnly = true)
	public Sala findOne(Long id) {
		// TODO Auto-generated method stub
		return salaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		salaDao.deleteById(id);
		
	}
	


}
