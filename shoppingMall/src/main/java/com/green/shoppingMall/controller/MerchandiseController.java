package com.green.shoppingMall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.shoppingMall.dao.IStockDAO;
import com.green.shoppingMall.repository.MerchandiseRepository;
import com.green.shoppingMall.repository.StockRepository;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/merchandise")
public class MerchandiseController {

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private IStockDAO stockDao;
	
	@Autowired
	private MerchandiseRepository merchandiseRepository;
	
	@GetMapping("/list")
	public String list() {
		
		stockRepository.findAll();
		
		return "";
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam("mno") Long mno, Model model) {
		
		model.addAttribute("merchandiseDetail", merchandiseRepository.findByMno(mno));
		model.addAttribute("amount", stockDao.getAmount(mno));
		
		return "/member/merchandiseDetail";
	}
}
