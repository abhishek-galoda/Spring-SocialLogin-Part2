package com.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.login.model.UserBean;

public interface UserRepository extends JpaRepository<UserBean, String> {

    	UserBean findByEmail(String email);

}

