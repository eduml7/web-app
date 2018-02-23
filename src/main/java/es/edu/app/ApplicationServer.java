package es.edu.app;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import es.edu.app.controller.API;
import es.edu.app.controller.ControllerHandler;
import es.edu.app.controller.LoginHandler;

public class ApplicationServer {
	 public static void main(String[] args) throws Exception {
		 int port = 9000;
		 HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		 System.out.println("server started at " + port);
		 server.createContext("/controller", new ControllerHandler());
		 server.createContext("/login", new LoginHandler());
		 server.createContext("/v1/api", new API());
		 server.setExecutor(null);
		 server.start();
	 }
}
