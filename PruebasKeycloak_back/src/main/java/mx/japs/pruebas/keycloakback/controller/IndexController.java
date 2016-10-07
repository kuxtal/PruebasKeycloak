package mx.japs.pruebas.keycloakback.controller;

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
	//@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
	public String index() {
		logger.debug("index()");
		//logger.debug("Usuario: {} - {}", userDetails.getId(), userDetails.getFullName());
		
		//return "Usuario ingresado: " + userDetails.getId() + " - " +  userDetails.getFullName() + ": Roles " + userDetails.getRoles();
		return "Hola Mundo!!!";
	}
	
	@RequestMapping(value = "/consulta", method = RequestMethod.GET)
	@ResponseBody
	//@PreAuthorize("hasRole('ROL_ADMIN')")
	public String consulta() {
		logger.debug("consulta()");
		//logger.debug("Usuario: {} - {}", userDetails.getId(), userDetails.getFullName());
		
		return "Pagina de Consulta";
	}
	
	@RequestMapping(value = "/modificacion", method = RequestMethod.GET)
	@ResponseBody
	//@PreAuthorize("hasRole('ROL_ADMIN')")
	public String modificacion() {
		logger.debug("modificacion()");
		//logger.debug("Usuario: {} - {}", userDetails.getId(), userDetails.getFullName());
		
		return "Pagina de Modificacion";
	}
}
