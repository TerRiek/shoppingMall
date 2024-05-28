package com.green.shoppingMall.entity;

import java.time.LocalDateTime;

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
@Table(name = "stock")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sno;
	
	@ManyToOne
	@JoinColumn(name = "uno")
	@ToString.Exclude
	private User uno;
	
	@ManyToOne
	@JoinColumn(name = "mno")
	@ToString.Exclude
	private Merchandise mno;
	
	private Long amount;
	private LocalDateTime orderdatetime;
	private LocalDateTime arrivedatetime;
	
}
