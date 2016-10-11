package mx.japs.pruebas.keycloakback.config.resolver;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;

public class KeycloakConfiguracionResolver implements KeycloakConfigResolver {
	private static final Logger logger = LogManager.getLogger(KeycloakConfiguracionResolver.class);

    private KeycloakDeployment keycloakDeployment;

    @Override
    public KeycloakDeployment resolve(HttpFacade.Request request) {
    	logger.debug("resolve()");
    	
        if (keycloakDeployment != null) {
        	logger.debug("keycloakDeployment NO null");
        	
            return keycloakDeployment;
        }

        
        logger.debug("keycloakDeployment null, se crea instancia");
        
        InputStream configInputStream = getClass().getResourceAsStream("/keycloak.json");
        if (configInputStream == null) {
        	logger.debug("configInputStream null, no se encontro {}", getClass().getResource("/keycloak.json"));
        	
            keycloakDeployment = new KeycloakDeployment();
            
            logger.debug("keycloakDeployment Default cors:[{}], bearer:[{}]", keycloakDeployment.isCors(), keycloakDeployment.isBearerOnly());
        } else {
        	logger.debug("configInputStream NO nulo");
            keycloakDeployment = KeycloakDeploymentBuilder.build(configInputStream);
            
            logger.debug("keycloakDeployment cors:[{}], bearer:[{}]", keycloakDeployment.isCors(), keycloakDeployment.isBearerOnly());
        }

        return keycloakDeployment;
    }
}
