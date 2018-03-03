package es.edu.app.filter.view;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.WebAppCookies;
import es.edu.app.dto.UserDTO;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.session.CookieUtils;
import es.edu.app.session.Session;
import es.edu.app.utils.ExchangeUtils;

public class ViewAuthorizationFilter extends Filter {

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {

		Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);

		UserDTO user = Session.getSession().get(cookies.get(WebAppCookies.SESSION));

		if (!user.getRoles().contains(WebAppFlow.fromPath(httpExchange.getRequestURI().toString()).getRole())) {
			ExchangeUtils.sendForbiddenResponse(httpExchange);
		} else {
			chain.doFilter(httpExchange);
		}
	}

	@Override
	public String description() {
		return "View Authorization filter";
	}

}
