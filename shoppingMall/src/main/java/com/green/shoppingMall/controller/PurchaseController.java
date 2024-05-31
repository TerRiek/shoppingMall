package com.green.shoppingMall.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.shoppingMall.domain.Detail;
import com.green.shoppingMall.entity.Cart;
import com.green.shoppingMall.entity.Purchase;
import com.green.shoppingMall.entity.Stock;
import com.green.shoppingMall.entity.User;
import com.green.shoppingMall.repository.CartRepository;
import com.green.shoppingMall.repository.DeliveryRepository;
import com.green.shoppingMall.repository.MerchandiseRepository;
import com.green.shoppingMall.repository.PurchaseRepository;
import com.green.shoppingMall.repository.StockRepository;
import com.green.shoppingMall.repository.UserRepository;

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
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	
	private CartRepository cartRepository;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/buy")
	public @ResponseBody String purchase(@RequestBody Detail detail, HttpSession session) {
		
		User user = (User)session.getAttribute("member");
		Purchase purchase = Purchase.builder()
				.pamount(detail.getAmount())
				.regdatetime(LocalDateTime.now())
				.sno(stockRepository.findByMno(detail.getMno()))
				.dno(deliveryRepository.findByUno(user.getUno()))
				.build();
		Stock stockResult = stockRepository.findByMno(detail.getMno());
		
		Stock stock = Stock.builder()
				.sno(stockResult.getSno())
				.amount(stockResult.getAmount() - detail.getAmount())
				.orderdatetime(LocalDateTime.now())
				.mno(merchandiseRepository.findByMno(detail.getMno()))
				.build();
				
		stockRepository.save(stock);
		
		purchaseRepository.save(purchase);
		
		return "구매 완료 되었습니다";
	}
	
	@GetMapping("/list")
	public String list(HttpSession session, Model model) {
		User user = (User)session.getAttribute("member");
		
		List<Purchase> purchaseList = purchaseRepository.findByDnoOrderByRegdatetimeDesc(deliveryRepository.findByUno(user.getUno()));
		
		session.setAttribute("cartTotal", cartRepository.countByUno(user.getUno()));
		if(purchaseList.size() > 0) {
			
		model.addAttribute("purchaseList", purchaseList);
		}
		return "/member/purchaseList";
	}
	
	@PostMapping("/buyList")
	public @ResponseBody String buyList(@RequestBody List<Detail> detail, HttpSession session) {
		
		if(detail.isEmpty()) {
			return "";
		}
		
		User user = (User)session.getAttribute("member");
		
		detail.forEach(e ->{
			Purchase purchase = Purchase.builder()
					.pamount(e.getAmount())
					.sno(stockRepository.findByMno(e.getMno()))
					.dno(deliveryRepository.findByUno(user.getUno()))
					.regdatetime(LocalDateTime.now())
					.build();
			
			Stock stockResult = stockRepository.findByMno(e.getMno());
			Stock stock = Stock.builder()
					.sno(stockResult.getSno())
					.amount(stockResult.getAmount() - e.getAmount())
					.orderdatetime(LocalDateTime.now())
					.mno(merchandiseRepository.findByMno(e.getMno()))
					.build();
					
			stockRepository.save(stock);
			
			purchaseRepository.save(purchase);
			Cart cart = cartRepository.findByMnoAndUno(e.getMno(), user.getUno());
			cartRepository.delete(cart);
		});
		
		
		
		return "구매가 성공적으로 진행됬습니다.";
	}
}
