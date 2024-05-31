package com.green.securityweb_1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean // 반환하는 클래스를 빈으로 등록한다
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		/* JPA 와 JSP를 동시에 사용할때 anyRequest().permitAll() 순서를 맨 아래로 내린다.
		 * http.authorizeHttpRequests(auth -> auth
		 * 			.requestMatchers("/member/**").authenticated()
		 * 			.requestMatchers("/admin/**").authenticated()
		 * 			.anyRequest().permitAll()
		 * 			);
		 * */
		
		http.authorizeHttpRequests((auth) -> auth // 권한을 부여하는 역할
				.requestMatchers("/", "/registForm", "/registProc").permitAll() // 모든 권한을 허용한다.(제한이 없다.)
				.requestMatchers("/members/**").hasAnyRole("ADMIN", "MEMBER")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated() // 나머지는 인증을 거쳐야 한다.
				);
		
		http.formLogin((auth) -> auth 
				.loginPage("/loginForm") // 로그인 폼 지정 - 쓰지 않으면 Spring Security가 제공하는 로그임 폼 사용 
				.loginProcessingUrl("/loginProc") // 로그인 폼 지정 후 폼 파라미터 보내는 경로지정 - 컨트롤러에 직접 만들지 않는다.(Spring Security가 알아서 처리함)
				.defaultSuccessUrl("/success")
				.permitAll()
				);
		
		http.csrf(AbstractHttpConfigurer::disable); // csrf 기능 끄기
		
		return http.build();
	}
	
}

