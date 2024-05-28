package com.green.shoppingMall.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.green.shoppingMall.entity.User;
import com.green.shoppingMall.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/member/loginForm";
	}
	
	@GetMapping("/registForm")
	public String registForm() {
		return "/member/registForm";
	}

	@PostMapping("/regist")
	public String regist(@ModelAttribute User user, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		User userBuilder = User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.email(user.getEmail())
				.internationalNumber(user.getInternationalNumber())
				.telephone(user.getTelephone())
				.role("ROLE_MEMBER")
				.regdate(LocalDate.now())
				.build();
		
		userRepository.save(userBuilder);
		
		out.print("<script>alert('회원가입이 성공적으로 완료되었습니다');</script/>");
		
		return "/member/loginForm";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute User user, HttpSession session) {
		
		User userResult = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		
		if(userResult.getRole().equals("ROLE_MEMBER")) {
			
			session.setAttribute("member", userResult);
			
			return "/member/indexMember";
		}else {

			session.setAttribute("admin", userResult);
			
			return "redirect:/admin/indexAdmin";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "/member/indexMember";
	}
}

