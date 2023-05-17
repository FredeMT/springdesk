package br.com.springdesk.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
	
	@Override
    public void initialize(PasswordConstraint password) {
    }

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value.matches(".*\\d+.*{5,10}")) {
			return true;
		}
		return false;
	}

}
