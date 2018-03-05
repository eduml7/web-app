package handler.api;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppExchangeAttributes;
import es.edu.app.handler.api.UserApiHandler;
import es.edu.app.persistence.PersistenceEngine;
import es.edu.app.persistence.entity.User;

public class UserApiHandlerTest {

	private UserApiHandler userApiHandler;

	@Mock
	private HttpExchange httpExchange;

	private String userDTOjson = "{\"username\":\"a\",\"password\":\"b\",\"roles\":[\"ADMIN\"]}";

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		userApiHandler = new UserApiHandler();
		PersistenceEngine.getPersistence().put("username", new User("username", "pass", null));
		PersistenceEngine.getPersistence().put("a", new User("a", "pass", null));
	}

	@Test
	public void handleUserApiGetTest() throws IOException {
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getRequestMethod()).thenReturn("GET");
		when(httpExchange.getAttribute(WebAppExchangeAttributes.PATH)).thenReturn("username");
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		userApiHandler.handle(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(200), eq(0L));
	}

	@Test
	public void handleUserApiGetNoUserTest() throws IOException {
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getRequestMethod()).thenReturn("GET");
		when(httpExchange.getAttribute(WebAppExchangeAttributes.PATH)).thenReturn("nouser");
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		userApiHandler.handle(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(404), eq(0L));
	}

	@Test
	public void handleUserApiDeleteTest() throws IOException {
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getRequestMethod()).thenReturn("DELETE");
		when(httpExchange.getAttribute(WebAppExchangeAttributes.PATH)).thenReturn("username");
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		userApiHandler.handle(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(200), eq(0L));
	}

	@Test
	public void handleUserApiPutTest() throws IOException {
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getRequestMethod()).thenReturn("PUT");
		when(httpExchange.getAttribute(WebAppExchangeAttributes.BODY)).thenReturn(userDTOjson);
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		userApiHandler.handle(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(200), eq(0L));
	}

	@Test
	public void handleUserApiPostTest() throws IOException {
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getRequestMethod()).thenReturn("POST");
		when(httpExchange.getAttribute(WebAppExchangeAttributes.BODY))
				.thenReturn("{\"username\":\"b\",\"password\":\"b\",\"roles\":[\"ADMIN\"]}");
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		userApiHandler.handle(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(201), eq(0L));
	}

	@Test
	public void handleUserApiPostUserExistsTest() throws IOException {
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getRequestMethod()).thenReturn("POST");
		when(httpExchange.getAttribute(WebAppExchangeAttributes.BODY)).thenReturn(userDTOjson);
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		userApiHandler.handle(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(409), eq(0L));
	}

	@Test
	public void handleUserApiNotAllowedTest() throws IOException {
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getRequestMethod()).thenReturn("PATCH");
		when(httpExchange.getAttribute(WebAppExchangeAttributes.BODY)).thenReturn(userDTOjson);
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		userApiHandler.handle(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(405), eq(0L));
	}
}
