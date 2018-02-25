package es.edu.app.handler.factory;

import com.sun.net.httpserver.HttpHandler;

import es.edu.app.enums.WebAppFlow;

public interface WebAppHandlerFactory {
	//TODO: personalizar exception
	public HttpHandler getHandler(WebAppFlow flow) throws Exception;
}
