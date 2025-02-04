package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User entity class tests")
class UserTest {

    private static String id;
    private static String firstName;
    private static String lastName;
    private static String username;
    private static String password;
    private static String email;
    private static String phone;
    private static Role role;
    private static String pictureId;
    private static List<String> favouriteMovieIds;
    private static List<String> viewedMovieIds;
    private static List<String> waitMovieIds;
    private static List<String> messageIds;

    private User user;

    @BeforeAll
    static void beforeAll() {
        UserTest.id = "1";
        UserTest.firstName = "John";
        UserTest.lastName = "Doe";
        UserTest.username = "JohnDoe";
        UserTest.password = "Doe";
        UserTest.email = "john@doe.com";
        UserTest.phone = "123456789";
        UserTest.role = Role.USER;
        UserTest.pictureId = "1";
        UserTest.favouriteMovieIds = Collections.emptyList();
        UserTest.viewedMovieIds = Collections.emptyList();
        UserTest.waitMovieIds = Collections.emptyList();
        UserTest.messageIds = Collections.emptyList();
    }

    @BeforeEach
    void setUp() {
        user = initUser();
    }

    @Test
    @DisplayName("Test all-args constructor initializes fields correctly")
    void testAllArgsConstructor() {
        String idLocal = "100";
        String firstNameLocal = "100";
        String lastNameLocal = "100";
        String usernameLocal = "100";
        String passwordLocal = "100";
        String emailLocal = "100";
        String phoneLocal = "100";
        Role roleLocal = Role.USER;
        String pictureIdLocal = "100";
        List<String> favouriteMovieIdsLocal = Collections.emptyList();
        List<String> viewedMovieIdsLocal = Collections.emptyList();
        List<String> waitMovieIdsLocal = Collections.emptyList();
        List<String> messageIdsLocal = Collections.emptyList();

        user = new User(
                idLocal,
                firstNameLocal,
                lastNameLocal,
                usernameLocal,
                passwordLocal,
                emailLocal,
                phoneLocal,
                roleLocal,
                pictureIdLocal,
                favouriteMovieIdsLocal,
                viewedMovieIdsLocal,
                waitMovieIdsLocal,
                messageIdsLocal
        );

        assertEquals(idLocal, user.getId(), "Id does not match the expected value");
        assertEquals(firstNameLocal, user.getFirstName(), "First name does not match the expected value");
        assertEquals(lastNameLocal, user.getLastName(), "Last name does not match the expected value");
        assertEquals(usernameLocal, user.getUsername(), "Username does not match the expected value");
        assertEquals(passwordLocal, user.getPassword(), "Password does not match the expected value");
        assertEquals(emailLocal, user.getEmail(), "Email does not match the expected value");
        assertEquals(phoneLocal, user.getPhone(), "Phone does not match the expected value");
        assertEquals(roleLocal, user.getRole(), "Role does not match the expected value");
        assertEquals(pictureIdLocal, user.getPictureId(), "Picture id does not match the expected value");
        assertEquals(favouriteMovieIdsLocal, user.getFavouriteMovieIds(), "Favourite movie ids does not match the expected value");
        assertEquals(viewedMovieIdsLocal, user.getViewedMovieIds(), "Viewed movie ids does not match the expected value");
        assertEquals(waitMovieIdsLocal, user.getWaitMovieIds(), "Wait movie ids does not match the expected value");
        assertEquals(messageIdsLocal, user.getMessageIds(), "Messages does not match the expected value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        user.setId(id);

        assertEquals(id, user.getId(), "Id does not match the value set");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals(UserTest.id, user.getId(), "Getter for id returned an unexpected value");
        assertEquals(UserTest.firstName, user.getFirstName(), "Getter for name returned an unexpected value");
        assertEquals(UserTest.lastName, user.getLastName(), "Getter for name returned an unexpected value");
        assertEquals(UserTest.username, user.getUsername(), "Getter for name returned an unexpected value");
        assertEquals(UserTest.password, user.getPassword(), "Getter for name returned an unexpected value");
        assertEquals(UserTest.email, user.getEmail(), "Getter for name returned an unexpected value");
        assertEquals(UserTest.phone, user.getPhone(), "Getter for name returned an unexpected value");
        assertEquals(UserTest.role, user.getRole(), "Getter for name returned an unexpected value");
        assertEquals(UserTest.pictureId, user.getPictureId(), "Getter for id returned an unexpected value");
        assertEquals(UserTest.favouriteMovieIds, user.getFavouriteMovieIds(), "Getter for id returned an unexpected value");
        assertEquals(UserTest.viewedMovieIds, user.getViewedMovieIds(), "Getter for id returned an unexpected value");
        assertEquals(UserTest.waitMovieIds, user.getWaitMovieIds(), "Getter for id returned an unexpected value");
        assertEquals(UserTest.messageIds, user.getMessageIds(), "Getter for id returned an unexpected value");
    }

    private User initUser() {
        return User.builder()
                .id(UserTest.id)
                .firstName(UserTest.firstName)
                .lastName(UserTest.lastName)
                .username(UserTest.username)
                .password(UserTest.password)
                .email(UserTest.email)
                .phone(UserTest.phone)
                .role(UserTest.role)
                .pictureId(UserTest.pictureId)
                .favouriteMovieIds(UserTest.favouriteMovieIds)
                .viewedMovieIds(UserTest.viewedMovieIds)
                .waitMovieIds(UserTest.waitMovieIds)
                .messageIds(UserTest.messageIds)
                .build();
    }

    @Nested
    @DisplayName("UserBuilder nested class tests")
    class UserBuilderTest {

        @Test
        @DisplayName("Test builder initializes fields correctly")
        void testBuilderInitialization() {
            String idLocal = "1000";
            String firstNameLocal = "1000";
            String lastNameLocal = "1000";
            String usernameLocal = "1000";
            String passwordLocal = "1000";
            String emailLocal = "1000";
            String phoneLocal = "1000";
            Role roleLocal = Role.ADMIN;
            String pictureIdLocal = "1000";
            List<String> favouriteMovieIdsLocal = Collections.emptyList();
            List<String> viewedMovieIdsLocal = Collections.emptyList();
            List<String> waitMovieIdsLocal = Collections.emptyList();
            List<String> messageIdsLocal = Collections.emptyList();

            user = User.builder()
                    .id(idLocal)
                    .firstName(firstNameLocal)
                    .lastName(lastNameLocal)
                    .username(usernameLocal)
                    .password(passwordLocal)
                    .email(emailLocal)
                    .phone(phoneLocal)
                    .role(roleLocal)
                    .pictureId(pictureIdLocal)
                    .favouriteMovieIds(favouriteMovieIdsLocal)
                    .viewedMovieIds(viewedMovieIdsLocal)
                    .waitMovieIds(waitMovieIdsLocal)
                    .messageIds(messageIdsLocal)
                    .build();

            assertEquals(idLocal, user.getId(), "Id does not match the expected value");
            assertEquals(firstNameLocal, user.getFirstName(), "First name does not match the expected value");
            assertEquals(lastNameLocal, user.getLastName(), "Last name does not match the expected value");
            assertEquals(usernameLocal, user.getUsername(), "Username does not match the expected value");
            assertEquals(passwordLocal, user.getPassword(), "Password does not match the expected value");
            assertEquals(emailLocal, user.getEmail(), "Email does not match the expected value");
            assertEquals(phoneLocal, user.getPhone(), "Phone does not match the expected value");
            assertEquals(roleLocal, user.getRole(), "Role does not match the expected value");
            assertEquals(pictureIdLocal, user.getPictureId(), "Picture id does not match the expected value");
            assertEquals(favouriteMovieIdsLocal, user.getFavouriteMovieIds(), "Favourite movie ids does not match the expected value");
            assertEquals(viewedMovieIdsLocal, user.getViewedMovieIds(), "Viewed movie ids does not match the expected value");
            assertEquals(waitMovieIdsLocal, user.getWaitMovieIds(), "Wait movie ids does not match the expected value");
            assertEquals(messageIdsLocal, user.getMessageIds(), "Messages does not match the expected value");
        }

        @Test
        @DisplayName("Test builder defaults to empty collections if no values are added")
        void testBuilderEmptyCollections() {
            user = User.builder().build();
            List<String> favouriteMovieIdsActual = user.getFavouriteMovieIds();
            List<String> viewedMovieIdsActual = user.getViewedMovieIds();
            List<String> waitMovieIdsActual = user.getWaitMovieIds();
            List<String> messageIdsActual = user.getMessageIds();

            assertNotNull(favouriteMovieIdsActual, "Favourite movies should not be null");
            assertTrue(favouriteMovieIdsActual.isEmpty(), "Favourite movies should be empty");
            assertNotNull(viewedMovieIdsActual, "Viewed movies should not be null");
            assertTrue(viewedMovieIdsActual.isEmpty(), "Viewed movies should be empty");
            assertNotNull(waitMovieIdsActual, "Wait movies should not be null");
            assertTrue(waitMovieIdsActual.isEmpty(), "Wait movies should be empty");
            assertNotNull(messageIdsActual, "Messages should not be null");
            assertTrue(messageIdsActual.isEmpty(), "Messages should be empty");
        }

        @Test
        @DisplayName("Test @Singular fields handle multiple values correctly")
        void testSingularFields_whenAdd() {
            List<String> favouriteMovieIdsExpected = List.of("favorite1", "favorite2");
            List<String> viewedMovieIdsExpected = List.of("viewed1", "viewed2");
            List<String> waitMovieIdsExpected = List.of("wait1", "wait2");
            List<String> messageIdsExpected = List.of("message1", "message2");

            user = User.builder()
                    .favouriteMovieId("favorite1")
                    .favouriteMovieId("favorite2")
                    .viewedMovieId("viewed1")
                    .viewedMovieId("viewed2")
                    .waitMovieId("wait1")
                    .waitMovieId("wait2")
                    .messageId("message1")
                    .messageId("message2")
                    .build();

            assertEquals(favouriteMovieIdsExpected, user.getFavouriteMovieIds(), "Favourite movies do not match the expected values");
            assertEquals(viewedMovieIdsExpected, user.getViewedMovieIds(), "Viewed movies do not match the expected values");
            assertEquals(waitMovieIdsExpected, user.getWaitMovieIds(), "Wait movies do not match the expected values");
            assertEquals(messageIdsExpected, user.getMessageIds(), "Messages do not match the expected values");
        }

        @Test
        @DisplayName("Test builder defaults to fields if no values are added")
        void testBuilderDefaultFields() {
            user = User.builder().build();
            Role roleExpected = Role.USER;

            assertEquals(roleExpected, user.getRole(), "Role does not match the expected value");
        }

    }

}