package filter.view;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.Filter.Chain;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.filter.view.ViewParameterFilter;

public class ViewParameterFilterTest {
	
	ViewParameterFilter viewParameterFilter;

	@Mock
	private HttpExchange httpExchange;
	@Mock
	private Chain chain;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		viewParameterFilter = new ViewParameterFilter();
	}
	
	@Test
	public void viewParameterFilterDescriptionTest() throws IOException {
		Assert.assertEquals(viewParameterFilter.description(), "Parses the requested URI for parameters");
	}

	@Test
	public void viewParameterFilterGetParamTest() throws IOException, URISyntaxException {
		when(httpExchange.getRequestURI()).thenReturn(new URI("/path?a=b"));
		when(httpExchange.getRequestMethod()).thenReturn("GET");
		viewParameterFilter.doFilter(httpExchange, chain);
		verify(httpExchange).setAttribute(isA(String.class), isA(Map.class));
	}
	
	@Test
	public void viewParameterFilterPostParamTest() throws IOException, URISyntaxException {
		when(httpExchange.getRequestURI()).thenReturn(new URI("/path"));
		when(httpExchange.getRequestMethod()).thenReturn("POST");
		when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(new byte[0]));
		viewParameterFilter.doFilter(httpExchange, chain);
		verify(httpExchange).getAttribute("parameters");
	}
	
}
