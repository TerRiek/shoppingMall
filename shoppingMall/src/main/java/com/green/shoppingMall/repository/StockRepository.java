package com.green.shoppingMall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.shoppingMall.entity.Stock;


public interface StockRepository extends JpaRepository<Stock, Long>{
	
	@Query(value = "DELETE FROM stock WHERE mno = :mno", nativeQuery = true)
	void deleteByMno(@Param("mno") Long mno);
	
	@Query(value="SELECT * FROM stock ORDER BY amount ASC", nativeQuery=true)
	List<Stock> findAllOrderByAmountAsc();
	
	@Query(value="SELECT * FROM stock WHERE mno = :mno GROUP BY mno", nativeQuery = true)
	Stock findByMno(@Param("mno") Long mno);
	
	@Query(value = "SELECT * FROM stock WHERE mno = :mno", nativeQuery = true)
	Stock findByMnos(@Param("mno") Long mno);
	
	@Query(value="SELECT * FROM stock ORDER BY orderdatetime DESC", nativeQuery = true)
	public List<Stock> findOrderByOrderdatetimeDesc();
	
}
