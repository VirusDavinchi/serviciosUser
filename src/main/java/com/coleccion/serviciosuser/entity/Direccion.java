package com.coleccion.serviciosuser.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "direccion")
public class Direccion implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	    @Id
	    @Column(name = "id_direccion", length = 20)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;	
		private String cp; 
	    private String asentamiento; 
	    private String tipoAsentamiento;
	    private String municipio;
	    private String estado;
	    private String ciudad;
	    private String pais;
	    

}
