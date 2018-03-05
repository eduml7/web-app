package filter.view;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.Filter.Chain;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.dto.UserDTO;
import es.edu.app.enums.Role;
import es.edu.app.filter.view.ViewAuthorizationFilter;
import es.edu.app.session.Session;

public class ViewAuthorizationFilterTest {
	
	private ViewAuthorizationFilter viewAuthorizationFilter;
	private Headers header;
	
	@Mock
	private HttpExchange httpExchange;
	@Mock
	private Chain chain;


	@Before
	public void setUp() throws Exception {
		header = new Headers();
		MockitoAnnotations.initMocks(this);
		viewAuthorizationFilter = new ViewAuthorizationFilter();
		List<Role> list = new ArrayList<Role>();
		list.add(Role.PAGE_1);
		UserDTO userDTO = new UserDTO();
		userDTO.setPassword("b");
		userDTO.setUsername("a");
		userDTO.setRoles(list);
		Session.getSession().put("4564", userDTO);
		header.add(WebAppCookies.COOKIE, "session=4564");
	}
	
	@Test
	public void viewAuthorizationFilterDescriptionTest() throws IOException {
		Assert.assertEquals(viewAuthorizationFilter.description(), "View Authorization filter");
	}

	@Test
	public void viewParameterFilterRedirectParamTest() throws IOException, URISyntaxException {
		when(httpExchange.getRequestHeaders()).thenReturn(header);
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		when(httpExchange.getRequestURI()).thenReturn(new URI("/page_2"));
		viewAuthorizationFilter.doFilter(httpExchange, chain);
		verify(httpExchange).sendResponseHeaders(eq(403), eq(0L));
	}
	
	@Test
	public void viewParameterFilterContinueParamTest() throws IOException, URISyntaxException {
		when(httpExchange.getRequestHeaders()).thenReturn(header);
		when(httpExchange.getResponseHeaders()).thenReturn(new Headers());
		when(httpExchange.getResponseBody()).thenReturn(new ByteArrayOutputStream());
		when(httpExchange.getRequestURI()).thenReturn(new URI("/page_1"));
		viewAuthorizationFilter.doFilter(httpExchange, chain);
		verify(chain).doFilter(httpExchange);
	}
	
}
