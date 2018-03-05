package handler.login;

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

import es.edu.app.constants.WebAppCookies;
import es.edu.app.handler.login.LoginSuccessfulHandler;

public class LoginSuccessfulHandlerTest {

	private LoginSuccessfulHandler loginSuccessfulHandler;

	@Mock
	private HttpExchange httpExchange;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		loginSuccessfulHandler = new LoginSuccessfulHandler();
	}

	@Test
	public void handleLoginSuccessWithoutRedirectTest() throws IOException {
		when(httpExchange.getRequestHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		loginSuccessfulHandler.handle(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(200), eq(0L));
	}

	@Test
	public void handleLoginSuccessWithRedirectTest() throws IOException {
		Headers header = new Headers();
		header.add(WebAppCookies.COOKIE, "caller=/login");
		when(httpExchange.getRequestHeaders()).thenReturn(header);
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		loginSuccessfulHandler.handle(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(301), eq(-1L));
	}

}
