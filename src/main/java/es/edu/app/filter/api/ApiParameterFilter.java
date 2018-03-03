package es.edu.app.filter.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppExchangeAttributes;

public class ApiParameterFilter extends Filter {

	private static final String CARRIER_RETURN = "\n";
	private static final String SLASH = "/";

	@Override
	public String description() {
		return "Parses the requested URI for parameters";
	}

	@Override
	public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
		getRequestBody(exchange);
		getPathParam(exchange);
		chain.doFilter(exchange);
	}

	public void getRequestBody(HttpExchange httpExchange) {
		String body = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody())).lines()
				.collect(Collectors.joining(CARRIER_RETURN));
		httpExchange.setAttribute(WebAppExchangeAttributes.BODY, body);
	}

	public void getPathParam(HttpExchange httpExchange) {
		String[] result = httpExchange.getRequestURI().getPath().split(SLASH);
		httpExchange.setAttribute(WebAppExchangeAttributes.PATH, result[result.length - 1]);
	}

}
