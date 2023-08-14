package com.example.demo.application.domain.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.application.domain.model.CompleteNewUser;
import com.example.demo.application.domain.model.User;
import com.example.demo.application.domain.model.UserSearch;



@Mapper
public interface UserMapper {
	
	// 権限ユーザー取得
	List<User> getCertifacatedUser(String mail);
	
	// トークン認証
	List<User> getToken(String token);

	// 新規パスワード登録用
	List<User> getPassword(String Password);
	
	// 重複エラー用ユーザー検索・取得
	List<User> getUserForDuplicatedError(String mail);
	
	// ユーザー検索
	List<User> getUsers(UserSearch userSearch);
	
	//ユーザー登録
	void insertUser(CompleteNewUser newUser);
	
	// 初回認証カウント
	void setInitialCertificationCount(User user);
	
	// トークン設定
	void setTokenInfo(User user);
	
	// 仮スワード設定
	void setAutoCreatePassword(User user);
}
