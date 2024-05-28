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

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class MyController {

	@Autowired
	private IPurchaseDAO purchaseDao;
	
	@Autowired
	private IStockDAO stockDao;
	
	@GetMapping("/")
	public String index(Model model) {
		
		List<PurchaseDetail> popularList = purchaseDao.findGroupByMnoOrderByAmountDesc();
		if(popularList.size() > 0) {
			model.addAttribute("popularList", popularList);
		}
		
		List<StockDetail> recentList = stockDao.findOrderByOrderdatetimeDesc();
		if(recentList.size() > 0) {
			model.addAttribute("recentList", recentList);
		}

		return "/member/indexMember";
		
	}
}
