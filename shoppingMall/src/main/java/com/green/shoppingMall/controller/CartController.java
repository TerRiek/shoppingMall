package com.green.shoppingMall.controller;

import java.time.LocalDateTime;
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
import com.green.shoppingMall.entity.Purchase;
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
		
		if(user == null) {
			return "redirect:/myerror";
		}
		session.setAttribute("cartTotal", cartRepository.countByUno(user.getUno()));
		
		List<Cart> cartList = cartRepository.findAllByUno(user.getUno());
		
		if(cartList.size() > 0) {
			model.addAttribute("cartList", cartList);
		}
		
		return "/member/cartList";
	}
	
	@PostMapping("/add")
	public @ResponseBody String add(@RequestBody Detail detail, HttpSession session, Model model) {
		
		
		User user = (User)session.getAttribute("member");
		
		Cart cart = cartRepository.findByMnoAndUno(detail.getMno(), user.getUno());
		
		System.out.println(cart);
		
		if(cart != null) {
			
			cart.setCno(cart.getCno());
			cart.setAmount(detail.getAmount());
			cart.setUno(user);
			cart.setMno(merchandiseRepository.findByMno(detail.getMno()));
			
			cartRepository.save(cart);
		}else {
			
			Cart cart2 = new Cart();
			
			cart2.setAmount(detail.getAmount());
			cart2.setUno(user);
			cart2.setMno(merchandiseRepository.findByMno(detail.getMno()));
			
			cartRepository.save(cart2);
		}
		
		return "장바구니에 추가되었습니다";
	}
	
	@PostMapping("/delete")
	public @ResponseBody String buyList(@RequestBody List<Detail> detail, HttpSession session) {
		
		if(detail.isEmpty()) {
			return "";
		}
		
		User user = (User)session.getAttribute("member");
		
		detail.forEach(e ->{

			Cart cart = cartRepository.findByMnoAndUno(e.getMno(), user.getUno());
			cartRepository.delete(cart);
		
		});
		
		return "장바구니에서 제거했습니다.";
	}
}
