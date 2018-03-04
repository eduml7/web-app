package filter.api;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.Filter.Chain;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.MediaType;
import es.edu.app.filter.api.ApiContentNegotiationFilter;

public class ApiContentNegotiationFilterTest {

	ApiContentNegotiationFilter apiContentNegotiationFilter;

	@Mock
	private HttpExchange httpExchange;
	@Mock
	private Chain chain;
	Headers header;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		apiContentNegotiationFilter = new ApiContentNegotiationFilter();
		header = new Headers();

		header.add(MediaType.CONTENT_TYPE, MediaType.APPLICATION_JSON);
		header.add(MediaType.ACCEPT, MediaType.APPLICATION_JSON);

	}

	@Test
	public void descriptionTest() {
		Assert.assertEquals(apiContentNegotiationFilter.description(), "Content negotiation filter");
	}

	@Test
	public void apiNotAcceptableTest() throws IOException, URISyntaxException {
		Headers headerKo = new Headers();
		headerKo.add(MediaType.CONTENT_TYPE, "MEDIA");
		headerKo.add(MediaType.ACCEPT, "MEDIA");
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getRequestMethod()).thenReturn("GET");
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		when(httpExchange.getRequestHeaders()).thenReturn(headerKo);
		apiContentNegotiationFilter.doFilter(httpExchange, chain);
		verify(httpExchange).sendResponseHeaders(eq(406), eq(0L));
	}

	@Test
	public void apiUnsupportedTypeTest() throws IOException, URISyntaxException {
		Headers headerKo = new Headers();
		headerKo.add(MediaType.CONTENT_TYPE, "MEDIA");
		headerKo.add(MediaType.ACCEPT,  MediaType.APPLICATION_JSON);
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getRequestMethod()).thenReturn("POST");
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		when(httpExchange.getRequestHeaders()).thenReturn(headerKo);
		apiContentNegotiationFilter.doFilter(httpExchange, chain);
		verify(httpExchange).sendResponseHeaders(eq(415), eq(0L));
	}
	
	@Test
	public void apiPostOkTest() throws IOException, URISyntaxException {
		when(httpExchange.getRequestHeaders()).thenReturn(header);
		when(httpExchange.getRequestMethod()).thenReturn("POST");
		apiContentNegotiationFilter.doFilter(httpExchange, chain);
		verify(chain).doFilter(httpExchange);
	}
	
	@Test
	public void apiGetOkTest() throws IOException, URISyntaxException {
		when(httpExchange.getRequestHeaders()).thenReturn(header);
		when(httpExchange.getRequestMethod()).thenReturn("GET");
		apiContentNegotiationFilter.doFilter(httpExchange, chain);
		verify(chain).doFilter(httpExchange);
	}

}
