package com.team7.propertypredict.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.team7.propertypredict.model.User;
import com.team7.propertypredict.service.UserService;

 
@Component
public class UserValidator implements Validator{

	@Autowired 
	private UserService uService;
	
	@Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

	@Override
	public void validate(Object o, Errors errors) {
		
		User user = (User) o;
		
		if (uService.findUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "error.user.email.duplicate");
        }	
		
		if (user.getUsername() == null) {
            errors.rejectValue("name", "error.user.name.blank");
        }	
		
	}

	public void validateLogin(Object o, Errors errors) {
		
		User user = (User) o;
		User actualUser = uService.findUserByEmail(user.getEmail());
		if (actualUser == null) {
			errors.rejectValue("password", "error.user.name.wrongPassword");
		}
		else {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			Boolean match = bCryptPasswordEncoder.matches(user.getPassword(), actualUser.getPassword());		
			
			if (!match) {
				errors.rejectValue("password", "error.user.name.wrongPassword");
			}
		}	
	}

}
