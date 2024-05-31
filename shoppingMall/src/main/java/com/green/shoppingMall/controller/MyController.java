package com.green.shoppingMall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.shoppingMall.dao.IPurchaseDAO;
import com.green.shoppingMall.dao.IStockDAO;
import com.green.shoppingMall.domain.PurchaseDetail;
import com.green.shoppingMall.domain.StockDetail;
import com.green.shoppingMall.entity.Purchase;
import com.green.shoppingMall.entity.Stock;
import com.green.shoppingMall.entity.User;
import com.green.shoppingMall.repository.CartRepository;
import com.green.shoppingMall.repository.PurchaseRepository;
import com.green.shoppingMall.repository.StockRepository;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class MyController {

	@Autowired
	private IPurchaseDAO purchaseDao;
	
	@Autowired
	private IStockDAO stockDao;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		
		List<Purchase> popularList = purchaseRepository.findAllGroupByAmountAllOrderByAmountDesc();
		
		
		if(popularList.size() > 0) {
			model.addAttribute("popularList", popularList);
		}
		
		List<Stock> recentList = stockRepository.findOrderByOrderdatetimeDesc();
		
		if(recentList.size() > 0) {
			model.addAttribute("recentList", recentList);
		}
		if(stockRepository.findAll().size() > 0) {
		
		model.addAttribute("stockList", stockRepository.findAll());
		}

		return "/member/indexMember";
		
		
	}
}
