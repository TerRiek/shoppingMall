package com.green.shoppingMall.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRespositoryTests {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void test() {
		
		System.out.println(userRepository.findByUsernameAndPassword("aaa", "aaa"));
	}
}
