package com.green.shoppingMall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.shoppingMall.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
	
	@Query(value = "UPDATE user SET email = :email, international_number = :international_number, password = :password, telephone = :telephone, username = :username WHERE uno = :uno", nativeQuery = true)
	void saveByUno(@Param("uno") Long uno, @Param("email") String email, @Param("international_number") String international_number, @Param("password") String password, @Param("telephone") String telephone, @Param("username") String username);
}
