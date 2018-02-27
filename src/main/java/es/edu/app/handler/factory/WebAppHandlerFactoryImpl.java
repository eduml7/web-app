package es.edu.app.handler.factory;

import com.sun.net.httpserver.HttpHandler;

import es.edu.app.enums.WebAppFlow;
import es.edu.app.handler.ApiHandler;
import es.edu.app.handler.LoginHandler;
import es.edu.app.handler.Page1ControllerHandler;
import es.edu.app.handler.PageControllerHandler;

public class WebAppHandlerFactoryImpl implements WebAppHandlerFactory {

	@Override
	public HttpHandler getHandler(WebAppFlow flow) throws Exception {

		switch (flow) {
		case LOGIN:
			return new LoginHandler();
		case PAGE_CONTROLLER:
			return new PageControllerHandler();
		case API:
			return new ApiHandler();
		case PAGE_1:
			return new Page1ControllerHandler();
		default:
			throw new Exception("Operation not allowed");
		}
	}

}
