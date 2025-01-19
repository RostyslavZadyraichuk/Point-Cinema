package com.zadyraichuk.point_cinema.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
class SpringDotenvConfigurationTest {

    @Test
    void testDotenvInjectValuesIntoSpring(@Value("${spring.dotenv.injection.test}") String value) {
        String expected = "TEST_VALUE";

        assertNotNull(value, "Value from Dotenv should be injected into Spring context");
        assertEquals(expected, value, "Value from Dotenv should be equal with properties value");
    }

}