package mx.japs.pruebas.keycloakback_test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import mx.japs.pruebas.keycloakback.PruebasKeycloackAppBack;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PruebasKeycloackAppBack.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0", "management.port=0" })
@DirtiesContext
public class PruebasKeycloackAppBackTest {
	private static final Logger logger = LogManager.getLogger(PruebasKeycloackAppBackTest.class);
	
	@Value("${local.server.port}")
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	private String contextPath = "PruebasKeycloak_back";
	
	//private AuthzClient cliente;


	//@Test
	public void whenCallingHelloWorldWithoutToken_Unauthorized() throws Exception {
		String url = "http://localhost:" + this.port + "/" + contextPath + "/";
		
		logger.debug("URL whenCallingHelloWorldWithoutToken_Unauthorized {}", url);
		ResponseEntity<HashMap> entity = new TestRestTemplate().getForEntity(url, HashMap.class);
		
		assertEquals(HttpStatus.UNAUTHORIZED, entity.getStatusCode());
	}
	
	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void whenCallingHelloWorldWithToken_Authorized_HelloUsername() throws Exception {
		AccessTokenResponse response = getToken();
		org.springframework.http.HttpEntity requestEntity = new org.springframework.http.HttpEntity(getHeaders(response.getToken()));
		ResponseEntity<HashMap> entity = new TestRestTemplate().exchange("http://localhost:" + this.port + "/" + contextPath, HttpMethod.GET, requestEntity, HashMap.class);
		
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		assertEquals("{\"content\": \"Hola Mundo!!!\"}", ((HttpEntity) entity.getBody()).getContent());
	}
	
	/**
	 * Obtain a token on behalf of angular-product-app.
	 * Send credentials through direct access api:
	 * http://docs.jboss.org/keycloak/docs/1.2.0.CR1/userguide/html/direct-access-grants.html
	 * Make sure the realm has the Direct Grant API switch ON (it can be found on Settings/Login page!)
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private AccessTokenResponse getToken() throws ClientProtocolException, IOException {
		logger.debug("URL getToken()");
		
		//cliente = AuthzClient.create();
		
		//return cliente.obtainAccessToken();
		
		return null;
	}

	
	/**
	 * Obtain headers for Keycloack authentication.
	 * @param token
	 * @return
	 */
    HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + new String(token));
        return headers;
      }
}
