package com.green.shoppingMall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.shoppingMall.domain.Detail;
import com.green.shoppingMall.entity.Cart;
import com.green.shoppingMall.entity.Stock;
import com.green.shoppingMall.entity.User;
import com.green.shoppingMall.repository.CartRepository;
import com.green.shoppingMall.repository.MerchandiseRepository;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private MerchandiseRepository merchandiseRepository;
	
	@GetMapping("/list")
	public String list(HttpSession session, Model model) {
		
		User user = (User)session.getAttribute("member");
		List<Cart> cartList = cartRepository.findAllByUno(user.getUno());
		
		for(int i = 0; i < cartList.size(); i++) {
		}
		
		model.addAttribute("cartList", cartList);
		return "/member/cartList";
	}
	
	@PostMapping("/add")
	public @ResponseBody String add(@RequestBody Detail detail, HttpSession session, Model model) {
		
		System.out.println(detail);
		
		Cart cart = Cart.builder()
				.amount(detail.getAmount())
				.mno(merchandiseRepository.findByMno(detail.getMno()))
				.uno((User)session.getAttribute("member"))
				.build();
				
		cartRepository.save(cart);
		
		return "장바구니에 추가되었습니다";
	}
}
