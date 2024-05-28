package com.green.shoppingMall.entity;

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
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cno;
	
	@ManyToOne
	@JoinColumn(name = "uno")
	@ToString.Exclude
	private User uno;
	
	@ManyToOne
	@JoinColumn(name = "mno")
	@ToString.Exclude
	private Merchandise mno;
	
	private Long amount;
}
