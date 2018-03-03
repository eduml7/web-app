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

import es.edu.app.constants.WebAppCookies;
import es.edu.app.dto.UserDTO;
import es.edu.app.session.CookieUtils;
import es.edu.app.session.Session;

public class ExchangeUtils {

	private final static Logger LOGGER = Logger.getLogger(ExchangeUtils.class.getName());

	private static final String USER_NAME_PARAM = "\\{USER_NAME\\}";
	
	public static void sendViewResponse(HttpExchange httpExchange, String view, int statusCode) {
		try {
			commonHeaders(httpExchange);
			httpExchange.sendResponseHeaders(statusCode, 0);
			OutputStream responseBody = httpExchange.getResponseBody();
			responseBody.write(Files.readAllBytes(new File(view).toPath()));
			responseBody.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	

	public static void sendApiResponse(HttpExchange httpExchange, String message, int statusCode) {
		try {
			commonHeaders(httpExchange);
			httpExchange.sendResponseHeaders(statusCode, message.getBytes().length);
			OutputStream responseBody = httpExchange.getResponseBody();
			responseBody.write(message.getBytes());
			responseBody.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}


	public static void sendPageResponse(HttpExchange httpExchange, String view) {
		try {
			httpExchange.sendResponseHeaders(200, 0);
			OutputStream os = httpExchange.getResponseBody();
			String content = new String(Files.readAllBytes(new File(view).toPath()), StandardCharsets.UTF_8);
			Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);

			UserDTO user = Session.getSession().get(cookies.get(WebAppCookies.SESSION));

			content = content.replaceAll(USER_NAME_PARAM, user.getUsername());
			os.write(content.getBytes());
			os.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public static void sendRedirectionResponse(HttpExchange httpExchange, String location) {
		try {
			commonHeaders(httpExchange);
			httpExchange.getResponseHeaders().set("Location", location);
			httpExchange.sendResponseHeaders(301, -1);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private static void commonHeaders(HttpExchange httpExchange) {
		httpExchange.getResponseHeaders().set("Pragma", "no-cache");
		httpExchange.getResponseHeaders().set("Expires", "0");
		httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
	}

}
