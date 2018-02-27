package es.edu.app.filter;

import java.io.IOException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class SessionFilter extends Filter {

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
		System.out.println("VES ALGO?");
		if (httpExchange.getRequestHeaders().get("cookie") == null) {
			System.out.println("NO VEO COOKIES");

			httpExchange.getResponseHeaders().set("Location", "http://localhost:9010/login");
			httpExchange.getResponseHeaders().set("Pragma", "no-cache");
			httpExchange.getResponseHeaders().set("Expires", "0");
			httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate");
			httpExchange.sendResponseHeaders(301, -1);
		} else {System.out.println("TIRO DE FILTRO");
			chain.doFilter(httpExchange);
		}
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Session filter";
	}

}
