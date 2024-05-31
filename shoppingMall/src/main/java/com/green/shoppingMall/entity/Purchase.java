package com.green.shoppingMall.entity;

import java.time.LocalDateTime;

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
@Table(name = "purchase")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pno;
	
	@ManyToOne
	@JoinColumn(name = "sno")
	@ToString.Exclude
	private Stock sno;
	
	@ManyToOne
	@JoinColumn(name = "dno")
	@ToString.Exclude
	private Delivery dno;

	private Long pamount;
	private LocalDateTime regdatetime;
}
