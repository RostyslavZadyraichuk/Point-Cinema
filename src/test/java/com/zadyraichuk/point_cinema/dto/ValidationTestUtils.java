package com.zadyraichuk.point_cinema.dto;

import com.zadyraichuk.point_cinema.dto.general.CinemaDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ValidationTestUtils {

    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        factory.close();
    }

    /**
     * Stream of Arguments for testing @NotBlank validation
     * contains pair (value, is valid)
     */
    public static final Stream<Arguments> NOT_BLANK_VALIDATION = Stream.of(
            Arguments.of(null, false),
            Arguments.of("", false),
            Arguments.of(" ", false),
            Arguments.of("a", true),
            Arguments.of("  a  ", true),
            Arguments.of("abc", true)
    );

    public static void validate(Object object, boolean isValid) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        if (isValid) {
            assertNotEquals(0, violations.size(), "Validation should pass");
        } else {
            assertEquals(1, violations.size(), "Validation should fail");
            assertNotEquals(null, violations.iterator().next().getMessage());
        }
    }

}
