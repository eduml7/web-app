package es.edu.app.filter.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppExchangeAttributes;

/**
 * Get params in http requests to the api
 * 
 * @author edu
 *
 */
public class ApiParameterFilter extends Filter {

	private final static Logger LOGGER = Logger.getLogger(ApiParameterFilter.class.getName());

	private static final String CARRIER_RETURN = "\n";
	private static final String SLASH = "/";

	@Override
	public String description() {
		return "Parses the requested URI for parameters";
	}

	@Override
	public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
		LOGGER.log(Level.INFO, this.description());
		getRequestBody(exchange);
		getPathParam(exchange);
		chain.doFilter(exchange);
	}

	private void getRequestBody(HttpExchange httpExchange) {
		String body = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody())).lines()
				.collect(Collectors.joining(CARRIER_RETURN));
		httpExchange.setAttribute(WebAppExchangeAttributes.BODY, body);
	}

	private void getPathParam(HttpExchange httpExchange) {
		String[] result = httpExchange.getRequestURI().getPath().split(SLASH);
		httpExchange.setAttribute(WebAppExchangeAttributes.PATH, result[result.length - 1]);
	}

}
