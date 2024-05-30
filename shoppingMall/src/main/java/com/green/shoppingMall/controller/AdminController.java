package com.green.shoppingMall.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.green.shoppingMall.dao.IStockDAO;
import com.green.shoppingMall.entity.Merchandise;
import com.green.shoppingMall.entity.Purchase;
import com.green.shoppingMall.entity.Stock;
import com.green.shoppingMall.entity.User;
import com.green.shoppingMall.repository.CartRepository;
import com.green.shoppingMall.repository.MerchandiseRepository;
import com.green.shoppingMall.repository.PurchaseRepository;
import com.green.shoppingMall.repository.ReviewRepository;
import com.green.shoppingMall.repository.StockRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
	public String indexAdmin(Model model, HttpSession session) {
		
		User user = (User)session.getAttribute("admin");
		if(user == null) {
			return "redirect:/myerror";
		}
		
		List<Merchandise> list = merchandiseRepository.findAll();
		if(list.size() > 0) {
			model.addAttribute("list", list);
		}
		
		return "/admin/indexAdmin";
	}
	
	@GetMapping("/registMerchandiseForm")
	public void registMerchandiseForm() {
		
		
		
	}
	
	@PostMapping("/registMerchandise")
	public String registMerchandise(Merchandise merchandise, @RequestParam("file")MultipartFile file, HttpSession session, Model model) {
		
		
		String fileRealName = file.getOriginalFilename(); //파일명을 얻어낼 수 있는 메서드!
		long size = file.getSize(); //파일 사이즈
		if(!fileRealName.isEmpty()) {
			
		System.out.println("파일명 : "  + fileRealName);
		System.out.println("용량크기(byte) : " + size);
		//서버에 저장할 파일이름 fileextension으로 .jsp이런식의  확장자 명을 구함
		String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());
		String uploadFolder = "C:\\dev_JeongYuChan\\ws\\shoppingMall\\shoppingMall\\src\\main\\resources\\static\\upload";
		
		/*
		  파일 업로드시 파일명이 동일한 파일이 이미 존재할 수도 있고 사용자가 
		  업로드 하는 파일명이 언어 이외의 언어로 되어있을 수 있습니다. 
		  타인어를 지원하지 않는 환경에서는 정산 동작이 되지 않습니다.(리눅스가 대표적인 예시)
		  고유한 랜던 문자를 통해 db와 서버에 저장할 파일명을 새롭게 만들어 준다.
		 */
		
		
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		String[] uuids = uuid.toString().split("-");
		
		String uniqueName = uuids[0];
		System.out.println("생성된 고유문자열" + uniqueName);
		System.out.println("확장자명" + fileExtension);
		
		merchandise.setImgPath("/upload/" + uniqueName + fileExtension);
		
		// File saveFile = new File(uploadFolder+"\\"+fileRealName); uuid 적용 전
		
		File saveFile = new File(uploadFolder+"\\"+uniqueName + fileExtension);  // 적용 후
		try {
			file.transferTo(saveFile); // 실제 파일 저장메서드(filewriter 작업을 손쉽게 한방에 처리해준다.)
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		merchandiseRepository.save(merchandise);
		
		model.addAttribute("msg", "상품이 성공적으로 등록되었습니다");
		model.addAttribute("url", "/admin/indexAdmin");
		return "/alert/alert";
		}else {
			model.addAttribute("msg", "등록 실패했습니다");
			model.addAttribute("url", "/admin/indexAdmin");
			return "/alert/alert";
		}
		
	}
	
	@GetMapping("/merchandiseDetail")
	public String merchandiseDetail(@RequestParam("mno") Long mno, Model model, HttpSession session) {
		
		User user = (User)session.getAttribute("admin");
		if(user == null) {
			return "redirect:/myerror";
		}
		
		System.out.println(merchandiseRepository.findByMno(mno).getImgPath());
		
		model.addAttribute("merchandiseDetail", merchandiseRepository.findByMno(mno));
	
		return "/admin/merchandiseDetail";
	}
	
	@GetMapping("/updateForm")
	public String updateForm(@RequestParam("mno") Long mno, Model model, HttpSession session) {
		
		User user = (User)session.getAttribute("admin");
		if(user == null) {
			return "redirect:/myerror";
		}
		
		model.addAttribute("merchandiseDetail", merchandiseRepository.findByMno(mno));
		return "/admin/updateForm";
	}
	
	@PostMapping("/update")
	public String update(Merchandise merchandise, @RequestParam("file") MultipartFile file, Model model) {
		
		
		String fileRealName = file.getOriginalFilename(); //파일명을 얻어낼 수 있는 메서드!
		
		if(!fileRealName.isEmpty()) {
			
		long size = file.getSize(); //파일 사이즈
		
		System.out.println("파일명 : "  + fileRealName);
		System.out.println("용량크기(byte) : " + size);
		//서버에 저장할 파일이름 fileextension으로 .jsp이런식의  확장자 명을 구함
		String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());
		String uploadFolder = "C:\\dev_JeongYuChan\\ws\\shoppingMall\\shoppingMall\\src\\main\\resources\\static\\upload";
		/*
		  파일 업로드시 파일명이 동일한 파일이 이미 존재할 수도 있고 사용자가 
		  업로드 하는 파일명이 언어 이외의 언어로 되어있을 수 있습니다. 
		  타인어를 지원하지 않는 환경에서는 정산 동작이 되지 않습니다.(리눅스가 대표적인 예시)
		  고유한 랜던 문자를 통해 db와 서버에 저장할 파일명을 새롭게 만들어 준다.
		 */
		
		
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		String[] uuids = uuid.toString().split("-");
		
		String uniqueName = uuids[0];
		System.out.println("생성된 고유문자열" + uniqueName);
		System.out.println("확장자명" + fileExtension);
		
		merchandise.setImgPath("/upload/" + uniqueName + fileExtension);
		
		// File saveFile = new File(uploadFolder+"\\"+fileRealName); uuid 적용 전
		
		File saveFile = new File(uploadFolder+"\\"+uniqueName + fileExtension);  // 적용 후
		try {
			file.transferTo(saveFile); // 실제 파일 저장메서드(filewriter 작업을 손쉽게 한방에 처리해준다.)
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		merchandise.setImgPath("/upload/" + uniqueName + fileExtension);
		
		merchandiseRepository.save(merchandise);
		}else {
			
			model.addAttribute("msg", "입력되지 않은 값들이 존재합니다");
			model.addAttribute("url", "/admin/updateForm?mno=" + merchandise.getMno());
			return "/alert/alert";
		}
		
		return "redirect:/admin/updateForm?mno=" + merchandise.getMno();
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("mno") Long mno, HttpSession session) {
		
		User user = (User)session.getAttribute("admin");
		if(user == null) {
			return "redirect:/myerror";
		}
		
		cartRepository.deleteByMno(mno);
		purchaseRepository.deleteByMno(mno);
		reviewRepository.deleteByMno(mno);
		stockRepository.deleteByMno(mno);
		merchandiseRepository.deleteByMno(mno);
		
		return "redirect:/admin/indexAdmin";
	}
	
	@GetMapping("/getStockList")
	public String getStockList(Model model, HttpSession session) {
		
		User user = (User)session.getAttribute("admin");
		if(user == null) {
			return "redirect:/myerror";
		}
		
		List<Stock> stockList = stockRepository.findAllOrderByAmountAsc();
		List<Purchase> purchaseList = new ArrayList<>();
		
		for(int i = 0; i < stockList.size(); i ++) {
			purchaseList.add(purchaseRepository.findByMno(stockList.get(i).getMno().getMno()));
		}
		
		model.addAttribute("purchaseList", purchaseList);
		model.addAttribute("stockList", stockList);
			
		return "/admin/stockList";
	}
	
	@GetMapping("/orderStockForm")
	public String orderStockForm(Model model, @RequestParam("mno") Long mno, HttpSession session) {
		
		User user = (User)session.getAttribute("admin");
		if(user == null) {
			return "redirect:/myerror";
		}
		
		if(stockRepository.findByMno(mno) != null) {
			model.addAttribute("amount", stockRepository.findByMno(mno).getAmount());
		}
		
		model.addAttribute("merchandiseDetail", merchandiseRepository.findByMno(mno));
		
		return "/admin/stockDetail";
	}
	
	@PostMapping("/orderStock")
	public String orderStock(@RequestParam("mno") Long mno, @RequestParam(value = "amount", required = false) Long amount, HttpSession session, Model model) throws IOException {
		
		Stock stock = stockRepository.findByMnos(mno);
		
		if(stock != null) {
			
			stock.setOrderdatetime(LocalDateTime.now());
			stock.setAmount(stock.getAmount() + amount);
			stock.setMno(merchandiseRepository.findByMno(mno));
			stock.setSno(stock.getSno());
			
			stockRepository.save(stock);
			
		}else {
			
			Stock stock2 = new Stock();
			stock2.setOrderdatetime(LocalDateTime.now());
			stock2.setAmount(amount);
			stock2.setMno(merchandiseRepository.findByMno(mno));
			
			stockRepository.save(stock2);
		}
		
		model.addAttribute("msg", "주문이 완료되었습니다");
		model.addAttribute("url", "/admin/getStockList");
		
		return "/alert/alert";
	}
	
	
}
