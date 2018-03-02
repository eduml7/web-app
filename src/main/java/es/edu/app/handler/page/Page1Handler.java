package es.edu.app.handler.page;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.utils.ExchangeUtils;

public class Page1Handler implements HttpHandler {
	public static final String PAGE_1_VIEW = "src/main/resources/html/users/page_1.html";

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		ExchangeUtils.sendPageResponse(httpExchange, PAGE_1_VIEW);

	}

}
