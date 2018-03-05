package filter.api;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.Filter.Chain;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.filter.api.validation.RequestValidationFilter;

public class RequestValidationFilterTest {

	private RequestValidationFilter requestValidationFilter;

	@Mock
	private HttpExchange httpExchange;
	
	@Mock
	private Chain chain;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		requestValidationFilter = new RequestValidationFilter();
	}

	@Test
	public void descriptionTest() {
		Assert.assertEquals(requestValidationFilter.description(), "Validation path filter for POST/PUT requests");
	}

	@Test
	public void requestValidationFilerPostTest() throws IOException, URISyntaxException {
		when(httpExchange.getRequestMethod()).thenReturn("POST");
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		when(httpExchange.getRequestURI()).thenReturn(new URI("/path"));
		requestValidationFilter.doFilter(httpExchange, chain);
		verify(httpExchange).sendResponseHeaders(eq(400), eq(0L));
	}
	
	@Test
	public void requestValidationFilerGetTest() throws IOException, URISyntaxException {
		when(httpExchange.getRequestMethod()).thenReturn("GET");
		requestValidationFilter.doFilter(httpExchange, chain);
		verify(chain).doFilter(httpExchange);
	}

}
