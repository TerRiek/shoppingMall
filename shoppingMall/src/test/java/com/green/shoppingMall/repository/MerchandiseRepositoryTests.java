package com.green.shoppingMall.repository;


import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MerchandiseRepositoryTests {

	@Autowired
	private MerchandiseRepository merchandiseRepository;
	
	@Test
	public void test() {
		
		merchandiseRepository.deleteByMno(1L);
		
	}
}
