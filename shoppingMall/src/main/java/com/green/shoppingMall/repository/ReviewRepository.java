package com.green.shoppingMall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.shoppingMall.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query(value = "DELETE FROM review WHERE mno = :mno", nativeQuery = true)
	void deleteByMno(@Param("mno") Long mno);
}
