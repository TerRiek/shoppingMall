package com.green.shoppingMall.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "delivery")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Delivery {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dno;
	
	@ManyToOne
	@JoinColumn(name = "uno")
	@ToString.Exclude
	private User uno;
	
	private String address1;
	private String address2;
	private String telephone;
	private String name;
	
}
