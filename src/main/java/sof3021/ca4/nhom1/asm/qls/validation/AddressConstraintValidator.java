package sof3021.ca4.nhom1.asm.qls.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressConstraintValidator
    implements ConstraintValidator<AddressConstraint, String> {

    @Override
    public void initialize(AddressConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return false;
        if(value.isEmpty()) return false;
        return true;
    }
}
