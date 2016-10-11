package mx.japs.pruebas.keycloakback.config.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import mx.japs.pruebas.keycloakback.config.KeycloakAuthentication;

@Component
public class AuthenticationFilter implements Filter{
	private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.debug("doFilter()");
		
		//we must obtain the user from the context.
		//CatalinaRequestAuthenticator saves the security context as request attribute
		try{
			SecurityContextHolder.getContext().setAuthentication(KeycloakAuthentication.KeycloakAuthenticationBuilder.buildAuthentication((HttpServletRequest)request));
			chain.doFilter(request, response);
		} finally {
			SecurityContextHolder.clearContext();
		}
	}

	
	@Override
	public void destroy() {
	}

}