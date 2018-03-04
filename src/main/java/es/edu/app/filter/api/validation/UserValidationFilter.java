package es.edu.app.filter.api.validation;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.Errors;
import es.edu.app.constants.HttpMethod;
import es.edu.app.constants.WebAppExchangeAttributes;
import es.edu.app.dto.UserDTO;
import es.edu.app.enums.HttpStatus;
import es.edu.app.exception.SerializationDeserializationException;
import es.edu.app.utils.ExchangeUtils;
import es.edu.app.utils.JsonUtils;

public class UserValidationFilter extends Filter {

	private final static Logger LOGGER = Logger.getLogger(UserValidationFilter.class.getName());

	@Override
	public String description() {
		return "Validation filter for user POST/PUT requests";
	}

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
		
		if (httpExchange.getRequestMethod().equals(HttpMethod.POST)
				|| httpExchange.getRequestMethod().equals(HttpMethod.PUT)) {
			try {
				LOGGER.log(Level.INFO, this.description());
				UserDTO user = JsonUtils.jsonToObject(
						httpExchange.getAttribute(WebAppExchangeAttributes.BODY).toString(), UserDTO.class);
				if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())
						|| user.getRoles().isEmpty()) {
					ExchangeUtils.sendApiResponse(httpExchange, Errors.INPUT_USER_VALIDATION_ERROR,
							HttpStatus.BAD_REQUEST.getHttpCode());
					LOGGER.log(Level.INFO, Errors.INPUT_USER_VALIDATION_ERROR);
				}
			} catch (SerializationDeserializationException e) {
				ExchangeUtils.sendApiResponse(httpExchange, HttpStatus.BAD_REQUEST.getDescription(),
						HttpStatus.BAD_REQUEST.getHttpCode());
				LOGGER.log(Level.INFO, e.getMessage(), e);
			}
		}

		chain.doFilter(httpExchange);

	}

}
