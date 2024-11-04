package model;

import java.io.Serializable;

public class Login implements Serializable{
	private String userId;
	private String pass;
	private String msg;
	
	public Login() {
	}
	public Login(String userId, String pass) {
		this.userId = userId;
		this.pass = pass;
		this.msg = "";
	}
	public String getUserId() {
		return this.userId;
	}
	public String getPass() {
		return this.pass;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return this.msg;
	}
}
