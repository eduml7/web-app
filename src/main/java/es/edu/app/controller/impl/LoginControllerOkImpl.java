package es.edu.app.controller.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.controller.WebAppController;
import es.edu.app.utils.ExchangeUtils;

public class LoginControllerOkImpl implements WebAppController {

	private static final String LOGIN_VIEW = "src/main/resources/html/login_ok.html";

	private HttpExchange httpExchange;

	public LoginControllerOkImpl(HttpExchange httpExchange) {
		super();
		this.httpExchange = httpExchange;
	}

	public void sendResponse() throws IOException {
		Map<String, String> cookies = new HashMap<String, String>();
		clientCookiesToMap(httpExchange, cookies);
		if (cookies.containsKey("caller")) {
			httpExchange.getResponseHeaders().set("Location", cookies.get("caller"));
			httpExchange.getResponseHeaders().set("Pragma", "no-cache");
			httpExchange.getResponseHeaders().set("Expires", "0");
			httpExchange.getResponseHeaders().set("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0");
			httpExchange.sendResponseHeaders(301, -1);
		} else {

			ExchangeUtils.sendResponse(httpExchange, LOGIN_VIEW);
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

}
