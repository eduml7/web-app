package es.edu.app.config;

import com.sun.net.httpserver.HttpServer;

import es.edu.app.enums.WebAppFlow;

public interface WebServerContextFactory {

	public void createContext(HttpServer server, WebAppFlow path);
	
}
