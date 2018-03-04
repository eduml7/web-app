package es.edu.app.filter.view;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.dto.UserDTO;
import es.edu.app.enums.HttpStatus;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.session.CookieUtils;
import es.edu.app.session.Session;
import es.edu.app.utils.ExchangeUtils;

/**
 * Checks if the user in session hash the sufficient privileges to view the page
 * 
 * @author edu
 *
 */
public class ViewAuthorizationFilter extends Filter {

	private final static Logger LOGGER = Logger.getLogger(ViewAuthorizationFilter.class.getName());

	private static final String FORBIDDEN = "src/main/resources/html/error/403.html";

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
		LOGGER.log(Level.INFO, this.description());
		Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);

		UserDTO user = Session.getSession().get(cookies.get(WebAppCookies.SESSION));

		if (!user.getRoles().contains(WebAppFlow.fromPath(httpExchange.getRequestURI().toString()).getRole())) {
			ExchangeUtils.sendViewResponse(httpExchange, FORBIDDEN, HttpStatus.FORBIDDEN.getHttpCode());
		} else {
			chain.doFilter(httpExchange);
		}
	}

	@Override
	public String description() {
		return "View Authorization filter";
	}

}
