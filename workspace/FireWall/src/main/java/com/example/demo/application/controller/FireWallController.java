package com.example.demo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.config.Hash;
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
	
	@PostMapping("/login")
	public String userLogin(@RequestBody LoginUser loginUser){
		
		if(service.getLoginUser(loginUser).size() == 0) {
			return"ログイン画面へ：ユーザーが存在しません";
		} 
		
		if(service.getLoginUser(loginUser).size() != 0 
				&& !loginUser.getMail().equals(service.getLoginUser(loginUser).get(0).getMail())){
			//画面に"メールが違います"
			return"ログイン画面へ：メールが違います";

		}
		
		Hash hash = new Hash();
		System.out.println(service.getLoginUser(loginUser).get(0).getPassword());
		if(service.getLoginUser(loginUser).size() != 0 
				&&hash.checkpw(loginUser.getPassword(), service.getLoginUser(loginUser).get(0).getPassword())) {
			return"2段階認証画面へ";
		} else {
			//画面に"パスワードが違います"
			return"ログイン画面へ：パスワードが違います";
		}
		
		

		
		
	}

}
