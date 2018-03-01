package es.edu.app.session;

import java.time.OffsetDateTime;

public class Cookie {
	private String key;
	private String value;
	private OffsetDateTime expiration;

	public Cookie(String key, String value, OffsetDateTime expiration) {
		super();
		this.key = key;
		this.value = value;
		this.expiration = expiration;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public OffsetDateTime getExpiration() {
		return expiration;
	}

	public void setExpiration(OffsetDateTime expiration) {
		this.expiration = expiration;
	}

}
