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
public class StockDetail {

	private Long sno;
	private Long amount;
	private LocalDateTime arrivedatetime;
	private LocalDateTime orderdatetime;
	private Long uno;
	private Long mno;

	private String code;
	private String name;
	private String type;
	private int buyprice;
	private int sellprice;
	private String company_name;
	private String imgPath;
}
