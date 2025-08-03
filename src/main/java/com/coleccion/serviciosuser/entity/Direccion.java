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
	    
	    @Column(name = "cp", length = 5)
		private String cp; 
	    
	    @Column(name = "asentamiento", length = 100)
	    private String asentamiento; 
	    
	    @Column(name = "tipo_Asentamiento", length = 50)
	    private String tipoAsentamiento;
	    
	    @Column(name = "municipio", length = 100)
	    private String municipio;
	    
	    @Column(name = "estado", length = 100)
	    private String estado;
	    
	    @Column(name = "ciudad", length = 100)
	    private String ciudad;
	    
	    @Column(name = "pais", length = 50)
	    private String pais;
	    

}
