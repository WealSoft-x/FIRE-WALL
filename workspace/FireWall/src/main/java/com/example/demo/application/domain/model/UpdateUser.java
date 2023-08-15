package com.example.demo.application.domain.model;

import lombok.Data;

@Data
public class UpdateUser {
	
	private String comfirmationMail;
	private String mail;
	private String name;
	private String password;
	private Boolean is_admin;

}
