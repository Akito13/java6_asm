package sof3021.ca4.nhom1.asm.qls.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.parameters.P;

import java.util.regex.Pattern;

public class PasswordConstraintValidator
    implements ConstraintValidator<PasswordConstraint, String> {
    Pattern textPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");
    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;
        if(value.isBlank() || value.length() <= 4) return false;
        return textPattern.matcher(value).matches();
    }
}
