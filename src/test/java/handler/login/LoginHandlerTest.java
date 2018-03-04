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

import es.edu.app.handler.login.LoginHandler;

public class LoginHandlerTest {

	LoginHandler loginHandler;

	@Mock
	private HttpExchange httpExchange;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		loginHandler = new LoginHandler();
	}

	@Test
	public void handleLoginTest() throws IOException {
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		loginHandler.handle(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(200), eq(0L));
	}

}
