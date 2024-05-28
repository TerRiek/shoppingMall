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
@Table(name = "review")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	
	@ManyToOne
	@JoinColumn(name = "mno")
	@ToString.Exclude
	private Merchandise mno;
		
	@ManyToOne
	@JoinColumn(name = "uno")
	@ToString.Exclude
	private User uno;
	
	private int score;
	private String content;
}
