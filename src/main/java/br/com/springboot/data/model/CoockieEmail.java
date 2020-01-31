package br.com.springboot.data.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class CoockieEmail {

	@PrimaryKey
	private String cookie_bid;

	private String email;

	public String getCookie_bid() {
		return cookie_bid;
	}

	public void setCookie_bid(String cookie_bid) {
		this.cookie_bid = cookie_bid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "cookie_bid=" + cookie_bid + "|email=" + email;
	}

}
