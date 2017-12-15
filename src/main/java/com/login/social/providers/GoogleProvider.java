package com.login.social.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.login.model.UserBean;

@Service
public class GoogleProvider   {

	private static final String REDIRECT_CONNECT_GOOGLE = "redirect:/login";
	private static final String GOOGLE = "google";

	@Autowired
    	BaseProvider baseProvider ;
    	

	public String getGoogleUserData(Model model, UserBean userBean) {

		ConnectionRepository connectionRepository = baseProvider.getConnectionRepository();
		if (connectionRepository.findPrimaryConnection(Google.class) == null) {
			return REDIRECT_CONNECT_GOOGLE;
		}

		populateUserDetailsFromGoogle(userBean);
		//Check if all Info has been collected
		if(!baseProvider.isAllInformationAvailable(userBean)) {
		    model.addAttribute("userBean", userBean);
		    return "incompleteInfo";
		}
		//Save the details in DB
		baseProvider.saveUserDetails(userBean);
		
		//Login the User
		baseProvider.autoLoginUser(userBean);
				
		model.addAttribute("loggedInUser",userBean);
		return "secure/user";
	}


	protected void populateUserDetailsFromGoogle(UserBean userform) {
		Google google = baseProvider.getGoogle();
		Person googleUser = google.plusOperations().getGoogleProfile();
		userform.setEmail(googleUser.getAccountEmail());
		userform.setFirstName(googleUser.getGivenName());
		userform.setLastName(googleUser.getFamilyName());
		userform.setImage(googleUser.getImageUrl());
		userform.setProvider(GOOGLE);
	}

}
