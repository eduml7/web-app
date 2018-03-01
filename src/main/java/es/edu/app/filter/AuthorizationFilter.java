package es.edu.app.filter;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.dto.UserDTO;
import es.edu.app.enums.WebAppFlow;
import es.edu.app.session.CookieUtils;
import es.edu.app.session.Session;
import es.edu.app.utils.ExchangeUtils;

public class AuthorizationFilter extends Filter {

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
		
		Map<String, String> cookies = CookieUtils.clientCookiesToMap(httpExchange);

		UserDTO user = Session.getSession().get(cookies.get("session"));
		
		if (!user.getRoles().contains(WebAppFlow.fromPath(httpExchange.getRequestURI().toString()).getRole())) {
			ExchangeUtils.sendForbiddenResponse(httpExchange);
		} else {
			chain.doFilter(httpExchange);
		}
	}



	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Authorization filter";
	}

}
