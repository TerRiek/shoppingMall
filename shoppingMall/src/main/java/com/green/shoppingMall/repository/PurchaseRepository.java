package com.green.shoppingMall.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.shoppingMall.entity.Purchase;


public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
	
	// @Query("SELECT SUM(purchase.amount), purchase.mno FROM Purchase purchase GROUP BY purchase.mno ORDER BY SUM(purchase.amount) DESC")
	// List<Purchase> findGroupByMnoOrderByAmountDesc();
	
	@Query(value = "DELETE FROM purchase WHERE mno = :mno", nativeQuery = true)
	void deleteByMno(@Param("mno") Long mno);
}
