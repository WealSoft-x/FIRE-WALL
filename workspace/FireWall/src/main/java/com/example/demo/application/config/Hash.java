package com.example.demo.application.config;

import org.mindrot.jbcrypt.BCrypt;


public class Hash {
	
	 private static String salt = "salt";
	 
	 
	public String hashPassword(String password) {
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		return hashedPassword;
	}
	
	public boolean checkpw(String loginPass, String dbPass){
		
		
		return BCrypt.checkpw(loginPass, dbPass);	
	}

}
