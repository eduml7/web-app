package es.edu.app.controller.impl;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.controller.WebAppController;
import es.edu.app.utils.ExchangeUtils;

public class PageControllerImpl implements WebAppController {
	
	public static final String PAGE_1_VIEW = "src/main/resources/html/users/page_1.html";
	public static final String PAGE_2_VIEW = "src/main/resources/html/users/page_2.html";
	public static final String PAGE_3_VIEW = "src/main/resources/html/users/page_3.html";
	
	private HttpExchange httpExchange;

	public PageControllerImpl(HttpExchange httpExchange) {
		super();
		this.httpExchange = httpExchange;
	}

	@Override
	public void sendResponse() throws IOException {
		ExchangeUtils.sendResponse(httpExchange, PAGE_1_VIEW);
	}
}
