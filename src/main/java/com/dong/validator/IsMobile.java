package com.dong.validator;

/**
 * 验证手机号
 */

import com.dong.vo.IsMobileValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidation.class}
)
public @interface IsMobile {
        //必填  手机号是必填的
         boolean required() default true;
        String message() default "手机号码格式错误";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
}
