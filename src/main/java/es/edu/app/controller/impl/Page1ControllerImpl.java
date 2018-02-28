package es.edu.app.controller.impl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.config.SessionConfig;
import es.edu.app.controller.WebAppController;
import es.edu.app.dto.UserDTO;
import es.edu.app.utils.ExchangeUtils;

public class Page1ControllerImpl implements WebAppController {
	
	public static final String PAGE_1_VIEW = "src/main/resources/html/users/page_1.html";
	
	private HttpExchange httpExchange;

	public Page1ControllerImpl(HttpExchange httpExchange) {
		super();
		this.httpExchange = httpExchange;
	}

	@Override
	public void sendResponse() throws IOException {
		String response = "";
		httpExchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = httpExchange.getResponseBody();
		String content = new String(Files.readAllBytes(new File(PAGE_1_VIEW).toPath()), StandardCharsets.UTF_8);

		Map<String, String> cookies = new HashMap<String, String>();
		clientCookiesToMap(httpExchange, cookies);

		UserDTO user = SessionConfig.getSession().get(cookies.get("session"));
		
		content = content.replaceAll("\\{USER_NAME\\}", user.getUsername());
		os.write(content.getBytes());
		os.close();
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
