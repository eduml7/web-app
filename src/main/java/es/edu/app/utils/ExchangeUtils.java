package es.edu.app.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.dto.UserDTO;
import es.edu.app.session.CookieUtils;
import es.edu.app.session.Session;

//TODO: naming de la clase
public class ExchangeUtils {

	private final static Logger LOGGER = Logger.getLogger(ExchangeUtils.class.getName());

	public static void sendResponse(HttpExchange httpExchange, String view) {
		try {
			String response = "";
			httpExchange.sendResponseHeaders(200, response.getBytes().length);
			OutputStream os = httpExchange.getResponseBody();
			os.write(Files.readAllBytes(new File(view).toPath()));
			os.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	public static void sendPageResponse(HttpExchange httpExchange, String view) {
		try {
			String response = "";
			httpExchange.sendResponseHeaders(200, response.getBytes().length);
			OutputStream os = httpExchange.getResponseBody();
			String content = new String(Files.readAllBytes(new File(view).toPath()), StandardCharsets.UTF_8);
			Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);

			UserDTO user = Session.getSession().get(cookies.get("session"));

			content = content.replaceAll("\\{USER_NAME\\}", user.getUsername());
			os.write(content.getBytes());
			os.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	public static void sendForbiddenResponse(HttpExchange httpExchange) {
		try {
			commonHeaders(httpExchange);
			httpExchange.sendResponseHeaders(401, -1);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	public static void sendRedirectionResponse(HttpExchange httpExchange, String location) {
		try {
			commonHeaders(httpExchange);
			httpExchange.getResponseHeaders().set("Location", location);
			httpExchange.sendResponseHeaders(301, -1);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	private static void commonHeaders(HttpExchange httpExchange) {
		httpExchange.getResponseHeaders().set("Pragma", "no-cache");
		httpExchange.getResponseHeaders().set("Expires", "0");
		httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
	}

}
