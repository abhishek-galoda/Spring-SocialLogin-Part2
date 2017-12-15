package com.login.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.login.model.UserBean;

public interface UserRepository extends JpaRepository<UserBean, String> {

    	UserBean findByEmail(String email);

	@Modifying
	@Transactional
	@Query("UPDATE user SET firstName = :firstName, lastName = :lastName, country = :country, image = :image "
			+ " WHERE email = :email")
	void saveWithoutPassword(@Param("firstName") String firstname, @Param("lastName") String lastname,
			@Param("country") String country, @Param("image") String image,
			@Param("email") String email);
}
