package com.example.demo.application.domain.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.application.domain.model.CompleteNewUser;
import com.example.demo.application.domain.model.User;



@Mapper
public interface UserMapper {
	
	// 権限ユーザー取得
	List<User> getCertifacatedUser(String mail);
	
	//ユーザー登録
	void insertUser(CompleteNewUser newUser);
	
	// 重複エラー用ユーザー検索・取得
	List<User> getUserForDuplicatedError(String mail);
	
	// 初回認証カウント
	void setInitialCertificationCount(User user);
	
	

}
