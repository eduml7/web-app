package es.edu.app.handler;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.edu.app.constants.HttpMethod;

public class ApiHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		String response = "Method not allowed";

		switch (httpExchange.getRequestMethod()) {
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

		httpExchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = httpExchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

}
