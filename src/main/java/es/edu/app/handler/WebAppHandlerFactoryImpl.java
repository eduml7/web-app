package es.edu.app.handler;

import com.sun.net.httpserver.HttpHandler;

import es.edu.app.enums.WebAppFlow;
import es.edu.app.handler.api.UserApiHandler;
import es.edu.app.handler.login.LoginHandler;
import es.edu.app.handler.login.LoginSuccessfulHandler;
import es.edu.app.handler.login.LogoutHandler;
import es.edu.app.handler.page.Page1Handler;
import es.edu.app.handler.page.Page2Handler;
import es.edu.app.handler.page.Page3Handler;

public class WebAppHandlerFactoryImpl implements WebAppHandlerFactory {

	@Override
	public HttpHandler getHandler(WebAppFlow flow) throws Exception {

		switch (flow) {
		case LOGIN:
			return new LoginHandler();
		case LOGOUT:
			return new LogoutHandler();
		case LOGIN_SUCCESSFUL:
			return new LoginSuccessfulHandler();
		case USER_API:
			return new UserApiHandler();
		case PAGE_1:
			return new Page1Handler();
		case PAGE_2:
			return new Page2Handler();
		case PAGE_3:
			return new Page3Handler();
		default:
			throw new Exception("Operation not allowed");
		}
	}

}
