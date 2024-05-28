package com.green.shoppingMall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.green.shoppingMall.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
