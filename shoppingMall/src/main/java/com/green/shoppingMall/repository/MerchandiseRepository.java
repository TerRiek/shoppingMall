package com.green.shoppingMall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.shoppingMall.entity.Merchandise;

public interface MerchandiseRepository extends JpaRepository<Merchandise, Long>{

	Merchandise findByMno(@Param("mno") Long mno);
	
	@Query(value = "DELETE FROM merchandise WHERE mno = :mno", nativeQuery = true)
	void deleteByMno(@Param("mno") Long mno);
	
	
	
}
