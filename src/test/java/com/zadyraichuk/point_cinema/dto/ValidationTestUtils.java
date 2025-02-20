package com.zadyraichuk.point_cinema.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mockito;

import java.util.Arrays;
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

    public static Stream<Arguments> forPositiveValidation() {
        return Stream.of(
                Arguments.of(-128, false),
                Arguments.of(0, false),
                Arguments.of(127, true)
        );
    }

    public static Stream<Arguments> forNotNullValidation(Class<?> clazz) {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(getInstance(clazz), true)
        );
    }

    public static Stream<Arguments> forEnumValidation(Class<? extends Enum<?>> enumType) {
        return Stream.concat(
                Arrays.stream(enumType.getEnumConstants())
                        .map(enumConstant -> Arguments.of(enumConstant, true)),
                Stream.of(Arguments.of(null, false))
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

    private static Object getInstance(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return getDefaultPrimitiveValue(clazz);
        }
        if (clazz == String.class) {
            return "";
        }
        if (Number.class.isAssignableFrom(clazz)) {
            return 0;
        }
        if (clazz == Boolean.class) {
            return false;
        }
        if (clazz == Character.class) {
            return '\0';
        }
        if (clazz == Class.class) {
            return Object.class;
        }
        return Mockito.mock(clazz);
    }

    private static Object getDefaultPrimitiveValue(Class<?> primitiveType) {
        if (primitiveType == int.class) return 0;
        if (primitiveType == long.class) return 0L;
        if (primitiveType == double.class) return 0.0;
        if (primitiveType == float.class) return 0.0f;
        if (primitiveType == boolean.class) return false;
        if (primitiveType == char.class) return '\0';
        if (primitiveType == byte.class) return (byte) 0;
        if (primitiveType == short.class) return (short) 0;
        return null;
    }

}
