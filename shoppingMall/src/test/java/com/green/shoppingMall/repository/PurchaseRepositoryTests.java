package com.green.shoppingMall.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.green.shoppingMall.dao.IStockDAO;

@SpringBootTest
public class PurchaseRepositoryTests {

	@Autowired
	private IStockDAO purchaseDao;
	
	@Test
	public void test() {
		System.out.println(purchaseDao.findOrderByOrderdatetimeDesc());
	}
}
