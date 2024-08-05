package br.com.livecenografia.fact_wise2.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import br.com.livecenografia.fact_wise2.service.MyUsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the email value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = MyUsersEmailUnique.MyUsersEmailUniqueValidator.class
)
public @interface MyUsersEmailUnique {

    String message() default "{Exists.myUsers.email}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class MyUsersEmailUniqueValidator implements ConstraintValidator<MyUsersEmailUnique, String> {

        private final MyUsersService myUsersService;
        private final HttpServletRequest request;

        public MyUsersEmailUniqueValidator(final MyUsersService myUsersService,
                final HttpServletRequest request) {
            this.myUsersService = myUsersService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(myUsersService.get(Long.parseLong(currentId)).getEmail())) {
                // value hasn't changed
                return true;
            }
            return !myUsersService.emailExists(value);
        }

    }

}
