package com.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.login.model.UserBean;
import com.login.repository.UserRepository;

@Controller
public class LoggedInUserController {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute("loggedInUser")
    public void secureUser(Model model) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	UserBean user = userRepository.findByEmail(auth.getName());
	model.addAttribute("loggedInUser", user);
    }

    @GetMapping("/secure/user")
    public String securePage() {
	return "secure/user";
    }

}
