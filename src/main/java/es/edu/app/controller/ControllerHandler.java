package es.edu.app.controller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


	public class ControllerHandler implements HttpHandler {

        @Override

        public void handle(HttpExchange he) throws IOException {
                // parse request
                Map<String, Object> parameters = new HashMap<String, Object>();
                InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String query = br.readLine();
       
                // send response
                String response = "";
                he.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = he.getResponseBody();
                
                os.write(Files.readAllBytes(new File("src/main/resources/html/page_1.html").toPath()));
                os.close();
        }

}
