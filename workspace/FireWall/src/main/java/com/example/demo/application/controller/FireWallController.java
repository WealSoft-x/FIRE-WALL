package com.example.demo.application.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.config.Hash;
import com.example.demo.application.config.Mail;
import com.example.demo.application.config.MailCheck;
import com.example.demo.application.config.TokenGenerate;
import com.example.demo.application.domain.model.LoginUser;
import com.example.demo.application.domain.model.NewPassword;
import com.example.demo.application.domain.model.TokenInfo;
import com.example.demo.application.domain.model.UpdateUser;
import com.example.demo.application.domain.model.User;
import com.example.demo.application.domain.model.UserRegisterRequestParam;
import com.example.demo.application.domain.model.UserResetPasswordParam;
import com.example.demo.application.domain.model.UserSearch;
import com.example.demo.application.domain.service.UserService;

@RestController
@RequestMapping("/api")
public class FireWallController {
	
	@Autowired
	private UserService service;
	
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void register(@RequestBody UserRegisterRequestParam param) {
		
		MailCheck mailCheck = new MailCheck();

		if (service.getCertifacatedUser(param.getManageUser().getMail()).size() == 0) {

			//画面に "エラーメッセージ:権限ユーザーがみつかりません"を表示する処理
			System.out.println( "エラーメッセージ:権限ユーザーがみつかりません");

		} else if (mailCheck.isValidMailAddress(param.getNewUser().getMail())){
			//画面に "メールアドレスが正しくありません"を表示する処理"
			System.out.println("メールアドレスが正しくありません");

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
			
			// 2段階認証の情報設定
			LocalDateTime ldt = LocalDateTime.now();
			
			TokenGenerate tokenGenerate = new TokenGenerate();
			user.setMail(service.getCertifacatedUser(loginUser.getMail()).get(0).getMail());
			user.setName(service.getCertifacatedUser(loginUser.getMail()).get(0).getName());
			user.setToken(tokenGenerate.generateToken());
			user.setToken_out_time(ldt);
			
			// メール通知処理
			Mail mail = new Mail();
			mail.sentMail(user);
			service.setTokenInfo(user);
			
			// 初回認証リセット
			user.setMail(loginUser.getMail());
			user.setInitial_certification_count(0);
			service.setInitialCertificationCount(user);
			
			return"2段階認証画面へ";
		} else {
			
			// 認証回数の設定
			user.setMail(loginUser.getMail());
			user.setInitial_certification_count(service.getCertifacatedUser(loginUser.getMail()).get(0).getInitial_certification_count() + 1);
			service.setInitialCertificationCount(user);
			
			// 画面に"パスワードが違います"
			return"ログイン画面へ：パスワードが違います";
		}
		
	}
	
	@PostMapping("/authentification")
	public String twoFactorAuthentification(@RequestBody TokenInfo token) {
		
		Duration calculatedMinutes = null;
		
		if(service.getToken(token).size() != 0) {
			// 現時刻とトークン払い出し時刻から制限時間を算出
			LocalDateTime ldt = LocalDateTime.now();
			LocalDateTime outTime = service.getToken(token).get(0).getToken_out_time();
			calculatedMinutes = Duration.between(ldt, outTime);
			
		}
		
		// 3分悔過した場合、トークンを失効する
		if(service.getToken(token).size() != 0 
				&& calculatedMinutes.toMinutesPart() < -3) {
			return "ログイン画面へ：トークン時間切れ";
		}
		
		if(service.getToken(token).size() == 0) {
			return "2段階認証画面へ: トークンが違います";
		}else {
			return "アプリケーションへ";
		}
		
	}
	
	@PostMapping("/sentresetpassword")
	public String sentResetPassword(@RequestBody UserResetPasswordParam param) {

		// アカウント有無確認
		if(service.getCertifacatedUser(param.getMail()).size() != 0 ){
			// 仮パスワード完了の連絡メール
			TokenGenerate tokenGenerate = new TokenGenerate();
			User user = new User();
			user.setMail(service.getCertifacatedUser(param.getMail()).get(0).getMail());
			user.setPassword(tokenGenerate.generateToken());
			Mail mail = new Mail();
			mail.sentMail(user);


			// 新しいパスワードを設定
			service.setAutoCreatePassword(user);			

			return "仮パスワードを送信しました";
		} else {
			return "アカウントが存在しません、メールアドレスを確認してください";
		}
		
	}
	
	@PostMapping("/resetpassword")
	public String resetPassword(@RequestBody NewPassword newPassword) {
		
		User user = new User();
		user.setMail(newPassword.getMail());
		user.setPassword(newPassword.getNewPassword());
		
		Hash hash = new Hash();
		if(service.getCertifacatedUser(user.getMail()).size()!= 0 
				&&!hash.checkpw(newPassword.getCreatePassword(), service.getCertifacatedUser(newPassword.getMail()).get(0).getPassword())) {
			return "仮パスワードが違います ：再入力してください";
		} else if (!newPassword.getNewPassword().equals(newPassword.getCheckPassword())){
			return "確認用パスワードが違います";
		} else {
			service.setAutoCreatePassword(user);
			return "パスワードの設定が完了しました：ログイン画面より再ログインしてください";
		}
		
	}
	
	@GetMapping("/search")
	public List<User> getUsers(@RequestParam(value = "mail", required = false) String mail,
			@RequestParam(value = "name", required = false) String name , 
			@RequestParam(value = "is_admin", required = false) Boolean is_admin){
		
		UserSearch userSearch = new UserSearch();
		userSearch.setMail(mail);
		userSearch.setName(name);
		userSearch.setIs_admin(is_admin);
		
		return service.getUsers(userSearch);
	}
	
	@DeleteMapping("/delete")
	public void deleteUser(@RequestParam(value="mail") String mail) {
		service.deleteUser(mail);
	}
	
	@PutMapping("/update")
	public String updateUser(@RequestBody UpdateUser updateUser) {
				
		if(service.getCertifacatedUser(updateUser.getComfirmationMail()).size() == 0){	
			return"ユーザーが存在しません：メールアドレス";
		}else if(updateUser.getPassword().equals("")){
			return"名前が規定の文字数を越えています（10文字まで）";
		} else {
			service.upateUser(updateUser);
			return "更新完了";
		}		
	}
		
		
}
