package sof3021.ca4.nhom1.asm.qls.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneConstraintValidator implements ConstraintValidator<PhoneConstraint, String> {

    private Pattern phonePattern = Pattern.compile("(^\\d+$)");
    @Override
    public void initialize(PhoneConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;
        if(value.isBlank() || value.length() < 10) return false;
        return phonePattern.matcher(value).matches();
    }
}
