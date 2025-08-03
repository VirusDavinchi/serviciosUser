package com.coleccion.serviciosuser.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coleccion.serviciosuser.dao.IUsuarioDao;
import com.coleccion.serviciosuser.entity.Usuario;
import com.coleccion.serviciosuser.services.IUsuariosService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class IUsuariosServiceImpl implements IUsuariosService{
	
	
	@Autowired
	IUsuarioDao iUsuarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {		
		 return (List<Usuario>) iUsuarioDao.findAll();
	}

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {		
		return iUsuarioDao.save(usuario);
	}

	@Override
	public Optional<Usuario> findById(Long id) {
		return iUsuarioDao.findById(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		iUsuarioDao.deleteById(id);
		
	}
	
	
	
	

}
