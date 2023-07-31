package com.example.demo.application.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.config.Hash;
import com.example.demo.application.domain.mapper.UserMapper;
import com.example.demo.application.domain.model.CompleteNewUser;
import com.example.demo.application.domain.model.CreateUserTime;
import com.example.demo.application.domain.model.LoginUser;
import com.example.demo.application.domain.model.ManageUser;
import com.example.demo.application.domain.model.NewUser;
import com.example.demo.application.domain.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	//ハッシュ化クラス
	private Hash hash = new Hash();
	
	
	
	public List<User> getCertifacatedUser(ManageUser manageUser) {
		return userMapper.getCertifacatedUser(manageUser.getMail());
	}
	
	public void insertUser(NewUser newUser) {
		
		//パスワードのハッシュ化
		String hashedPassword = hash.hashPassword(newUser.getPassword());
		newUser.setPassword(hashedPassword);
		
		//作成日時の設定
		CreateUserTime createUserTime = new CreateUserTime();
		LocalDateTime ldt = LocalDateTime.now();
		createUserTime.setCreated_at(ldt);
		
		//データ詰め替え
		CompleteNewUser completeNewUser = new CompleteNewUser();
		completeNewUser.setMail(newUser.getMail());
		completeNewUser.setName(newUser.getName());
		completeNewUser.setPassword(newUser.getPassword());
		completeNewUser.setIs_admin(newUser.getIs_admin());
		completeNewUser.setCreated_at(createUserTime.getCreated_at());
		
		userMapper.insertUser(completeNewUser);		
	}
	
	public List<User> getUserForDuplicatedError(NewUser newUser){
		return userMapper.getUserForDuplicatedError(newUser.getMail());
	}
	
	public List<User> getLoginUser(LoginUser loginUser) {
		
		return userMapper.getLoginUser(loginUser);
	}

}
