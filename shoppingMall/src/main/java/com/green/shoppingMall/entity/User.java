package com.green.shoppingMall.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
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
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uno;
	
	@Column(unique = true)
	private String username;
	private String password;
	
	@Column(unique = true)
	private String email;
	private String internationalNumber;
	private String telephone;
	private String role;
	private LocalDate regdate;
	
}
