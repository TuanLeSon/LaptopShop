package vn.hoidanit.laptopshop.service.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = RegisterValidator.class)
@Target({ ElementType.TYPE }) // Adjusted to apply to the class level
@Retention(RetentionPolicy.RUNTIME)
@Documented
// mỗi lần chạy vào annotation này sẽ phải chạy tiếp vào class constrain phía
// trên
public @interface RegisterChecked {

    String message() default "User register validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
