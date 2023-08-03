package com.example.demo.application.config;

import org.apache.commons.validator.routines.EmailValidator;

public class MailCheck {
	
	public  boolean isValidMailAddress(String mail) {
	    boolean result = false;
	    if (mail != null) {
	        if(EmailValidator.getInstance().isValid(mail)) {
	            result = true;
	        }
	    }
	    return result;
	}

}
