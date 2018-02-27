package es.edu.app.filter;

import java.io.IOException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class SessionFilter extends Filter {

	@Override
	public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
		if(exchange.getRequestHeaders().get("cookie") == null) {
			exchange.getResponseHeaders().set("Location", "http://localhost:9010/login");
		}
		chain.doFilter(exchange);
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Session filter";
	}

}
