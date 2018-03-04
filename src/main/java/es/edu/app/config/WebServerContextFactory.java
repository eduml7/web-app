package es.edu.app.config;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import es.edu.app.enums.WebAppFlow;

/**
 * Factory for creating the different contexts of the server
 * 
 * @author edu
 *
 */
public interface WebServerContextFactory {

	public HttpContext createContext(HttpServer server, WebAppFlow path);

}
