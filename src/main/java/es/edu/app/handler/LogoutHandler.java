package es.edu.app.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class LogoutHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		httpExchange.getResponseHeaders().add("set-cookie", "session=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT");

		httpExchange.getResponseHeaders().add("set-cookie", "caller=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT");

		httpExchange.getResponseHeaders().set("Location", "http://localhost:9010/login");
		httpExchange.getResponseHeaders().set("Pragma", "no-cache");
		httpExchange.getResponseHeaders().set("Expires", "0");
		httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
		httpExchange.sendResponseHeaders(301, -1);
	}

}
