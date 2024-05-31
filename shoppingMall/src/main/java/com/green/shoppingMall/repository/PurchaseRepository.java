package com.green.shoppingMall.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.shoppingMall.entity.Delivery;
import com.green.shoppingMall.entity.Purchase;
import com.green.shoppingMall.entity.User;


public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
	
	// @Query("SELECT SUM(purchase.amount), purchase.mno FROM Purchase purchase GROUP BY purchase.mno ORDER BY SUM(purchase.amount) DESC")
	// List<Purchase> findGroupByMnoOrderByAmountDesc();
	
	@Query(value = "DELETE FROM purchase WHERE mno = :mno", nativeQuery = true)
	void deleteByMno(@Param("mno") Long mno);
	
	@Query(value = "DELETE FROM purchase WHERE sno = :sno", nativeQuery = true)
	void deleteBySno(@Param("sno") Long sno);
	
	@Query(value = "SELECT pno, SUM(amount) as amount, regdatetime, cno, dno, mno FROM purchase GROUP BY mno HAVING mno = :mno", nativeQuery = true)
	Purchase findByMno(@Param("mno") Long mno);

	public List<Purchase> findByDnoOrderByRegdatetimeDesc(Delivery dno);
	
	@Query(value="SELECT *, SUM(pamount) AS amount FROM purchase GROUP BY sno ORDER BY SUM(pamount) DESC;", nativeQuery = true)
	public List<Purchase> findAllGroupByAmountAllOrderByAmountDesc();
}
