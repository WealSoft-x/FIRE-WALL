package com.example.demo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.domain.model.LoginUser;
import com.example.demo.application.domain.model.UserRegisterRequestParam;
import com.example.demo.application.domain.service.UserService;

@RestController
public class FireWallController {
	
	@Autowired
	private UserService service;
	
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@RequestBody UserRegisterRequestParam param) {
		
		
		if (service.getCertifacatedUser(param.getManageUser()).size() == 0) {
			
			//画面に "エラーメッセージ:権限ユーザーがみつかりません"を表示する処理
			System.out.println( "エラーメッセージ:権限ユーザーがみつかりません");
			
		} else if (service.getUserForDuplicatedError(param.getNewUser()).get(0).getMail().equals(param.getNewUser().getMail())){
			
			//画面に "SQL:登録メールのユーザーは既に存在しています"
			
			System.out.println("SQL:登録メールのユーザーは既に存在しています");
		} else {
			
			service.insertUser(param.getNewUser());
		
		} 
		
		
		

	}
	
	@GetMapping("/login")
	public String userLogin(LoginUser loginUser){
		
		
		service.getLoginUser(loginUser);
		
		return "ログイン成功";
	}

}
