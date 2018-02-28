package es.edu.app.controller;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.HttpMethod;
import es.edu.app.persistence.repository.UserRepository;
import es.edu.app.persistence.repository.UserRepositoryImpl;

public class UserApiController {

	private UserRepository userRepo;
	private HttpExchange httpExchange;

	public UserApiController(HttpExchange httpExchange) {
		super();
		this.httpExchange = httpExchange;
		userRepo = new UserRepositoryImpl();
	}

	public void sendResponse() throws IOException {
		String response = "Method not allowed";
		switch (httpExchange.getRequestMethod()) {
		case HttpMethod.POST:
			response = userRepo.createUser(null).toString();
			break;
		case HttpMethod.PUT:
			response = userRepo.updateUser(null).toString();
			break;
		case HttpMethod.GET:
			response = userRepo.getUser("admin").toString();
			break;
		case HttpMethod.DELETE:
			response = userRepo.deleteUser("").toString();
			break;
		}

		httpExchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = httpExchange.getResponseBody();

		os.write(response.getBytes());
		os.close();

	}

}
