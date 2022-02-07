package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.dao.IPlanificacionesDao;
import com.springboot.app.models.dao.IProductosDao;
import com.springboot.app.models.dao.ISalasDao;
import com.springboot.app.models.dao.IUsuarioDao;
import com.springboot.app.models.entity.Planificacione;
import com.springboot.app.models.entity.Producto;
import com.springboot.app.models.entity.Sala;
import com.springboot.app.models.entity.Usuario;

@Service
public class UsuariosServiceImpl implements IUsuariosService{
    
	@Autowired
	private IUsuarioDao usuarioDao;

	@Autowired
	private IProductosDao productoDao;
	
	@Autowired
	private IPlanificacionesDao planificacioneDao;
	
	@Autowired
	private ISalasDao salaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioDao.save(usuario);
		
	}

	@Override
	@Transactional(readOnly = true)	
	public Usuario findOne(Long id) {
		// TODO Auto-generated method stub
		return usuarioDao.findById(id).orElse(null);
	}
 
	@Override
	@Transactional	
	public void delete(Long id) {
		usuarioDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)	
	public List<Producto> finByNombre(String term) {
		// TODO Auto-generated method stub
		return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void savePlanificacione(Planificacione planificacione) {
		// TODO Auto-generated method stub
		planificacioneDao.save(planificacione);
	}
 
	@Override
	@Transactional(readOnly = true)		
	public Producto findProductoById(Long id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)	
	public Planificacione findPlanificacioneById(Long id) {
		// TODO Auto-generated method stub
		return planificacioneDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deletePlanificacion(Long id) {
		planificacioneDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Planificacione> findAll2() {
		// TODO Auto-generated method stub
		return (List<Planificacione>) planificacioneDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)	
	public Sala findSalaById(Long id) {
		// TODO Auto-generated method stub
		return salaDao.findById(id).orElse(null);
	}

	@Override
	public Usuario findByUsuario(String usuario) {
		
		return this.usuarioDao.findByUsuario(usuario);
	}
	
	

}
