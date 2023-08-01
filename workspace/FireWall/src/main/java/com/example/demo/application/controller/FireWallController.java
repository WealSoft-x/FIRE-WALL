package com.example.demo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.config.Hash;
import com.example.demo.application.domain.model.LoginUser;
import com.example.demo.application.domain.model.User;
import com.example.demo.application.domain.model.UserRegisterRequestParam;
import com.example.demo.application.domain.service.UserService;

@RestController
public class FireWallController {
	
	@Autowired
	private UserService service;
	
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@RequestBody UserRegisterRequestParam param) {
		
		
		if (service.getCertifacatedUser(param.getManageUser().getMail()).size() == 0) {
			
			//画面に "エラーメッセージ:権限ユーザーがみつかりません"を表示する処理
			System.out.println( "エラーメッセージ:権限ユーザーがみつかりません");
			
		} else if (service.getUserForDuplicatedError(param.getNewUser()).size() != 0 
				&&service.getUserForDuplicatedError(param.getNewUser()).get(0).getMail().equals(param.getNewUser().getMail())){
			
			//画面に "SQL:登録メールのユーザーは既に存在しています"
			System.out.println("SQL:登録メールのユーザーは既に存在しています");
			
		} else {
			
			service.insertUser(param.getNewUser());
		
		} 

	}
	
	@PostMapping("/login")
	public String userLogin(@RequestBody LoginUser loginUser){
		
		User user = new User();
		
		if(service.getCertifacatedUser(loginUser.getMail()).size() == 0) {
			
			return"ログイン画面へ：ユーザーが存在しません(メールが違います)";
		} 
		
		if(service.getCertifacatedUser(loginUser.getMail()).get(0).getInitial_certification_count() >= 5) {
			return "あなたのアカウントはロックされています";
		}
		
		
		Hash hash = new Hash();
		if(service.getCertifacatedUser(loginUser.getMail()).size() != 0 
				&&hash.checkpw(loginUser.getPassword(), service.getCertifacatedUser(loginUser.getMail()).get(0).getPassword())) {
			return"2段階認証画面へ";
		} else {
			
			user.setMail(loginUser.getMail());
			user.setInitial_certification_count(service.getCertifacatedUser(loginUser.getMail()).get(0).getInitial_certification_count() + 1);
			service.setInitialCertificationCount(user);
			
			//画面に"パスワードが違います"
			return"ログイン画面へ：パスワードが違います";
		}
		
	}
	
	@PostMapping("/authentification")
	public void twoFactorAuthentification() {
		
	}
}
