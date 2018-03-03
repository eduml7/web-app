package es.edu.app.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.edu.app.exception.SerializationDeserializationException;

public class JsonUtils {

	private final static Logger LOGGER = Logger.getLogger(JsonUtils.class.getName());

	public static <T> String objectToJson(T object) throws SerializationDeserializationException{
		ObjectMapper objectMapper = new ObjectMapper();
		String result = null;
		try {
			result = objectMapper.writeValueAsString(object);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new SerializationDeserializationException();
		}
		return result;
		
	}
	
	public static <T> T jsonToObject(String json, Class<T> clazz) throws SerializationDeserializationException{
		ObjectMapper objectMapper = new ObjectMapper();
		T result = null;
		try {
			result = (T) objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new SerializationDeserializationException();
		}
		return result;
		
	}

}
