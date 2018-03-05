package config;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import es.edu.app.auth.ApiAuthenticator;
import es.edu.app.auth.ViewAuthenticator;
import es.edu.app.config.WebServerContextFactory;
import es.edu.app.config.WebServerContextFactoryImpl;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.filter.api.ApiContentNegotiationFilter;
import es.edu.app.filter.api.ApiParameterFilter;
import es.edu.app.filter.api.validation.RequestValidationFilter;
import es.edu.app.filter.api.validation.UserValidationFilter;
import es.edu.app.filter.view.SessionFilter;
import es.edu.app.filter.view.ViewAuthorizationFilter;
import es.edu.app.filter.view.ViewParameterFilter;
import es.edu.app.handler.api.UserApiHandler;
import es.edu.app.handler.login.LoginHandler;
import es.edu.app.handler.login.LoginSuccessfulHandler;
import es.edu.app.handler.login.LogoutHandler;
import es.edu.app.handler.page.Page1Handler;
import es.edu.app.handler.page.Page2Handler;
import es.edu.app.handler.page.Page3Handler;

public class WebServerContextFactoryTest {

	private WebServerContextFactory webServerContextFactory;
	private HttpServer httpServer;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		webServerContextFactory = new WebServerContextFactoryImpl();
		httpServer = HttpServer.create();
	}

	@After
	public void tearDown() throws Exception {
		httpServer.stop(0);
	}

	@Test
	public void createLoginContext() {
		HttpContext context = webServerContextFactory.createContext(httpServer, WebAppFlow.LOGIN);
		Assert.assertTrue(context.getHandler() instanceof LoginHandler);
		Assert.assertEquals(context.getFilters().size(), 1);
		Assert.assertTrue(context.getFilters().get(0) instanceof ViewParameterFilter);
	}

	@Test
	public void createLogoutContext() {
		HttpContext context = webServerContextFactory.createContext(httpServer, WebAppFlow.LOGOUT);
		Assert.assertTrue(context.getHandler() instanceof LogoutHandler);
	}

	@Test
	public void createLoginSuccessContext() {
		HttpContext context = webServerContextFactory.createContext(httpServer, WebAppFlow.LOGIN_SUCCESSFUL);
		Assert.assertTrue(context.getHandler() instanceof LoginSuccessfulHandler);
		Assert.assertEquals(context.getFilters().size(), 1);
		Assert.assertTrue(context.getFilters().get(0) instanceof ViewParameterFilter);
		Assert.assertTrue(context.getAuthenticator() instanceof ViewAuthenticator);
	}

	@Test
	public void createPage1Context() {
		HttpContext context = webServerContextFactory.createContext(httpServer, WebAppFlow.PAGE_1);
		Assert.assertEquals(context.getFilters().size(), 3);
		Assert.assertTrue(context.getFilters().get(0) instanceof ViewParameterFilter);
		Assert.assertTrue(context.getFilters().get(1) instanceof SessionFilter);
		Assert.assertTrue(context.getFilters().get(2) instanceof ViewAuthorizationFilter);
		Assert.assertTrue(context.getHandler() instanceof Page1Handler);
	}

	@Test
	public void createPage2Context() {
		HttpContext context = webServerContextFactory.createContext(httpServer, WebAppFlow.PAGE_2);
		Assert.assertEquals(context.getFilters().size(), 3);
		Assert.assertTrue(context.getFilters().get(0) instanceof ViewParameterFilter);
		Assert.assertTrue(context.getFilters().get(1) instanceof SessionFilter);
		Assert.assertTrue(context.getFilters().get(2) instanceof ViewAuthorizationFilter);
		Assert.assertTrue(context.getHandler() instanceof Page2Handler);
	}

	@Test
	public void createPage3Context() {
		HttpContext context = webServerContextFactory.createContext(httpServer, WebAppFlow.PAGE_3);
		Assert.assertEquals(context.getFilters().size(), 3);
		Assert.assertTrue(context.getFilters().get(0) instanceof ViewParameterFilter);
		Assert.assertTrue(context.getFilters().get(1) instanceof SessionFilter);
		Assert.assertTrue(context.getFilters().get(2) instanceof ViewAuthorizationFilter);
		Assert.assertTrue(context.getHandler() instanceof Page3Handler);
	}

	@Test
	public void createUserApiContext() {
		HttpContext context = webServerContextFactory.createContext(httpServer, WebAppFlow.USER_API);
		Assert.assertEquals(context.getFilters().size(), 4);
		Assert.assertTrue(context.getFilters().get(0) instanceof RequestValidationFilter);
		Assert.assertTrue(context.getFilters().get(1) instanceof ApiContentNegotiationFilter);
		Assert.assertTrue(context.getFilters().get(2) instanceof ApiParameterFilter);
		Assert.assertTrue(context.getFilters().get(3) instanceof UserValidationFilter);
		Assert.assertTrue(context.getHandler() instanceof UserApiHandler);
		Assert.assertTrue(context.getAuthenticator() instanceof ApiAuthenticator);
	}
}
