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
	public void requestValidationFilerKOPostTest() throws IOException, URISyntaxException {
		when(httpExchange.getRequestMethod()).thenReturn("POST");
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		when(httpExchange.getRequestURI()).thenReturn(new URI("/v1/api/user/username"));
		requestValidationFilter.doFilter(httpExchange, chain);
		
	}
	
	@Test
	public void requestValidationFilerOKPostTest() throws IOException, URISyntaxException {
		when(httpExchange.getRequestMethod()).thenReturn("POST");
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		when(httpExchange.getRequestURI()).thenReturn(new URI("/v1/api/user"));
		requestValidationFilter.doFilter(httpExchange, chain);
	}
	
	@Test
	public void requestValidationFilerOKGetTest() throws IOException, URISyntaxException{
		when(httpExchange.getRequestMethod()).thenReturn("GET");
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		when(httpExchange.getRequestURI()).thenReturn(new URI("/v1/api/user/username"));
		requestValidationFilter.doFilter(httpExchange, chain);
		verify(chain).doFilter(httpExchange);
	}
	
	@Test
	public void requestValidationFilerKOGetTest() throws IOException, URISyntaxException{
		when(httpExchange.getRequestMethod()).thenReturn("GET");
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		when(httpExchange.getRequestURI()).thenReturn(new URI("/v1/api/users/username"));
		requestValidationFilter.doFilter(httpExchange, chain);
		verify(httpExchange).sendResponseHeaders(eq(400), eq(0L));
	}

}
