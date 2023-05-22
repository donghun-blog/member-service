package me.donghun.memberservice.adapter.input.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import me.donghun.memberservice.domain.model.CodeEnum;
import me.donghun.memberservice.domain.model.OccupationType;

import java.lang.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = EnumValid.EnumValidValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
public @interface EnumValid {
    String message() default "";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
    Class<? extends CodeEnum> target();

    class EnumValidValidator implements ConstraintValidator<EnumValid, Object> {
        private List<Object> validList;

        @Override
        public void initialize(EnumValid constraintAnnotation) {
            validList = Arrays
                    .stream(constraintAnnotation.target().getEnumConstants())
                    .map(CodeEnum::getName)
                    .collect(Collectors.toList());
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            return validList.contains(value);
//            boolean isValid = validList.contains(value);
//
//            if(!isValid) {
//                context
//                        .buildConstraintViolationWithTemplate(
//                                format("{Invalid Input for Code Field of Enum: Should be in %s}", validList)
//                        )
//                        .addConstraintViolation()
//                        .disableDefaultConstraintViolation();
//            }
//
//            return isValid;
        }
    }
}