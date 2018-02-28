package es.edu.app.handler.factory;

import com.sun.net.httpserver.HttpHandler;

import es.edu.app.enums.WebAppFlow;
import es.edu.app.handler.LoginHandler;
import es.edu.app.handler.LogoutHandler;
import es.edu.app.handler.Page1ControllerHandler;
import es.edu.app.handler.PageControllerHandler;
import es.edu.app.handler.UserApiHandler;

public class WebAppHandlerFactoryImpl implements WebAppHandlerFactory {

	@Override
	public HttpHandler getHandler(WebAppFlow flow) throws Exception {

		switch (flow) {
		case LOGIN:
			return new LoginHandler();
		case LOGOUT:
			return new LogoutHandler();
		case PAGE_CONTROLLER:
			return new PageControllerHandler();
		case USER_API:
			return new UserApiHandler();
		case PAGE_1:
			return new Page1ControllerHandler();
		default:
			throw new Exception("Operation not allowed");
		}
	}

}
