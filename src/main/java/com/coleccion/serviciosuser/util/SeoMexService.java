package com.coleccion.serviciosuser.util;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class SeoMexService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(SeoMexService.class);
	
	// Token en modo pruebas de la API SEPOMEX
    private static final String TOKEN_PRUEBAS = "pruebas";
    
   // Endpoint base para consultar por CP, usando el token en modo prueba
    private static final String ENDPOINT = "https://api.copomex.com/query/info_cp/{cp}?token=" + TOKEN_PRUEBAS;

   
    public Map<String, Object> obtenerDireccionPorCP(String cp) {
    	 logger.info("Solicitando datos de dirección para CP: {}", cp); 
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String, Object>> respuestaLista = restTemplate.getForObject(ENDPOINT, List.class, cp);

        if (respuestaLista == null || respuestaLista.isEmpty()) {
        	// Registra un error y lanza excepción si no hay datos
        	logger.error("Respuesta vacía o nula del API SEPOMEX para CP: {}", cp);
            throw new RuntimeException("Respuesta vacía del API SEPOMEX para CP: " + cp);
        }

        Map<String, Object> primerElemento = respuestaLista.get(0);
        
        // Extrae el mapa que contiene los datos de dirección
        Map<String, Object> response = (Map<String, Object>) primerElemento.get("response");
        
        logger.info("Datos recibidos para el CP {}: {}", cp, response);
        // retorna los datos de la dirección
        return response; 
    }
	

}
