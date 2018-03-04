package es.edu.app.filter.api.validation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.HttpMethod;
import es.edu.app.enums.HttpStatus;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.utils.ExchangeUtils;

public class RequestValidationFilter extends Filter {

	private final static Logger LOGGER = Logger.getLogger(RequestValidationFilter.class.getName());

	@Override
	public String description() {
		return "Validation path filter for POST/PUT requests";
	}

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {

		if (httpExchange.getRequestMethod().equals(HttpMethod.POST)
				|| httpExchange.getRequestMethod().equals(HttpMethod.PUT)) {

			LOGGER.log(Level.INFO, this.description());

			if (!httpExchange.getRequestURI().toString().equals(WebAppFlow.USER_API.getPath())) {
				ExchangeUtils.sendApiResponse(httpExchange, HttpStatus.BAD_REQUEST.getDescription(),
						HttpStatus.BAD_REQUEST.getHttpCode());
				LOGGER.log(Level.SEVERE,
						String.format("%s: %s", HttpStatus.BAD_REQUEST.getDescription(), httpExchange.getRequestURI()));
			}
		}

		chain.doFilter(httpExchange);

	}

}
