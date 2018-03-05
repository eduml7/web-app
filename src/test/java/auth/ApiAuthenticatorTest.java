package auth;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.Authenticator.Failure;
import com.sun.net.httpserver.Authenticator.Result;
import com.sun.net.httpserver.Authenticator.Success;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.auth.ApiAuthenticator;
import es.edu.app.enums.Role;
import es.edu.app.persistence.PersistenceEngine;
import es.edu.app.persistence.entity.User;

public class ApiAuthenticatorTest {

	private ApiAuthenticator apiAuthenticator;
	private Headers header;
	
	@Mock
	private HttpExchange httpExchange;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		apiAuthenticator = new ApiAuthenticator("API");
		header = new Headers();
		header.add("Authorization", "Basic YWRtaW46YWRtaW4=");//admin:admin
	}

	@Test
	public void apiAuthenticationOKTest() {
		List<Role> list = new ArrayList<Role>();
		list.add(Role.ADMIN);
		PersistenceEngine.getPersistence().put("admin", new User("admin", "admin", list));
		when(httpExchange.getRequestHeaders()).thenReturn(header);
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getRequestMethod()).thenReturn("POST");
		Result result = apiAuthenticator.authenticate(httpExchange);
		Assert.assertTrue(result instanceof Success);
		
	}

	@Test
	public void apiAuthenticationKOTest() throws IOException, URISyntaxException {
		PersistenceEngine.getPersistence().put("blabla", new User("admin", "admin", null));
		when(httpExchange.getRequestHeaders()).thenReturn(header);
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getRequestMethod()).thenReturn("POST");
		Result result = apiAuthenticator.authenticate(httpExchange);
		Assert.assertTrue(result instanceof Failure);
	}

}
