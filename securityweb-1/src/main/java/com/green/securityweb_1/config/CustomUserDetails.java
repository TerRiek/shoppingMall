package com.green.securityweb_1.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.green.securityweb_1.entity.Member;

public class CustomUserDetails implements UserDetails{ // 시큐리티의 세션에 들어갈수 있는 객체가 정해져있다. 규칙에 따라야 한다.

	private Member member;
	
	public CustomUserDetails(Member member) {
		this.member = member;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { // GrantedAuthority 를 상속 받은 애들만 들어갈수 있다

		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {

			@Override
			public String getAuthority() {
				
				return member.getRole();
			}
			
		});
		
		return collection;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUsername();
	} 

	public String getName() {
		return member.getName();
	} 
	
	public String getRole() {
		return member.getRole();
	} 
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}
