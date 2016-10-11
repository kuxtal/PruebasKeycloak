package mx.japs.pruebas.keycloakback.config;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class KeycloakAuthentication implements Authentication {
	private static final Logger logger = LogManager.getLogger(KeycloakAuthentication.class);
	
	private boolean authenticated;
	private String keycloackId;
	
	public KeycloakAuthentication(String keycloackId) {
		super();
		this.authenticated = true;
		this.keycloackId = keycloackId;
	}

	public KeycloakAuthentication(boolean authenticated) {
		super();
		this.authenticated = authenticated;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return keycloackId;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated = isAuthenticated;
		
	}

	@Override
	public String getName() {
		return keycloackId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	
	public static class KeycloakAuthenticationBuilder {
		
		public static KeycloakAuthentication buildAuthentication(HttpServletRequest request) {
			if(request.getUserPrincipal() != null){
				return new KeycloakAuthentication(request.getUserPrincipal().getName());
			} else {
				return new KeycloakAuthentication(false);
			}
		}
	}
}
