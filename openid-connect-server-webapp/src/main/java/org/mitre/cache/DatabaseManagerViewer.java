package org.mitre.cache;

import org.hsqldb.util.DatabaseManagerSwing;

public class DatabaseManagerViewer {

	private String jdbcUrl;
	
	private String username;
	
	private String password;
	
	public void init(){
	   DatabaseManagerSwing.main(new String[] { "--url", jdbcUrl, "--user", username, "--password", password});
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
