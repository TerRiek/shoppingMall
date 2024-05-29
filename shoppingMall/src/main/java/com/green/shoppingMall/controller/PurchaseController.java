package com.green.shoppingMall.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.shoppingMall.domain.Detail;
import com.green.shoppingMall.entity.Purchase;
import com.green.shoppingMall.entity.User;
import com.green.shoppingMall.repository.DeliveryRepository;
import com.green.shoppingMall.repository.MerchandiseRepository;
import com.green.shoppingMall.repository.PurchaseRepository;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/purchase")
public class PurchaseController {

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private MerchandiseRepository merchandiseRepository;
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@PostMapping("/buy")
	public @ResponseBody String purchase(@RequestBody Detail detail, HttpSession session) {
		
		User user = (User)session.getAttribute("member");
		Purchase purchase = Purchase.builder()
				.amount(detail.getAmount())
				.regdatetime(LocalDateTime.now())
				.mno(merchandiseRepository.findByMno(detail.getMno()))
				.dno(deliveryRepository.findByUno(user.getUno()))
				.build();
		
		System.out.println("purchase : " + purchase);
		purchaseRepository.save(purchase);
		
		return "구매 완료 되었습니다";
	}
}
