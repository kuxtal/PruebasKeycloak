package mx.japs.pruebas.keycloakback;

import org.apache.catalina.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.descriptor.web.LoginConfig;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.keycloak.adapters.tomcat.KeycloakAuthenticatorValve;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

import mx.japs.pruebas.keycloakback.config.resolver.KeycloakConfiguracionResolver;

@SpringBootApplication
public class PruebasKeycloackAppBack {
	private static final Logger logger = LogManager.getLogger(PruebasKeycloackAppBack.class);
	
    public static void main(String[] args) throws Exception {
    	logger.debug("Iniciando aplicacion {}", "PruebasKeycloackAppBack");
    	
        SpringApplication.run(PruebasKeycloackAppBack.class, args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer getKeycloakContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
            	logger.debug("customize()");
            	
                if (configurableEmbeddedServletContainer instanceof TomcatEmbeddedServletContainerFactory) {
                	logger.debug("configurableEmbeddedServletContainer instancia de TomcatEmbeddedServletContainerFactory");
                    TomcatEmbeddedServletContainerFactory container = (TomcatEmbeddedServletContainerFactory) configurableEmbeddedServletContainer;

                    container.addContextValves(new KeycloakAuthenticatorValve());
                    container.addContextCustomizers(getKeycloakContextCustomizer());
                }
            }
        };
    }
    
    @Bean
    public TomcatContextCustomizer getKeycloakContextCustomizer() {
        return new TomcatContextCustomizer() {
            @Override
            public void customize(Context context) {
            	logger.debug("customize()");
            	
                LoginConfig loginConfig = new LoginConfig();
                loginConfig.setAuthMethod("KEYCLOAK");
                context.setLoginConfig(loginConfig);

                context.addSecurityRole("USER");
                context.addSecurityRole("ADMIN");

                SecurityConstraint constraint = new SecurityConstraint();
                constraint.addAuthRole("USER");

                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);

                context.addConstraint(constraint);

                context.addParameter("keycloak.config.resolver", KeycloakConfiguracionResolver.class.getName());
            }
        };
    }
}
