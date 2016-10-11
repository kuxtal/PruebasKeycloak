package mx.japs.pruebas.keycloakback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class PruebasKeycloackAppBack extends SpringBootServletInitializer {
	private static final Logger logger = LogManager.getLogger(PruebasKeycloackAppBack.class);
	
    public static void main(String[] args) throws Exception {
    	logger.debug("Iniciando aplicacion {}", "PruebasKeycloackAppBack");
    	
        SpringApplication.run(PruebasKeycloackAppBack.class, args);
    }
    
    /**
     * Initializes this application when running in a servlet container (e.g. Tomcat)
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PruebasKeycloackAppBack.class);
    }
}
