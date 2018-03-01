package es.edu.app.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;

//TODO: naming de la clase
public class ExchangeUtils {

	private final static Logger LOGGER = Logger.getLogger(ExchangeUtils.class.getName());

	public static void sendResponse(HttpExchange httpExchange, String view) throws IOException {
		String response = "";
		httpExchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = httpExchange.getResponseBody();

		os.write(Files.readAllBytes(new File(view).toPath()));
		os.close();
	}

	public static void sendForbiddenResponse(HttpExchange httpExchange) {
		try {
			httpExchange.getResponseHeaders().set("Pragma", "no-cache");
			httpExchange.getResponseHeaders().set("Expires", "0");
			httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
			httpExchange.sendResponseHeaders(401, -1);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	public static void sendRedirectionResponse(HttpExchange httpExchange, String location) {
		try {
			// TODO: cambiar a map, y putall
			httpExchange.getResponseHeaders().set("Location", location);
			httpExchange.getResponseHeaders().set("Pragma", "no-cache");
			httpExchange.getResponseHeaders().set("Expires", "0");
			httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
			httpExchange.sendResponseHeaders(301, -1);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

}
