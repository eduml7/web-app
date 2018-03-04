package es.edu.app.handler.page;

import java.io.IOException;
import java.util.Observable;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.enums.HttpStatus;
import es.edu.app.utils.ExchangeUtils;

public class Page1Handler extends Observable implements HttpHandler {
	
	private static final String PAGE_1_VIEW = "src/main/resources/html/users/page_1.html";

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		
		setChanged();
        notifyObservers(httpExchange);
        
		ExchangeUtils.sendViewResponse(httpExchange, PAGE_1_VIEW, HttpStatus.OK.getHttpCode());

	}

}
