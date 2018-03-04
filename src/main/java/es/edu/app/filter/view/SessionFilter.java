package es.edu.app.filter.view;

import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.session.Cookie;
import es.edu.app.session.CookieUtils;
import es.edu.app.utils.ExchangeUtils;

public class SessionFilter extends Filter {

	private final static Logger LOGGER = Logger.getLogger(SessionFilter.class.getName());

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
		LOGGER.log(Level.INFO, this.description());
		Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);

		if (!cookies.containsKey(WebAppCookies.SESSION)) {
			httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER,
					CookieUtils.createCookie(new Cookie(WebAppCookies.CALLER, httpExchange.getRequestURI().toString(),
							OffsetDateTime.now().plus(Duration.ofMinutes(5)))));
			ExchangeUtils.sendRedirectionResponse(httpExchange, WebAppFlow.LOGIN.getPath());
		} else {
			chain.doFilter(httpExchange);
		}
	}

	@Override
	public String description() {
		return "Session filter";
	}
}
