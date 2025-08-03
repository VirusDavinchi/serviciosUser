package com.coleccion.serviciosuser.services;

import java.util.List;
import java.util.Optional;

import com.coleccion.serviciosuser.entity.Usuario;

public interface IUsuariosService {
	
	 public List<Usuario> findAll();
     public Usuario save(Usuario usuario);
     public Optional<Usuario> findById(Long id);
     public void delete(Long id);
	

}
