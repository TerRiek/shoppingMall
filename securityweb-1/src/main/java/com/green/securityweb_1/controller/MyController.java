package com.green.securityweb_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.securityweb_1.dto.MemberDto;
import com.green.securityweb_1.entity.Member;
import com.green.securityweb_1.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MyController {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping("/")
	public String root() {
		
		log.info("root----------------------");
		return "index";
	}
	
	@GetMapping("/registForm")
	public void registForm() {
		log.info("registForm........");
	}
	
	@PostMapping("/registProc")
	public @ResponseBody String registProc(MemberDto memberDto) {
		log.info("registProc.............." + memberDto);
		
		Member member = new Member();
		member.setUsername(memberDto.getUsername());
		
		String newPw = bCryptPasswordEncoder.encode(memberDto.getPassword());
		member.setPassword(newPw); // 암호화된 패스워드
		
		member.setName(memberDto.getName());
		member.setRole("ROLE_MEMBER");
		
		memberRepository.save(member);
		
		return "회원가입 완료됨.";
	}
	
	@GetMapping("/loginForm")
	public void login() {
		
	}
}
