package com.example.demo.application.config;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class TokenGenerate {

	public String generateToken() {  
		String token = null;  
		SecureRandom secureRandom;  
		try {
		
		// 乱数生成
		secureRandom = SecureRandom.getInstance("SHA1PRNG");  
		
		secureRandom.setSeed(secureRandom.generateSeed(128));  
		
		// 負の数の場合×-1を行ない生の数にする
		int preToken = secureRandom.nextInt();
		
		if(Math.signum(preToken) == -1.0) {
			token = Integer.toString(preToken * -1);
		} else {
			token = Integer.toString(preToken);
		}  
		
		
		} catch (NoSuchAlgorithmException e) {  
		// TODO Auto-generated catch block  
		e.printStackTrace();  
		}  
		
		return token;  
		}  
}
