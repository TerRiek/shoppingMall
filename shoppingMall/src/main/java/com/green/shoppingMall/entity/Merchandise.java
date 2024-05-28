package com.green.shoppingMall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "merchandise")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Merchandise {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mno;
	
	private String code;
	private String name;
	private String type;
	private int buyprice;
	private int sellprice;
	private String company_name;
	
}
