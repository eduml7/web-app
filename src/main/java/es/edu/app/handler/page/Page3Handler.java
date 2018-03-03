package es.edu.app.handler.page;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.utils.ExchangeUtils;

public class Page3Handler implements HttpHandler {

	private static final String PAGE_3_VIEW = "src/main/resources/html/users/page_3.html";

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		ExchangeUtils.sendPageResponse(httpExchange, PAGE_3_VIEW);

	}

}
