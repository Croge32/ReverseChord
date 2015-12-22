package com.reversechord;

public class User {

	private int userId;
	private String email;
	private String password;
	private int status;
	
	public User() {}
	
	public User(String e, String pass) {
		email = e;
		password = pass;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int id) {
		userId = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String e) {
		email = e;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String pass) {
		password = pass;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int s) {
		status = s;
	}
	
	public String toString() {
		return userId+" "+email+" "+password+" "+status;
	}	
}
