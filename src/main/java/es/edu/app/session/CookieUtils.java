package es.edu.app.session;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppCookies;

/**
 * Creates and gets cookies
 * 
 * @author edu
 *
 */
public class CookieUtils {

	public static final String COOKIE_HEADER = "\\s*;\\s*";
	public static final String COOKIE_KEY_VALUE = "\\s*=\\s*";

	public static Map<String, String> clientCookiesToMap(HttpExchange httpExchange) {

		Map<String, List<String>> headers = httpExchange.getRequestHeaders();
		List<String> cookieHeaders = headers.get(WebAppCookies.COOKIE);
		Map<String, String> cookies = new HashMap<String, String>();

		if (cookieHeaders != null) {
			cookieHeaders.stream().map(cookieHeader -> cookieHeader.split(COOKIE_HEADER))
					.map(cookieArray -> Arrays.asList(cookieArray)).forEach(cookieList -> {
						cookieList.stream().map(keyValue -> keyValue.split(COOKIE_KEY_VALUE))
								.forEach(pair -> cookies.put(pair[0], pair[1]));
					});
		}

		return cookies;
	}

	public static String createCookie(Cookie cookie) {
		return String.format("%s=%s; expires=%s", cookie.getKey(), cookie.getValue(),
				DateTimeFormatter.RFC_1123_DATE_TIME.format(cookie.getExpiration()));
	}
}
