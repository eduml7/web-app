package es.edu.app.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import es.edu.app.config.SessionConfig;
import es.edu.app.dto.UserDTO;
import es.edu.app.enums.WebAppFlow;

public class AuthorizationFilter extends Filter {

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
		
		Map<String, String> cookies = new HashMap<String, String>();
		clientCookiesToMap(httpExchange, cookies);

		UserDTO user = SessionConfig.getSession().get(cookies.get("session"));
		
		if (!user.getRoles().contains(WebAppFlow.fromPath(httpExchange.getRequestURI().toString()).getRole())) {
			httpExchange.getResponseHeaders().set("Pragma", "no-cache");
			httpExchange.getResponseHeaders().set("Expires", "0");
			httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
			httpExchange.sendResponseHeaders(401, -1);
		} else {
			chain.doFilter(httpExchange);
		}
	}
	public void clientCookiesToMap( HttpExchange e, Map<String, String> cookies ) {
	    Map<String, List<String>> headers = e.getRequestHeaders();
	    List<String> cookieHeaders = headers.get( "cookie" );
	    if ( cookieHeaders != null ) {
	      cookieHeaders.stream()
	        .map( cookieHeader -> cookieHeader.split( "\\s*;\\s*" ) )
	        .map( cookieArray -> Arrays.asList( cookieArray ) )
	        .forEach( cookieList -> {
	            cookieList.stream()
	              .map( keyValue -> keyValue.split( "\\s*=\\s*" ) )
	              .forEach( pair -> cookies.put( pair[ 0 ], pair[ 1 ] ) );
	          } );
	    }
	}
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Authorization filter";
	}

}
