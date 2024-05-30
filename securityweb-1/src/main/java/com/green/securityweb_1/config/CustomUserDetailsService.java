package com.green.securityweb_1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.green.securityweb_1.entity.Member;
import com.green.securityweb_1.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member member = memberRepository.findByUsername(username);
		
		if(member != null) {
			return new CustomUserDetails(member);
		}
		
		return null;
	}

}
