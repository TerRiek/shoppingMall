package com.green.shoppingMall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.shoppingMall.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	@Query(value = "DELETE FROM cart WHERE mno = :mno", nativeQuery = true)
	void deleteByMno(@Param("mno") Long mno);

	@Query(value = "SELECT cno, SUM(amount) AS amount, mno, uno FROM cart WHERE uno = :uno GROUP BY mno;", nativeQuery = true)
	List<Cart> findAllByUno(@Param("uno") Long uno);
}
