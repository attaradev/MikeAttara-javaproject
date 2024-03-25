package dev.attara.stockify.domain.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.PositiveOrZero;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
@DecimalMin(value = "0")
@PositiveOrZero
@ReportAsSingleViolation
public @interface NonNegative {
    String message() default "Value must be non-negative";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
