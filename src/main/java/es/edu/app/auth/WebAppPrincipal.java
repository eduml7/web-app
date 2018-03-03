package es.edu.app.auth;

import java.util.List;

import com.sun.net.httpserver.HttpPrincipal;

import es.edu.app.enums.Role;

public class WebAppPrincipal extends HttpPrincipal {

	  private List<Role> roles; 
	  
	  public WebAppPrincipal(String username, String realm) { 
	    super(username, realm); 
	  } 
	 
	  public WebAppPrincipal(String username, String realm, List<Role> roles) { 
	    super(username, realm); 
	    this.roles = roles; 
	  } 
	 
	  public List<Role> getRoles() { 
	    return roles; 
	  } 
	 
	  public void setRoles(List<Role> roles) { 
	    this.roles = roles; 
	  } 

}
