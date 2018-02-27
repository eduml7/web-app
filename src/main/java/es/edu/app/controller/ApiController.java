package es.edu.app.controller;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import es.edu.app.constants.HttpMethod;
import es.edu.app.persistence.repository.UserRepository;
import es.edu.app.persistence.repository.UserRepositoryImpl;
import es.edu.app.utils.ExchangeUtils;

public class ApiController {

	private UserRepository userRepo;
	private HttpExchange httpExchange;

	public ApiController(HttpExchange httpExchange) {
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

		ExchangeUtils.sendResponse(httpExchange, response);
	}

}
