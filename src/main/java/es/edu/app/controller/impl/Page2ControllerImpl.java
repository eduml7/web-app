package es.edu.app.controller.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.controller.WebAppController;
import es.edu.app.utils.ExchangeUtils;

public class Page2ControllerImpl implements WebAppController {

	public static final String PAGE_2_VIEW = "src/main/resources/html/users/page_2.html";
	
	private HttpExchange httpExchange;

	public Page2ControllerImpl(HttpExchange httpExchange) {
		super();
		this.httpExchange = httpExchange;
	}

	@Override
	public void sendResponse() throws IOException {
		String response = "";
		httpExchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = httpExchange.getResponseBody();
		String content = new String(Files.readAllBytes(new File(PAGE_2_VIEW).toPath()), StandardCharsets.UTF_8);
		content = content.replaceAll("\\{USER_NAME\\}", "page_2_name!");//httpExchange.getPrincipal().getUsername());
		os.write(content.getBytes());
		os.close();
	}
}
