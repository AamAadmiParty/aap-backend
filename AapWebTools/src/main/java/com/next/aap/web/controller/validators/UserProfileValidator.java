package com.next.aap.web.controller.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.google.gdata.util.common.base.StringUtil;
import com.next.aap.web.dto.UserDto;

@Component
public class UserProfileValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("UserDto.class.isAssignableFrom(clazz)="+UserDto.class.isAssignableFrom(clazz));
		return UserDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		UserDto user = (UserDto) arg0;
		System.out.println("user.getName()=["+user.getName()+"]");
		if(StringUtil.isEmptyOrWhitespace(user.getName())){
			System.out.println("Please Enter Name ");
			errors.reject("name", "Please Enter Name");
		}

	}

}
