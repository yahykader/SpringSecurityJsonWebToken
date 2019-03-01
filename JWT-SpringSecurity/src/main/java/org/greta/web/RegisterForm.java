package org.greta.web;

public class RegisterForm {
	
	private String userName;
	private String password;
	private String repassword;
	
	public RegisterForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegisterForm(String userName, String password, String repassword) {
		super();
		this.userName = userName;
		this.password = password;
		this.repassword = repassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
	
	
	

}
