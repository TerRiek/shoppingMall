package com.green.shoppingMall.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.green.shoppingMall.dao.IPurchaseDAO;
import com.green.shoppingMall.dao.IStockDAO;
import com.green.shoppingMall.domain.PurchaseDetail;
import com.green.shoppingMall.domain.StockDetail;
import com.green.shoppingMall.entity.Delivery;
import com.green.shoppingMall.entity.Purchase;
import com.green.shoppingMall.entity.Stock;
import com.green.shoppingMall.entity.User;
import com.green.shoppingMall.repository.CartRepository;
import com.green.shoppingMall.repository.DeliveryRepository;
import com.green.shoppingMall.repository.PurchaseRepository;
import com.green.shoppingMall.repository.StockRepository;
import com.green.shoppingMall.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IPurchaseDAO purchaseDao;
	
	@Autowired
	private IStockDAO stockDao;
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private StockRepository stockRepository;

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/member/loginForm";
	}
	
	@GetMapping("/registForm")
	public String registForm() {
		return "/member/registForm";
	}

	@PostMapping("/regist")
	public String regist(@ModelAttribute User user, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		User userBuilder = User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.email(user.getEmail())
				.internationalNumber(user.getInternationalNumber())
				.telephone(user.getTelephone())
				.role("ROLE_MEMBER")
				.regdate(LocalDate.now())
				.build();
		
		
		userRepository.save(userBuilder);
		
		User userResult = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		Delivery delivery = Delivery.builder()
				.uno(userResult)
				.build();
		
		deliveryRepository.save(delivery);
		
		out.print("<script>alert('회원가입이 성공적으로 완료되었습니다');</script>");
		
		return "/member/loginForm";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute User user, HttpSession session, HttpServletResponse response, Model model) throws IOException {
		User userResult = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		
		if(userResult == null) {

			model.addAttribute("msg", "로그인 실패했습니다.");
			model.addAttribute("url", "/user/loginForm");
			return "/alert/alert";
		} else if(userResult.getRole().equals("ROLE_MEMBER")) {
			
			session.setAttribute("member", userResult);
			
			User user2 = (User)session.getAttribute("member");
			session.setAttribute("cartTotal", cartRepository.countByUno(user2.getUno()));
			
			model.addAttribute("msg", "개인회원 로그인 성공했습니다.");
			model.addAttribute("url", "/user/indexMember");
			return "/alert/alert";
		}else {

			session.setAttribute("admin", userResult);
			model.addAttribute("msg", "관리자 로그인 성공했습니다.");
			model.addAttribute("url", "/admin/indexAdmin");

			return "/alert/alert";
		}
			
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/user/indexMember";
	}
	
	@GetMapping("/indexMember")
	public String indexMember(Model model, HttpSession session) {
		
List<Purchase> popularList = purchaseRepository.findAllGroupByAmountAllOrderByAmountDesc();
		
		if(popularList.size() > 0) {
			model.addAttribute("popularList", popularList);
			System.out.println(popularList);
		}
		
		List<Stock> recentList = stockRepository.findOrderByOrderdatetimeDesc();
		System.out.println(recentList);
		
		if(recentList.size() > 0) {
			model.addAttribute("recentList", recentList);
		}
		if(stockRepository.findAll().size() > 0) {
		
			
			
		model.addAttribute("stockList", stockRepository.findAll());
		}

		return "/member/indexMember";
	}
	
	@GetMapping("/detailMember")
	public String detailMember(HttpSession session, Model model) {
		User user = (User)session.getAttribute("member");
		if(user == null) {
			return "redirect:/myerror";
		}
		model.addAttribute("member", user);
		
		session.setAttribute("cartTotal", cartRepository.countByUno(user.getUno()));
		
		return "/member/userDetail";
	}
	
	@GetMapping("/detailAdmin")
	public String detailAdmin(HttpSession session, Model model) {
		User user = (User)session.getAttribute("admin");
		if(user == null) {
			return "redirect:/myerror";
		}
		model.addAttribute("admin", user);
		
		return "/member/userDetail";
	}
	
	@PostMapping("/updateMember")
	public String updateMember(User user, Model model, HttpSession session) {
		
		User userResult = (User)session.getAttribute("member");
		Long uno = userResult.getUno();
		userRepository.saveByUno(uno, user.getEmail(), user.getInternationalNumber(), user.getPassword(), user.getTelephone(), user.getUsername());
		
		model.addAttribute("msg", "정보 수정이 완료되었습니다");
		model.addAttribute("url", "/user/detailMember");
		return "/alert/alert";
	}
	
	@PostMapping("/updateAdmin")
	public String updateAdmin(User user, Model model, HttpSession session) {
		
		User userResult = (User)session.getAttribute("admin");
		Long uno = userResult.getUno();
		userRepository.saveByUno(uno, user.getEmail(), user.getInternationalNumber(), user.getPassword(), user.getTelephone(), user.getUsername());
		
		model.addAttribute("msg", "정보 수정이 완료되었습니다");
		model.addAttribute("url", "/user/detailAdmin");
		return "/alert/alert";
	}
	
}

