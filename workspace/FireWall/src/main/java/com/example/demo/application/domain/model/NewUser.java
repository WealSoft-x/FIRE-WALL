package com.example.demo.application.domain.model;



import lombok.Data;

@Data
public class NewUser {
	
	private String mail;
	private String password;
	private String name;
	private Boolean is_admin;
	
	

}
