package com.green.shoppingMall.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDetail {

	private Long pno;
	private Long amount;
	private LocalDateTime regdatetime;
	private Long cno;
	private Long dno;
	private Long mno;
	
	private String code;
	private String name;
	private String type;
	private int buyprice;
	private int sellprice;
	private String company_name;
	private String img;
}
