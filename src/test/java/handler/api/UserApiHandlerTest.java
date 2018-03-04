package handler.api;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.handler.api.UserApiHandler;

public class UserApiHandlerTest {

	UserApiHandler userApiHandler;

	@Mock
	private HttpExchange httpExchange;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		userApiHandler = new UserApiHandler();
	}

	@Test
	public void handleUserApiGETTest() throws IOException {
		
 
	}


}
