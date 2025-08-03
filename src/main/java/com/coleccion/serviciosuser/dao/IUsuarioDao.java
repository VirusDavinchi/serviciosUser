package com.coleccion.serviciosuser.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coleccion.serviciosuser.entity.Usuario;


@Repository
public interface IUsuarioDao extends JpaRepository<Usuario,Long>{
	

}
