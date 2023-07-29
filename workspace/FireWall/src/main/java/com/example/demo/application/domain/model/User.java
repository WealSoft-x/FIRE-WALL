package com.example.demo.application.domain.model;

import java.sql.Timestamp;

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
	private Timestamp token_out_time;
	private Timestamp updated_time;
	private Timestamp created_time;
	

}
