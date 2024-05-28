package com.green.shoppingMall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.shoppingMall.dao.IStockDAO;
import com.green.shoppingMall.domain.StockDetail;
import com.green.shoppingMall.entity.Merchandise;
import com.green.shoppingMall.entity.Stock;
import com.green.shoppingMall.repository.CartRepository;
import com.green.shoppingMall.repository.MerchandiseRepository;
import com.green.shoppingMall.repository.PurchaseRepository;
import com.green.shoppingMall.repository.ReviewRepository;
import com.green.shoppingMall.repository.StockRepository;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private IStockDAO stockDao;
	
	@Autowired
	private MerchandiseRepository merchandiseRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private StockRepository stockRepository;
	
	@GetMapping("/indexAdmin")
	public void indexAdmin(Model model) {
		
		List<Stock> list = stockRepository.findAll();
		if(list.size() > 0) {
			model.addAttribute("list", list);
		}

	}
	
	@GetMapping("/registMerchandiseForm")
	public void registMerchandiseForm() {
		
	}
	
	@PostMapping("/registMerchandise")
	public String registMerchandise(Merchandise merchandise) {
		
		merchandiseRepository.save(merchandise);
		
		return "redirect:/admin/index";
	}
	
	@GetMapping("/merchandiseDetail")
	public void merchandiseDetail(@RequestParam("mno") Long mno, Model model) {
		
		model.addAttribute("merchandiseDetail", merchandiseRepository.findByMno(mno));
	}
	
	@GetMapping("/updateForm")
	public void updateForm(@RequestParam("mno") Long mno, Model model) {
		
		model.addAttribute("merchandiseDetail", merchandiseRepository.findByMno(mno));
	}
	
	@PostMapping("/update")
	public String update(Merchandise merchandise) {
		
		merchandiseRepository.save(merchandise);
		
		return "redirect:/admin/updateForm?mno=" + merchandise.getMno();
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("mno") Long mno) {
		
		cartRepository.deleteByMno(mno);
		purchaseRepository.deleteByMno(mno);
		reviewRepository.deleteByMno(mno);
		stockRepository.deleteByMno(mno);
		merchandiseRepository.deleteByMno(mno);
		
		return "redirect:/admin/indexAdmin";
	}
	
	@GetMapping("/getStockList")
	public String getStockList(Model model) {
		
		model.addAttribute("stockList", stockRepository.findAllOrderBySnoAsc());
		
		return "/admin/stockList";
	}
	
	@GetMapping("/orderStockForm")
	public String orderStockForm(Model model, @RequestParam("mno") Long mno) {
		
		
		model.addAttribute("stockDetail", stockRepository.findByMno(mno));
		
		return "/admin/stockDetail";
	}
	
	
}
