package handler.page;

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
import es.edu.app.dto.UserDTO;
import es.edu.app.handler.page.Page2Handler;
import es.edu.app.session.Session;

public class Page2HandlerTest {

	private Page2Handler page2Handler;
	private Headers header;

	@Mock
	private HttpExchange httpExchange;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		header = new Headers();
		page2Handler = new Page2Handler();
		Session.getSession().put("4564", new UserDTO("a", "a", null));
		header.add(WebAppCookies.COOKIE, "session=4564");
	}

	@Test
	public void handlePage1Test() throws IOException {
		when(httpExchange.getRequestHeaders()).thenReturn(header);
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		page2Handler.handle(httpExchange);
		verify(httpExchange).sendResponseHeaders(eq(200), eq(0L));
	}

}
