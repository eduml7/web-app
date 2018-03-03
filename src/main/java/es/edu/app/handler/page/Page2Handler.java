package es.edu.app.handler.page;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.enums.HttpStatus;
import es.edu.app.utils.ExchangeUtils;

public class Page2Handler implements HttpHandler {
	
	private static final String PAGE_2_VIEW = "src/main/resources/html/users/page_2.html";

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		ExchangeUtils.sendViewResponse(httpExchange, PAGE_2_VIEW, HttpStatus.OK.getHttpCode());

	}

}
