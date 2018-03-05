package filter.api;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.Filter.Chain;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppExchangeAttributes;
import es.edu.app.filter.api.ApiParameterFilter;

public class ApiParameterFilterTest {

	private ApiParameterFilter apiParameterFilter;

	@Mock
	private HttpExchange httpExchange;
	
	@Mock
	private Chain chain;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		apiParameterFilter = new ApiParameterFilter();
	}

	@Test
	public void descriptionTest() {
		Assert.assertEquals(apiParameterFilter.description(), "Parses the requested URI for parameters");
	}

	@Test
	public void apiParameterFilterPathTest() throws IOException, URISyntaxException {

		when(httpExchange.getRequestURI()).thenReturn(new URI("/path"));
		when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(new byte[0]));
		apiParameterFilter.doFilter(httpExchange, chain);
		verify(httpExchange).setAttribute(WebAppExchangeAttributes.PATH, "path");
	}

	@Test
	public void apiParameterFilterBodyTest() throws IOException, URISyntaxException {
		when(httpExchange.getRequestURI()).thenReturn(new URI("/path"));
		when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream(new byte[0]));
		apiParameterFilter.doFilter(httpExchange, chain);
		verify(httpExchange).setAttribute(WebAppExchangeAttributes.BODY, "");
	}

}
