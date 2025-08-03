package com.coleccion.serviciosuser.entity;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	@Id
	@Column(name = "id_User", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "nombre", length = 50)
    private String nombre;
	
	@Column(name = "apellido_Paterno", length = 100)
    private String apellidoPaterno;
    
	@Column(name = "apellido_Materno", length = 100)
    private String apellidoMaterno;
    
	@Email(message = "El email debe tener un formato válido")
	@Column(name = "email")
    private String email;
    
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_direccion")
	private Direccion direccion;
	
	

}
