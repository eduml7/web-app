package es.edu.app.session;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppCookies;

/**
 * Cookie observer, if is notified, increases cookie session life. Is notified
 * in every user action.
 * 
 * @author edu
 *
 */
public class CookieObserver implements Observer {

	private static CookieObserver cookieObserver = null;

	private CookieObserver() {
		// nothing to do here
	}

	public static CookieObserver getObserver() {

		if (cookieObserver == null) {
			cookieObserver = new CookieObserver();
		}
		return cookieObserver;

	}

	@Override
	public void update(Observable o, Object arg) {
		HttpExchange httpExchange = (HttpExchange) arg;
		Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);
		if (cookies.get(WebAppCookies.SESSION) != null) {
			httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER, String.format("%s=%s; expires=%s",
					WebAppCookies.SESSION, cookies.get(WebAppCookies.SESSION),
					DateTimeFormatter.RFC_1123_DATE_TIME.format(OffsetDateTime.now().plus(Duration.ofMinutes(5)))));
		}
	}

}
