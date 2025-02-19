package com.zadyraichuk.point_cinema.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Utility class for validation-related operations used in testing.
 * Provides a reusable validator and common validation scenarios.
 * <p>
 * This class offers:
 * <ul>
 *     <li>A method to generate a stream of arguments for @NotBlank validation.</li>
 *     <li>A method to validate an object against defined constraints and assert expected validity.</li>
 * </ul>
 * </p>
 */
public class ValidationTestUtils {

    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    public static Stream<Arguments> forNotBlankValidation() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of(" ", false),
                Arguments.of("a", true),
                Arguments.of("  a  ", true),
                Arguments.of("abc", true)
        );
    }

    public static void validate(Object object, boolean isValid) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        if (isValid) {
            assertEquals(0, violations.size(), "Validation should pass");
        } else {
            assertEquals(1, violations.size(), "Validation should fail");
            assertNotEquals(null, violations.iterator().next().getMessage());
        }
    }

}
