package es.edu.app.controller;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.constants.HttpMethod;

public class API implements HttpHandler {

	@Override
	public void handle(HttpExchange he) throws IOException {

		String response = "Method not allowed";

		switch (he.getRequestMethod()) {
		case HttpMethod.POST:
			response = HttpMethod.POST;
			break;
		case HttpMethod.PUT:
			response = HttpMethod.PUT;
			break;
		case HttpMethod.GET:
			response = HttpMethod.GET;
			break;
		}

		he.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = he.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

}
