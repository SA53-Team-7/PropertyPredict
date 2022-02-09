package com.team7.propertypredict.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
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
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
				"user.email", "error.user.email.emptyOrSpace", 
				"Email Address must not be empty or contain space");
		
		if (uService.findUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "error.user.email.duplicate");
        }
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
				"user.password", "error.user.password.emptyOrSpace", 
				"Password must not be empty or contain space");
		
	}

}
