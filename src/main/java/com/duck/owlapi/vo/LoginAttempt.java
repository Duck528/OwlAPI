package com.duck.owlapi.vo;

public class LoginAttempt {
	private int id;
	private String userEmail;
	private String password;
	private String ipNumber;
	private String attemptDate;
	private boolean isSuccess;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIpNumber() {
		return ipNumber;
	}
	public void setIpNumber(String ipNumber) {
		this.ipNumber = ipNumber;
	}
	public String getAttemptDate() {
		return attemptDate;
	}
	public void setAttemptDate(String attemptDate) {
		this.attemptDate = attemptDate;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}
