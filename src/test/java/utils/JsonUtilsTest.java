package utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.edu.app.dto.UserDTO;
import es.edu.app.enums.Role;
import es.edu.app.exception.SerializationDeserializationException;
import es.edu.app.utils.JsonUtils;

public class JsonUtilsTest {

	private List<Role> list;
	private UserDTO userDTO;
	private String userDTOjson = "{\"username\":\"a\",\"password\":\"b\",\"roles\":[\"ADMIN\"]}";
	private String userJsonResult = "{\"username\":\"a\",\"roles\":[\"ADMIN\"]}";

	@Before
	public void setUp() throws Exception {
		list = new ArrayList<Role>();
		list.add(Role.ADMIN);
		userDTO = new UserDTO("a", "b", list);
	}

	@Test
	public void objectToJson() {
		Assert.assertEquals(JsonUtils.objectToJson(userDTO), userJsonResult);
	}

	@Test
	public void jsonToObject() {
		UserDTO result = JsonUtils.jsonToObject(userDTOjson, UserDTO.class);
		Assert.assertEquals(result.getPassword(), "b");
		Assert.assertEquals(result.getUsername(), "a");
		Assert.assertEquals(result.getRoles().get(0), Role.ADMIN);
	}

	@Test(expected = SerializationDeserializationException.class)
	public void testSerializationDeserializationException() {
		JsonUtils.jsonToObject("hello", UserDTO.class);
	}
}
