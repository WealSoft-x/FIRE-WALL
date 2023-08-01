package com.example.demo.application.config;

import java.util.HashMap;
import java.util.Map;

public class CommonMail {
	
	// メール送り先
	private String to = "";
	// メール送り元
	private String from = "";

	private String charest = "UTF-8";
	
	private String encoding = "base64";
	
	// for gamil
	private String host = "";
	
	private int port = 8443;
	
	private boolean starttls = false;
	
	private Map<String, String> headers = new HashMap<String, String>(){
	    private static final long serialVersionUID = 1L;
	    {
	      put("Content-Transfer-Encoding", encoding);
	    }
	  };
	  
	public void sendMail() {
		
	}
}
