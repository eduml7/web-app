package es.edu.app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

//TODO: naming de la clase
public class ExchangeUtils {
	
	public static void sendResponse(HttpExchange httpExchange, String view) throws IOException {
		String response = "";
		httpExchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = httpExchange.getResponseBody();
		
		os.write(Files.readAllBytes(new File(view).toPath()));
		os.close();
	}

	public static void parseRequest(HttpExchange httpExchange) throws UnsupportedEncodingException, IOException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String query = br.readLine();
	}

}
