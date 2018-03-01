package es.edu.app.filter;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.session.CookieUtils;

public class SessionFilter extends Filter {

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {

		Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);

		if (!cookies.containsKey("session")) {
			httpExchange.getResponseHeaders().add("set-cookie",
					"caller=" + httpExchange.getRequestURI() + "; expires=Sat, 03 May 2025 17:44:22 GMT");
			httpExchange.getResponseHeaders().set("Location", "http://localhost:9010/login");
			httpExchange.getResponseHeaders().set("Pragma", "no-cache");
			httpExchange.getResponseHeaders().set("Expires", "0");
			httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
			httpExchange.sendResponseHeaders(301, -1);
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
