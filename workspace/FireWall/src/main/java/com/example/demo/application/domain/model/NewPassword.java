package com.example.demo.application.domain.model;

import lombok.Data;

@Data
public class NewPassword {
	
	private String mail;
	
	private String createPassword;

	private String newPassword;
	
	private String checkPassword;
}
