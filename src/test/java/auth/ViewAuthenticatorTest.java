package auth;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.Authenticator.Result;
import com.sun.net.httpserver.Authenticator.Success;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.auth.ViewAuthenticator;
import es.edu.app.constants.WebAppExchangeAttributes;
import es.edu.app.enums.Role;
import es.edu.app.persistence.PersistenceEngine;
import es.edu.app.persistence.entity.User;

public class ViewAuthenticatorTest {

	ViewAuthenticator viewAuthenticator;
	Headers header;
	Map<String, String> params;
	@Mock
	private HttpExchange httpExchange;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		viewAuthenticator = new ViewAuthenticator();
		params = new HashMap<String, String>();
		params.put("username", "admin");
		params.put("password", "admin");

		header = new Headers();
		header.add("Authorization", "Basic YWRtaW46YWRtaW4=");// admin:admin
	}

	@Test
	public void viewAuthenticationOKTest() {
		when(httpExchange.getAttribute(WebAppExchangeAttributes.PARAMETERS)).thenReturn(params);
		List<Role> list = new ArrayList<Role>();
		list.add(Role.ADMIN);
		PersistenceEngine.getPersistence().put("admin", new User("admin", "admin", list));
		when(httpExchange.getRequestHeaders()).thenReturn(header);
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		Result result = viewAuthenticator.authenticate(httpExchange);
		Assert.assertTrue(result instanceof Success);

	}

	@Test
	public void apiAuthenticationKOTest() throws IOException, URISyntaxException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", "admin");
		params.put("password", "nope");
		when(httpExchange.getAttribute(WebAppExchangeAttributes.PARAMETERS)).thenReturn(params);
		PersistenceEngine.getPersistence().put("admin", new User("admin", "admin", null));
		when(httpExchange.getRequestHeaders()).thenReturn(header);

		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		viewAuthenticator.authenticate(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(401), eq(0L));

	}

}
