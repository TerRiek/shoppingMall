package com.green.shoppingMall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.green.shoppingMall.entity.Delivery;
import com.green.shoppingMall.entity.User;
import com.green.shoppingMall.repository.CartRepository;
import com.green.shoppingMall.repository.DeliveryRepository;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/delivery")
public class DeliveryController {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@GetMapping("/manage")
	public String manage(HttpSession session, Model model) {
		

		User user = (User)session.getAttribute("member");
		if(user == null) {
			return "redirect:/myerror";
		}
	
		Delivery deliveryDetail = deliveryRepository.findByUno(user.getUno());
		if(deliveryDetail.getAddress1() == null) {
			
		}else {
			model.addAttribute("deliveryDetail", deliveryDetail);
		}
		
		session.setAttribute("cartTotal", cartRepository.countByUno(user.getUno()));
		
		return "/member/deliveryDetail";
	}
	
	@PostMapping("/save")
	public String save(Delivery delivery, HttpSession session, Model model) {
		
		User user = (User)session.getAttribute("member");
		
		Delivery deliveryDetail = deliveryRepository.findByUno(user.getUno());
		
		deliveryDetail.setDno(deliveryDetail.getDno());
		deliveryDetail.setAddress1(delivery.getAddress1());
		deliveryDetail.setAddress2(delivery.getAddress2());
		deliveryDetail.setName(delivery.getName());
		deliveryDetail.setTelephone(delivery.getTelephone());
		deliveryDetail.setUno(user);
		deliveryRepository.save(deliveryDetail);
		
		model.addAttribute("msg", "배송지가 성공적으로 저장되었습니다");
		model.addAttribute("url", "/user/indexMember");
		
		return "/alert/alert";
	}
}
