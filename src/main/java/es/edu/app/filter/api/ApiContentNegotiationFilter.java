package es.edu.app.filter.api;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.HttpMethod;
import es.edu.app.constants.MediaType;
import es.edu.app.enums.HttpStatus;
import es.edu.app.utils.ExchangeUtils;

public class ApiContentNegotiationFilter extends Filter {
	
	private final static Logger LOGGER = Logger.getLogger(ApiContentNegotiationFilter.class.getName());

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
		
		LOGGER.log(Level.INFO, this.description());

		Headers requestHeaders = httpExchange.getRequestHeaders();
		String contentType = requestHeaders.getFirst(MediaType.CONTENT_TYPE);
		String accept = requestHeaders.getFirst(MediaType.ACCEPT);

		if (!accept.equals(MediaType.APPLICATION_JSON)) {
			ExchangeUtils.sendApiResponse(httpExchange, HttpStatus.NOT_ACCEPTABLE.getDescription(),
					HttpStatus.NOT_ACCEPTABLE.getHttpCode());
		} else {

			if ((httpExchange.getRequestMethod().equals(HttpMethod.POST)
					|| httpExchange.getRequestMethod().equals(HttpMethod.PUT))
					&& !contentType.startsWith(MediaType.APPLICATION_JSON)) {
				ExchangeUtils.sendApiResponse(httpExchange, HttpStatus.UNSUPPORTED_MEDIA_TYPE.getDescription(),
						HttpStatus.UNSUPPORTED_MEDIA_TYPE.hashCode());
			} else {

				chain.doFilter(httpExchange);
			}
		}
	}

	@Override
	public String description() {
		return "Content negotiation filter";
	}
}
