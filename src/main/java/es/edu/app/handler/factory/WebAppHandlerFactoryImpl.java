package es.edu.app.handler.factory;

import com.sun.net.httpserver.HttpHandler;

import es.edu.app.enums.WebAppFlow;
import es.edu.app.handler.LoginHandler;
import es.edu.app.handler.LogoutHandler;
import es.edu.app.handler.Page1ControllerHandler;
import es.edu.app.handler.Page2ControllerHandler;
import es.edu.app.handler.Page3ControllerHandler;
import es.edu.app.handler.LoginSuccessfulControllerHandler;
import es.edu.app.handler.UserApiHandler;

public class WebAppHandlerFactoryImpl implements WebAppHandlerFactory {

	@Override
	public HttpHandler getHandler(WebAppFlow flow) throws Exception {

		switch (flow) {
		case LOGIN:
			return new LoginHandler();
		case LOGOUT:
			return new LogoutHandler();
		case LOGIN_SUCCESSFUL:
			return new LoginSuccessfulControllerHandler();
		case USER_API:
			return new UserApiHandler();
		case PAGE_1:
			return new Page1ControllerHandler();
		case PAGE_2:
			return new Page2ControllerHandler();
		case PAGE_3:
			return new Page3ControllerHandler();
		default:
			throw new Exception("Operation not allowed");
		}
	}

}
