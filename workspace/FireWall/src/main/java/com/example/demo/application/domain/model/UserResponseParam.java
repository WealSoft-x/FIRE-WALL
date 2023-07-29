package com.example.demo.application.domain.model;

import java.util.List;

import lombok.Data;

@Data
public class UserResponseParam {
	private List<User> user;
	private String remarks;
}
