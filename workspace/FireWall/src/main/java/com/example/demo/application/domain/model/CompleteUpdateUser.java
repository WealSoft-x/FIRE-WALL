package com.example.demo.application.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CompleteUpdateUser {
	
	private String  comfirmationMail;
	
	private String mail;
	
	private String name;
	
	private String password;
	
	private Boolean is_admin;
	
	private LocalDateTime updated_at;

}
