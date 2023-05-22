package me.donghun.memberservice.adapter.input.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import me.donghun.memberservice.domain.model.CodeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = ValidUrl.ValidUrlValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
public @interface ValidUrl {
    String message() default "url 형식이 맞지 않습니다.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

    class ValidUrlValidator implements ConstraintValidator<ValidUrl, String> {


        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            Pattern pattern = Pattern.compile("^((http|https)://)?(www\\.)?([a-zA-Z0-9]+\\.)?[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}(\\/.*)?$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
    }
}
