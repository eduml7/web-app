package es.edu.app.controller.impl;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.controller.WebAppController;
import es.edu.app.utils.ExchangeUtils;

public class Page3ControllerImpl implements WebAppController {

	public static final String PAGE_3_VIEW = "src/main/resources/html/users/page_3.html";
	
	private HttpExchange httpExchange;

	public Page3ControllerImpl(HttpExchange httpExchange) {
		super();
		this.httpExchange = httpExchange;
	}

	@Override
	public void sendResponse() throws IOException {
		//Consulta a la BD por si esta
		//si no esta error
		//si esta ya devuelve la vista segun el rol
		ExchangeUtils.sendResponse(httpExchange, PAGE_3_VIEW);
	}
}
