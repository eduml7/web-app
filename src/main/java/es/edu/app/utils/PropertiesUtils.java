package es.edu.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import es.edu.app.WebApplication;

public class PropertiesUtils {

	private final static String CONFIG_FILE = "/web-app.properties";

	public static String getPropValues(String propertyKey) throws IOException {
		Properties prop = new Properties();
		File jarPath = new File(WebApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String propertiesPath = jarPath.getParentFile().getAbsolutePath();
		prop.load(new FileInputStream(propertiesPath + CONFIG_FILE));
		return prop.getProperty(propertyKey);
	}
}
