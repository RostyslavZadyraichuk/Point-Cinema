package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Role;
import com.zadyraichuk.point_cinema.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User repository tests")
@DataMongoTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static User user;

    @BeforeEach
    void setUp() {
        user = initUser();
        user = userRepository.save(user);
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test creating an User")
    void testCreate(String username, String email, String phone, boolean shouldThrowException) {
        User userLocal = new User(null, "Unique", "Unique", "Unique", username,
                email, phone, Role.USER, "Unique", Collections.emptySet(), Collections.emptySet(),
                Collections.emptySet(), Collections.emptyList());
        assertNull(userLocal.getId(), "The user has just created should have no ID");

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> userRepository.save(userLocal),
                    "Should have thrown an exception due to duplicate username, email or phone");
        } else {
            User savedUser = assertDoesNotThrow(() -> userRepository.save(userLocal));
            assertNotNull(savedUser.getId(), "The saved user should have a generated ID");
            assertEquals(userLocal.getFirstName(), savedUser.getFirstName(), "The first name should match");
            assertEquals(userLocal.getLastName(), savedUser.getLastName(), "The last name should match");
            assertEquals(userLocal.getPictureId(), savedUser.getPictureId(), "The picture ID should match");
            assertEquals(userLocal.getRole(), savedUser.getRole(), "The role should match");
            assertEquals(userLocal.getUsername(), savedUser.getUsername(), "The username should match");
            assertEquals(userLocal.getEmail(), savedUser.getEmail(), "The email should match");
            assertEquals(userLocal.getPhone(), savedUser.getPhone(), "The phone should match");
            assertEquals(userLocal.getFavouriteMovieIds(), savedUser.getFavouriteMovieIds(), "The favourite movie IDs should match");
            assertEquals(userLocal.getWaitMovieIds(), savedUser.getWaitMovieIds(), "The wait movie IDs should match");
            assertEquals(userLocal.getViewedMovieIds(), savedUser.getViewedMovieIds(), "The viewed movie IDs should match");
        }

    }

    @Test
    @DisplayName("Test finding an User by ID")
    void testFindById() {
        Optional<User> foundUserOpt = userRepository.findById(user.getId());
        assertTrue(foundUserOpt.isPresent(), "The user should be found by ID");

        User foundUser = foundUserOpt.get();
        assertEquals(user.getId(), foundUser.getId(), "The IDs should match");
        assertEquals(user.getFirstName(), foundUser.getFirstName(), "The first name should match");
        assertEquals(user.getLastName(), foundUser.getLastName(), "The last name should match");
        assertEquals(user.getPictureId(), foundUser.getPictureId(), "The picture ID should match");
        assertEquals(user.getRole(), foundUser.getRole(), "The role should match");
        assertEquals(user.getUsername(), foundUser.getUsername(), "The username should match");
        assertEquals(user.getEmail(), foundUser.getEmail(), "The email should match");
        assertEquals(user.getPhone(), foundUser.getPhone(), "The phone should match");
        assertEquals(user.getFavouriteMovieIds(), foundUser.getFavouriteMovieIds(), "The favourite movie IDs should match");
        assertEquals(user.getWaitMovieIds(), foundUser.getWaitMovieIds(), "The wait movie IDs should match");
        assertEquals(user.getViewedMovieIds(), foundUser.getViewedMovieIds(), "The viewed movie IDs should match");
    }

    @Test
    @DisplayName("Test finding all Users")
    void testFindAll() {
        User[] users = getUsersForTests();
        userRepository.saveAll(Arrays.asList(users));
        int expectedLength = users.length + 1;
        List<User> foundUsers = userRepository.findAll();

        assertNotNull(foundUsers, "The found list should not be null");
        assertFalse(foundUsers.isEmpty(), "The found list should not be empty");
        assertEquals(expectedLength, foundUsers.size(), "The size of the list should match the number of users saved");
        assertTrue(foundUsers.containsAll(Arrays.asList(users)), "The found users should contain all users saved before");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test updating an User")
    void testUpdate(String username, String email, String phone, boolean shouldThrowException) {
        User userBeforeUpdate = new User(null, "Unique", "Unique", "Unique", "Unique1",
                "Unique1", "Unique1", Role.USER, "Unique", Collections.emptySet(),
                Collections.emptySet(), Collections.emptySet(), Collections.emptyList());
        userBeforeUpdate = userRepository.save(userBeforeUpdate);
        User updatedUser = new User(null, "Unique", "Unique", "Unique", username,
                email, phone, Role.USER, "Unique", Collections.emptySet(),
                Collections.emptySet(), Collections.emptySet(), Collections.emptyList());
        updatedUser.setId(userBeforeUpdate.getId());

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> userRepository.save(updatedUser),
                    "Should have thrown an exception due to duplicate username, email or phone");
        } else {
            user = assertDoesNotThrow(() -> userRepository.save(updatedUser));
            assertEquals(updatedUser.getId(), user.getId(), "The ID should remain the same after update");
            assertEquals(updatedUser.getFirstName(), user.getFirstName(), "The first name should match");
            assertEquals(updatedUser.getLastName(), user.getLastName(), "The last name should match");
            assertEquals(updatedUser.getPictureId(), user.getPictureId(), "The picture ID should match");
            assertEquals(updatedUser.getRole(), user.getRole(), "The role should match");
            assertEquals(updatedUser.getUsername(), user.getUsername(), "The username should match");
            assertEquals(updatedUser.getEmail(), user.getEmail(), "The email should match");
            assertEquals(updatedUser.getPhone(), user.getPhone(), "The phone should match");
            assertEquals(updatedUser.getFavouriteMovieIds(), user.getFavouriteMovieIds(), "The favourite movie IDs should match");
            assertEquals(updatedUser.getWaitMovieIds(), user.getWaitMovieIds(), "The wait movie IDs should match");
            assertEquals(updatedUser.getViewedMovieIds(), user.getViewedMovieIds(), "The viewed movie IDs should match");
        }
    }

    @Test
    @DisplayName("Test save all Users")
    void testSaveAll() {
        User[] users = getUsersForTests();

        List<User> savedUsers = userRepository.saveAll(Arrays.asList(users));

        assertNotNull(savedUsers, "The saved users list should not be null");
        assertEquals(users.length, savedUsers.size(), "The size of the saved users should match the input list size");
        Stream<String> userIds = savedUsers.stream().map(User::getId);
        assertTrue(userIds.allMatch(Objects::nonNull), "Each saved user should have a generated ID");

        List<User> foundUsers = userRepository.findAll();
        int expectedSize = users.length + 1;
        assertEquals(expectedSize, foundUsers.size(), "The number of users found should match the saved users");
        assertTrue(foundUsers.containsAll(savedUsers), "The found users should match the saved users");
    }

    @Test
    @DisplayName("Test deleting an User")
    void testDelete() {
        userRepository.delete(user);

        Optional<User> deletedUserOpt = userRepository.findById(user.getId());
        assertFalse(deletedUserOpt.isPresent(), "The user should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting an User by ID")
    void testDeleteById() {
        userRepository.deleteById(user.getId());

        Optional<User> deletedUserOpt = userRepository.findById(user.getId());
        assertFalse(deletedUserOpt.isPresent(), "The user should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting all Users")
    void testDeleteAll() {
        User[] users = getUsersForTests();
        userRepository.saveAll(Arrays.asList(users));

        List<User> foundUsers = userRepository.findAll();
        assertNotNull(foundUsers, "The users should exist before deleting");
        assertFalse(foundUsers.isEmpty(), "The users should exist before deleting");

        userRepository.deleteAll();
        foundUsers = userRepository.findAll();
        assertNotNull(foundUsers, "The users should be deleted and users list should not be null");
        assertTrue(foundUsers.isEmpty(), "The users should be deleted and users list should be empty");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByUsernameOrEmailOrPhoneTest")
    @DisplayName("Test finding an User by Username or Email or Phone")
    void testFindByUsernameOrEmailOrPhone(String usernameOrEmailOrPhone,
                                          boolean shouldExists,
                                          int expectedUserIndex) {
        User[] users = getUsersForTests();
        userRepository.saveAll(Arrays.asList(users));

        Optional<User> foundUserOpt = userRepository.findByUsernameOrEmailOrPhone(usernameOrEmailOrPhone);

        assertNotNull(foundUserOpt, "The result should not be null");
        assertEquals(foundUserOpt.isPresent(), shouldExists,
                String.format("The result should contain user with username, email or phone '%s'", usernameOrEmailOrPhone));
        foundUserOpt.ifPresent(value -> assertEquals(value, users[expectedUserIndex], "User should match the expected user"));
    }

    private User initUser() {
        return User.builder()
                .username("test")
                .email("test")
                .phone("test")
                .firstName("test")
                .lastName("test")
                .password("test")
                .role(Role.USER)
                .pictureId("test")
                .favouriteMovieIds(Collections.emptyList())
                .viewedMovieIds(Collections.emptyList())
                .waitMovieIds(Collections.emptyList())
                .messageIds(Collections.emptyList())
                .build();
    }

    private User[] getUsersForTests() {
        User unique1 = new User(null, "Unique", "Unique", "Unique", "Unique1",
                "Unique11", "Unique111", Role.USER, "Unique", Collections.emptySet(),
                Collections.emptySet(), Collections.emptySet(), Collections.emptyList());
        User unique2 = new User(null, "Unique", "Unique", "Unique", "Unique2",
                "Unique22", "Unique222", Role.USER, "Unique", Collections.emptySet(),
                Collections.emptySet(), Collections.emptySet(), Collections.emptyList());
        User unique3 = new User(null, "Unique", "Unique", "Unique", "Unique3",
                "Unique33", "Unique333", Role.USER, "Unique", Collections.emptySet(),
                Collections.emptySet(), Collections.emptySet(), Collections.emptyList());

        return new User[]{unique1, unique2, unique3};
    }

    /**
     * Provides arguments for creating and updating tests in {@link UserRepository}.
     * Each argument consists of:
     * <ul>
     *     <li>username - the username of the user to save or update</li>
     *     <li>email - the email of the user to save or update</li>
     *     <li>phone - the phone of the user to save or update</li>
     *     <li>shouldThrowException - whether the test should expect a DuplicateKeyException</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForCreateAndUpdateTests() {
        return Stream.of(
                Arguments.of("Unique", "Unique", "Unique", false),
                Arguments.of(user.getUsername(), "Unique", "Unique", true),
                Arguments.of("Unique", user.getEmail(), "Unique", true),
                Arguments.of("Unique", "Unique", user.getPhone(), true)
        );
    }

    /**
     * Provides arguments for testing the findByUsernameOrEmailOrPhone method in {@link UserRepository}.
     * Each argument consists of:
     * <ul>
     *     <li>usernameOrEmailOrPhone - the value to search for a user by username, email, or phone</li>
     *     <li>shouldExists - whether the user with the given username, email, or phone should exist in the test data</li>
     *     <li>expectedUserIndex - the index of the expected user in the test data, or null if no user should be found</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByUsernameOrEmailOrPhoneTest() {
        return Stream.of(
                Arguments.of("U", false, -1),
                Arguments.of("Unique", false, -1),
                Arguments.of("unique1", false, -1),
                Arguments.of("", false, -1),
                Arguments.of("Unique1", true, 0),
                Arguments.of("Unique11", true, 0),
                Arguments.of("Unique111", true, 0),
                Arguments.of("Unique2", true, 1),
                Arguments.of("Unique22", true, 1),
                Arguments.of("Unique222", true, 1),
                Arguments.of("Unique3", true, 2),
                Arguments.of("Unique33", true, 2),
                Arguments.of("Unique333", true, 2)
        );
    }

}