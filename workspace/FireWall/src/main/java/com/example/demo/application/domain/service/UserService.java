package com.example.demo.application.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.application.config.Hash;
import com.example.demo.application.domain.mapper.UserMapper;
import com.example.demo.application.domain.model.CompleteNewUser;
import com.example.demo.application.domain.model.CompleteUpdateUser;
import com.example.demo.application.domain.model.CreateUserTime;
import com.example.demo.application.domain.model.NewUser;
import com.example.demo.application.domain.model.TokenInfo;
import com.example.demo.application.domain.model.UpdateUser;
import com.example.demo.application.domain.model.User;
import com.example.demo.application.domain.model.UserSearch;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	//ハッシュ化クラス
	private Hash hash = new Hash();
	
	
	
	public List<User> getCertifacatedUser(String mail) {
		return userMapper.getCertifacatedUser(mail);
	}
	
	public List<User> getUsers(UserSearch userSearch){
		return userMapper.getUsers(userSearch);
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
	
	public void setInitialCertificationCount(User user) {
		userMapper.setInitialCertificationCount(user);
		}
	
	public void setTokenInfo(User user) {
		userMapper.setTokenInfo(user);
	}
	
	public List<User> getToken(TokenInfo token){
		return userMapper.getToken(token.getToken());
	}
	public void setAutoCreatePassword(User user) {
		//パスワードのハッシュ化
		String hashedPassword = hash.hashPassword(user.getPassword());
		user.setPassword(hashedPassword);
		userMapper.setAutoCreatePassword(user);
	}
	public void deleteUser(String mail) {
		userMapper.deleteUser(mail);
	}
	public void upateUser(UpdateUser updateUser) {
		
		//パスワードのハッシュ化
		String hashedPassword = hash.hashPassword(updateUser.getPassword());
		// 更新日時
		LocalDateTime ldt = LocalDateTime.now();
		CompleteUpdateUser user = new CompleteUpdateUser();
		user.setMail(updateUser.getMail());
		user.setName(updateUser.getName());
		user.setPassword(hashedPassword);
		user.setUpdated_at(ldt);
		user.setComfirmationMail(updateUser.getComfirmationMail());
		
		
		userMapper.updateUser(user);
	}
}
