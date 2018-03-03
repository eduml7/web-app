package es.edu.app.filter.view;

import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Map;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.session.Cookie;
import es.edu.app.session.CookieUtils;
import es.edu.app.utils.ExchangeUtils;

public class SessionFilter extends Filter {

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {

		Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);

		if (!cookies.containsKey(WebAppCookies.SESSION)) {
			httpExchange.getResponseHeaders().add(WebAppCookies.SET_COOKIE_HEADER,
					CookieUtils.createCookie(new Cookie(WebAppCookies.CALLER, httpExchange.getRequestURI().toString(),
							OffsetDateTime.now().plus(Duration.ofMinutes(5)))));
			ExchangeUtils.sendRedirectionResponse(httpExchange, "http://localhost:9010/login");
		} else {
			chain.doFilter(httpExchange);
		}
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Session filter";
	}
}
