package mx.japs.pruebas.keycloakback.controller;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	private static final Logger logger = LogManager.getLogger(IndexController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("hasAuthority('INDEX_PUBLICO')")
	public HashMap index() {
		logger.debug("index()");
		//logger.debug("Usuario: {} - {}", userDetails.getId(), userDetails.getFullName());
		
		//return "Usuario ingresado: " + userDetails.getId() + " - " +  userDetails.getFullName() + ": Roles " + userDetails.getRoles();
		
		HashMap respuesta = new HashMap();
		respuesta.put("content", "Hola Mundo!!! Index");

		return respuesta;
	}
	
	@RequestMapping(value = "/consulta", method = RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("hasAuthority('INDEX_CONSULTA')")
	public HashMap consulta() {
		logger.debug("consulta()");
		//logger.debug("Usuario: {} - {}", userDetails.getId(), userDetails.getFullName());
		
		HashMap respuesta = new HashMap();
		respuesta.put("content", "Hola Mundo!!! Consulta");
		
		return respuesta;
	}
	
	@RequestMapping(value = "/modificacion", method = RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("hasAuthority('INDEX_MODIFICACION')")
	public HashMap modificacion() {
		logger.debug("modificacion()");
		//logger.debug("Usuario: {} - {}", userDetails.getId(), userDetails.getFullName());
		
		HashMap respuesta = new HashMap();
		respuesta.put("content", "Hola Mundo!!! Modificacion");
		
		return respuesta;
	}
}
