package com.green.shoppingMall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.shoppingMall.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>{

	@Query(value="SELECT * FROM delivery WHERE uno = :uno", nativeQuery=true)
	Delivery findByUno(@Param("uno") Long uno);
}
