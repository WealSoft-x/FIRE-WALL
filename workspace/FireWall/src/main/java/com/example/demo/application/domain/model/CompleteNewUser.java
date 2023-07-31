package com.example.demo.application.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CompleteNewUser {
	
	private String mail;
	private String password;
	private String name;
	private Boolean is_admin;
	private LocalDateTime created_at;
	
}
