package es.edu.app.config;

import java.util.ArrayList;
import java.util.List;

import es.edu.app.enums.Role;
import es.edu.app.persistence.PersistenceEngine;
import es.edu.app.persistence.entity.User;

/**
 * Creates the basic users of the system.
 * 
 * @author edu
 *
 */
public class PersistenceConfig {

	public static void init() {

		List<Role> adminRole = new ArrayList<Role>();
		adminRole.add(Role.ADMIN);

		List<Role> page1Role = new ArrayList<Role>();
		page1Role.add(Role.PAGE_1);
		page1Role.add(Role.PAGE_2);

		List<Role> page2Role = new ArrayList<Role>();
		page2Role.add(Role.PAGE_2);

		List<Role> page3Role = new ArrayList<Role>();
		page3Role.add(Role.PAGE_3);
		page3Role.add(Role.PAGE_2);

		PersistenceEngine.getPersistence().put("admin", new User("admin", "admin", adminRole));
		PersistenceEngine.getPersistence().put("user_page_1", new User("user_page_1", "user_page_1", page1Role));
		PersistenceEngine.getPersistence().put("user_page_2", new User("user_page_2", "user_page_2", page2Role));
		PersistenceEngine.getPersistence().put("user_page_3", new User("user_page_3", "user_page_3", page3Role));

	}
}
