package com.coleccion.serviciosuser.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coleccion.serviciosuser.entity.Direccion;
import com.coleccion.serviciosuser.entity.Usuario;
import com.coleccion.serviciosuser.services.IUsuariosService;
import com.coleccion.serviciosuser.util.SeoMexService;

import io.swagger.v3.oas.annotations.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class UsuariosController {
	
	 private static final Logger logger = LoggerFactory.getLogger(UsuariosController.class);

	    @Autowired
	    IUsuariosService iUsuariosService;
	    
	    
	    @Autowired
	    private SeoMexService seoMexService;

			    
	    @Operation(summary = "Obtiene todos los usuarios")
	    @GetMapping("/usuario")
	    public ResponseEntity<?> getUsuarioAll() {
	    	 logger.info("Iniciando solicitud para obtener todos los usuarios");

	    	    List<Usuario> usuarioList = iUsuariosService.findAll();

	    	    if (usuarioList.isEmpty()) {
	    	        logger.warn("No se encontraron usuarios en la base de datos");
	    	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	    	                .body("No se encontraron usuarios");
	    	    } else {
	    	        logger.info("Se encontraron {} usuarios en total", usuarioList.size());
	    	        return ResponseEntity.ok(usuarioList);
	    	    }
	    }
	    
	    @Operation(summary = "Obtnener usuario por Id")
	    @GetMapping("/usuario/{id}")
	    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
	        logger.info("Obteniendo Usuario por Id: {}");
	       
	        try {
	            Optional<Usuario> tipoOptional = iUsuariosService.findById(id);
	            if (tipoOptional.isPresent()) {
	            	 logger.info("Usuario encontrado ID: {}", id);
	            	Usuario tipo = tipoOptional.get();
	                return ResponseEntity.ok(tipo);
	            } else {
	            	logger.warn("Usuario no encontrado con ID: {}", id);
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado  con ID: " + id);
	            }
	        } catch (Exception e) {
	            logger.error("Error interno al obtener usuario con ID: {}", id, e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
	        }
	    }
	    
        
	    @Operation(summary = "Crear un nuevo usuario")
	    @PostMapping("/usuario")
	    public ResponseEntity<?> createUsuario(@RequestBody Map<String, Object> usuarioMap) {
	        Map<String, Object> response = new HashMap<>();
	        Usuario usuarioCreado;

	        try {
	            // Aqui se definen todas las variables para generar el usuario
	            String nombre = (String) usuarioMap.get("nombre");
	            String apellidoPaterno = (String) usuarioMap.get("apellidoPaterno");
	            String apellidoMaterno = (String) usuarioMap.get("apellidoMaterno");
	            String email = (String) usuarioMap.get("email");
	            String cp = (String) usuarioMap.get("cp");
	            
	            logger.info("Iniciando creación de usuario con datos: {}", usuarioMap);
	            logger.info("Consultando dirección con CP: {}", cp);

	            // Llama API SEPOMEX para obtener  la dirección
	            Map<String, Object> direccionData;
	            
	            try {
	            	 direccionData =  seoMexService.obtenerDireccionPorCP(cp);	       
	            } catch (IllegalArgumentException e) {
	            
	            	 logger.warn("CP no válido o no tiene datos: {}", cp);
	                 response.put("mensaje", "El código postal " + cp + " no es válido o no tiene información.");
	                 response.put("error", e.getMessage());
	                 return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	             }
	            

	            // Aqui se definen las variables para la direccion	            	         
	            String asentamiento = (String) direccionData.get("asentamiento");
	            String tipoasentamiento = (String) direccionData.get("tipoAsentamiento");
	            String municipio = (String) direccionData.get("municipio");
	            String estado = (String) direccionData.get("estado");
	            String ciudad = (String) direccionData.get("ciudad");
	            String pais = (String) direccionData.get("pais");
	            
	            logger.info("Datos de dirección recibidos: Asentamiento: {}, Municipio: {}, Estado: {}", asentamiento, municipio, estado);
	            
	         // Se genera el Objeto Dirección
	            Direccion direccion = new Direccion();
	            direccion.setCp(cp);
	            direccion.setAsentamiento(asentamiento != null ? asentamiento : "No info");
	            direccion.setTipoAsentamiento(tipoasentamiento != null ? tipoasentamiento : "No info");
	            direccion.setMunicipio(municipio != null ? municipio : "No info");
	            direccion.setEstado(estado != null ? estado : "No info");
	            direccion.setCiudad(ciudad != null ? ciudad : "No info");
	            direccion.setPais(pais != null ? pais : "No info");
	            
	            // Crear usuario con la dirección
	            Usuario usuario = new Usuario();
	            usuario.setNombre(nombre);
	            usuario.setApellidoPaterno(apellidoPaterno);
	            usuario.setApellidoMaterno(apellidoMaterno);
	            usuario.setEmail(email);
	            usuario.setDireccion(direccion); // Aqui se Asigna la dirección

	            // Guardar Usuario con dirección
	            usuarioCreado = iUsuariosService.save(usuario);
	            logger.info("Usuario creado con ID: {}", usuarioCreado.getId());

	            response.put("mensaje", "Usuario creado con éxito");
	            response.put("usuario", usuarioCreado);
	            return new ResponseEntity<>(response, HttpStatus.CREATED);

	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.error("Error al crear usuario con datos: {}", usuarioMap, e);
	            response.put("mensaje", "Error al crear usuario");
	            response.put("error", e.getMessage());
	            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
	   
	    @Operation(summary = "Obtener usuario por Id")
	    @PutMapping("/usuario/{id}")	    
	    public ResponseEntity<?> updateUsuario(@RequestBody Map<String, Object> usuarioMap, @PathVariable Long id) {
	        logger.info("Updating Usuario with ID: {}", id);

	        Map<String, Object> response = new HashMap<>();
	        Optional<Usuario> optionalActual = iUsuariosService.findById(id);
	        if (!optionalActual.isPresent()) {
	            logger.info("Usuario no encontrado por ID: {}", id);
	            response.put("mensaje", "Error: no se pudo editar, el Usuario ID: ".concat(id.toString().concat(" no existe en la base de datos para el usuario actual")));
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	        Usuario usuarioActual = optionalActual.get();
	                                
	        try {	
	        	
	              Usuario usuarioActualizado = null;
	            	
	        	  String nombre = (String) usuarioMap.get("nombre");
	              String apellidoPaterno = (String) usuarioMap.get("apellidoPaterno");
	              String apellidoMaterno = (String) usuarioMap.get("apellidoMaterno");
	              String email = (String) usuarioMap.get("email");
	              String cp = (String) usuarioMap.get("cp");
	              
	              logger.info("Actualizando usuario ID: {} con datos: {}", id, usuarioMap);	  	          	        
	  	        
	  	         Map<String, Object> direccionData = seoMexService.obtenerDireccionPorCP(cp);
	  	         // Extrae los datos
	              String asentamiento = (String) direccionData.get("asentamiento");
	              String tipoasentamiento = (String) direccionData.get("tipoAsentamiento");
	              String municipio = (String) direccionData.get("municipio");
	              String estado = (String) direccionData.get("estado");
	              String ciudad = (String) direccionData.get("ciudad");
	              String pais = (String) direccionData.get("pais");
	              
	            // Se crea la Direccion
	              Direccion direccion = new Direccion();
	              direccion.setCp(cp);
	              direccion.setAsentamiento(asentamiento != null ? asentamiento : "No info");
	              direccion.setTipoAsentamiento(tipoasentamiento != null ? tipoasentamiento : "No info");
	              direccion.setMunicipio(municipio != null ? municipio : "No info");
	              direccion.setEstado(estado != null ? estado : "No info");
	              direccion.setCiudad(ciudad != null ? ciudad : "No info");
	              direccion.setPais(pais != null ? pais : "No info");
	  	        
	              //Se crea el usuario con la direccion referente al cp
	  	          usuarioActual.setNombre(nombre);
	  	          usuarioActual.setApellidoPaterno(apellidoPaterno);
	  	          usuarioActual.setApellidoMaterno(apellidoMaterno);
	  	          usuarioActual.setEmail(email);
	  	          usuarioActual.setDireccion(direccion);
	  	         //Se registran los cambios en la direccion y usuario
	        	  usuarioActualizado = iUsuariosService.save(usuarioActual);
	        	  logger.info("Usuario ID {} actualizado correctamente", id);
	        	  
	        	  response.put("mensaje", "El Usuario ha sido actualizado con éxito");
	              response.put("usuario", usuarioActualizado);
	             
	             return new ResponseEntity<>(response, HttpStatus.OK);
	        } catch (Exception e) {
	            logger.error("Error al actualizar el Usuario", e);
	            response.put("mensaje", "Error al actualizar el Usuario en la base de datos");
	            response.put("error", e.getMessage());	
	            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	        }	        	       	       
	       
	    }
	    
	    
	    @Operation(summary = "Eliminar usuario")
	    @DeleteMapping("/usuario/{id}")	   
	    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
	        logger.info("Eliminando Usuario con ID: {}", id);

	        Map<String, Object> response = new HashMap<>();
	        Optional<Usuario> optionalUsuario = iUsuariosService.findById(id);
	        if (!optionalUsuario.isPresent()) {
	            logger.info("Usuario no encontrado por ID: {}", id);
	            response.put("mensaje", "Error: no se pudo eliminar, el Usuario ID: ".concat(id.toString().concat(" no existe en la base de datos para el usuario actual")));
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }
	        try {
	        	iUsuariosService.delete(id);
	        } catch (DataAccessException e) {
	            logger.error("Error deleting Usuario", e);
	            response.put("mensaje", "Error al eliminar el id Usuario en la base de datos");
	            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
	            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        response.put("mensaje", "El Usuario ha sido eliminado con éxito");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    
	    
	    


	

}
