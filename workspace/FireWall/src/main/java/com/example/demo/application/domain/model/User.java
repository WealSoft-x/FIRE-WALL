package com.example.demo.application.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
	
	private int id;
	private String mail;
	private String password;
	private String name;
	private Boolean is_admin;
	private int initial_certification_count;
	private String token;
	private LocalDateTime token_out_time;
	private LocalDateTime updated_at;
	private LocalDateTime created_at;
	

}
