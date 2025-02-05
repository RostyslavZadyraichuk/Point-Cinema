package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Genre;
import com.zadyraichuk.point_cinema.entity.MPAA;
import com.zadyraichuk.point_cinema.entity.Movie;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Movie repository tests")
@DataMongoTest
@ActiveProfiles("test")
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    private static Movie movie;

    @BeforeEach
    void setUp() {
        movie = initMovie();
        movie = movieRepository.save(movie);
    }

    @AfterEach
    void afterEach() {
        movieRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test creating an Movie")
    void testCreate(String name,
                    String surname,
                    boolean shouldThrowException) {
        Movie movieLocal = new Movie(null, name, surname, "", "", 0,
                LocalDate.now(), 0.0, MPAA.G, 0.0, "", "", "",
                List.of(), Set.of(), Set.of());
        assertNull(movieLocal.getId(), "The movie has just created should have no ID");

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> movieRepository.save(movieLocal),
                    "Should have thrown an exception due to duplicate name and surname");
        } else {
            Movie savedMovie = assertDoesNotThrow(() -> movieRepository.save(movieLocal), "The movie should be saved");
            assertNotNull(savedMovie.getId(), "The saved movie should have a generated ID");
            assertEquals(movieLocal.getName(), savedMovie.getName(), "The name should match");
            assertEquals(movieLocal.getSurname(), savedMovie.getSurname(), "The surname should match");
            assertEquals(movieLocal.getDescription(), savedMovie.getDescription(), "The description should match");
            assertEquals(movieLocal.getDirectedBy(), savedMovie.getDirectedBy(), "The directed by should match");
            assertEquals(movieLocal.getDuration(), savedMovie.getDuration(), "The duration should match");
            assertEquals(movieLocal.getReleaseDate(), savedMovie.getReleaseDate(), "The release date should match");
            assertEquals(movieLocal.getUsersRating(), savedMovie.getUsersRating(), "The users rating should match");
            assertEquals(movieLocal.getMpaaRating(), savedMovie.getMpaaRating(), "The MPAA rating should match");
            assertEquals(movieLocal.getImdbRating(), savedMovie.getImdbRating(), "The IMDB rating should match");
            assertEquals(movieLocal.getMovieCountry(), savedMovie.getMovieCountry(), "The country should match");
            assertEquals(movieLocal.getWidePictureId(), savedMovie.getWidePictureId(), "The wide picture ID should match");
            assertEquals(movieLocal.getPosterPictureId(), savedMovie.getPosterPictureId(), "The poster picture ID should match");
            assertEquals(movieLocal.getGalleryPictureIds().size(), savedMovie.getGalleryPictureIds().size(), "The gallery picture IDs size should match");
            assertTrue(movieLocal.getGalleryPictureIds().containsAll(savedMovie.getGalleryPictureIds()), "The gallery picture IDs should contain all saved IDs");
            assertEquals(movieLocal.getActorIds().size(), savedMovie.getActorIds().size(), "The actor IDs size should match");
            assertTrue(movieLocal.getActorIds().containsAll(savedMovie.getActorIds()), "The actor IDs should contain all saved IDs");
            assertEquals(movieLocal.getGenres().size(), savedMovie.getGenres().size(), "The genres size should match");
            assertTrue(movieLocal.getGenres().containsAll(savedMovie.getGenres()), "The genres should contain all saved genres");
        }
    }

    @Test
    @DisplayName("Test finding an Movie by ID")
    void testFindById() {
        Optional<Movie> foundMovieOpt = movieRepository.findById(movie.getId());
        assertTrue(foundMovieOpt.isPresent(), "The movie should be found by ID");

        Movie foundMovie = foundMovieOpt.get();
        assertEquals(movie.getId(), foundMovie.getId(), "The IDs should match");
        assertEquals(movie.getName(), foundMovie.getName(), "The name should match");
        assertEquals(movie.getSurname(), foundMovie.getSurname(), "The surname should match");
        assertEquals(movie.getDescription(), foundMovie.getDescription(), "The description should match");
        assertEquals(movie.getDirectedBy(), foundMovie.getDirectedBy(), "The directed by should match");
        assertEquals(movie.getDuration(), foundMovie.getDuration(), "The duration should match");
        assertEquals(movie.getReleaseDate(), foundMovie.getReleaseDate(), "The release date should match");
        assertEquals(movie.getUsersRating(), foundMovie.getUsersRating(), "The users rating should match");
        assertEquals(movie.getMpaaRating(), foundMovie.getMpaaRating(), "The MPAA rating should match");
        assertEquals(movie.getImdbRating(), foundMovie.getImdbRating(), "The IMDB rating should match");
        assertEquals(movie.getMovieCountry(), foundMovie.getMovieCountry(), "The country should match");
        assertEquals(movie.getWidePictureId(), foundMovie.getWidePictureId(), "The wide picture ID should match");
        assertEquals(movie.getPosterPictureId(), foundMovie.getPosterPictureId(), "The poster picture ID should match");
        assertEquals(movie.getGalleryPictureIds().size(), foundMovie.getGalleryPictureIds().size(), "The gallery picture IDs size should match");
        assertTrue(movie.getGalleryPictureIds().containsAll(foundMovie.getGalleryPictureIds()), "The gallery picture IDs should contain all saved IDs");
        assertEquals(movie.getActorIds().size(), foundMovie.getActorIds().size(), "The actor IDs size should match");
        assertTrue(movie.getActorIds().containsAll(foundMovie.getActorIds()), "The actor IDs should contain all saved IDs");
        assertEquals(movie.getGenres().size(), foundMovie.getGenres().size(), "The genres size should match");
        assertTrue(movie.getGenres().containsAll(foundMovie.getGenres()), "The genres should contain all saved genres");
    }

    @Test
    @DisplayName("Test finding all Movies")
    void testFindAll() {
        Movie[] movies = getMoviesForGeneralCrudTests();
        movieRepository.saveAll(Arrays.asList(movies));
        int expectedLength = movies.length + 1;
        List<Movie> foundMovies = movieRepository.findAll();

        assertNotNull(foundMovies, "The found list should not be null");
        assertFalse(foundMovies.isEmpty(), "The found list should not be empty");
        assertEquals(expectedLength, foundMovies.size(), "The size of the list should match the number of movies saved");

        for (Movie a : movies) {
            assertTrue(foundMovies.contains(a), "The found movies should contain all movies saved before");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test updating an Movie")
    void testUpdate(String name,
                    String surname,
                    boolean shouldThrowException) {
        Movie movieBeforeUpdate = new Movie(null, "updatable", "updatable", "", "", 0,
                LocalDate.now(), 0.0, MPAA.G, 0.0, "", "", "",
                List.of(), Set.of(), Set.of());
        movieBeforeUpdate = movieRepository.save(movieBeforeUpdate);
        Movie updatedMovie = new Movie(movieBeforeUpdate.getId(), name, surname, "", "", 0,
                LocalDate.now(), 0.0, MPAA.G, 0.0, "", "", "",
                List.of(), Set.of(), Set.of());

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> movieRepository.save(updatedMovie),
                    "Should have thrown an exception due to duplicate name and surname");
        } else {
            movie = assertDoesNotThrow(() -> movieRepository.save(updatedMovie), "The movie should be updated");
            assertEquals(updatedMovie.getId(), movie.getId(), "The ID should remain the same after update");
            assertEquals(updatedMovie.getName(), movie.getName(), "The name should match");
            assertEquals(updatedMovie.getSurname(), movie.getSurname(), "The surname should match");
            assertEquals(updatedMovie.getDescription(), movie.getDescription(), "The description should match");
            assertEquals(updatedMovie.getDirectedBy(), movie.getDirectedBy(), "The directed by should match");
            assertEquals(updatedMovie.getDuration(), movie.getDuration(), "The duration should match");
            assertEquals(updatedMovie.getReleaseDate(), movie.getReleaseDate(), "The release date should match");
            assertEquals(updatedMovie.getUsersRating(), movie.getUsersRating(), "The users rating should match");
            assertEquals(updatedMovie.getMpaaRating(), movie.getMpaaRating(), "The MPAA rating should match");
            assertEquals(updatedMovie.getImdbRating(), movie.getImdbRating(), "The IMDB rating should match");
            assertEquals(updatedMovie.getMovieCountry(), movie.getMovieCountry(), "The country should match");
            assertEquals(updatedMovie.getWidePictureId(), movie.getWidePictureId(), "The wide picture ID should match");
            assertEquals(updatedMovie.getPosterPictureId(), movie.getPosterPictureId(), "The poster picture ID should match");
            assertEquals(updatedMovie.getGalleryPictureIds().size(), movie.getGalleryPictureIds().size(), "The gallery picture IDs size should match");
            assertTrue(updatedMovie.getGalleryPictureIds().containsAll(movie.getGalleryPictureIds()), "The gallery picture IDs should contain all saved IDs");
            assertEquals(updatedMovie.getActorIds().size(), movie.getActorIds().size(), "The actor IDs size should match");
            assertTrue(updatedMovie.getActorIds().containsAll(movie.getActorIds()), "The actor IDs should contain all saved IDs");
            assertEquals(updatedMovie.getGenres().size(), movie.getGenres().size(), "The genres size should match");
            assertTrue(updatedMovie.getGenres().containsAll(movie.getGenres()), "The genres should contain all saved genres");
        }
    }

    @Test
    @DisplayName("Test save all Movies")
    void testSaveAll() {
        Movie[] movies = getMoviesForGeneralCrudTests();

        List<Movie> savedMovies = movieRepository.saveAll(Arrays.asList(movies));

        assertNotNull(savedMovies, "The saved movies list should not be null");
        assertEquals(movies.length, savedMovies.size(), "The size of the saved movies should match the input list size");
        for (Movie a : savedMovies) {
            assertNotNull(a.getId(), "Each saved movie should have a generated ID");
        }

        List<Movie> foundMovies = movieRepository.findAll();
        int expectedSize = movies.length + 1;
        assertEquals(expectedSize, foundMovies.size(), "The number of movies found should match the saved movies");
        assertTrue(foundMovies.containsAll(savedMovies), "The found movies should match the saved movies");
    }

    @Test
    @DisplayName("Test deleting an Movie")
    void testDelete() {
        movieRepository.delete(movie);

        Optional<Movie> deletedMovieOpt = movieRepository.findById(movie.getId());
        assertFalse(deletedMovieOpt.isPresent(), "The movie should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting an Movie by ID")
    void testDeleteById() {
        movieRepository.deleteById(movie.getId());

        Optional<Movie> deletedMovieOpt = movieRepository.findById(movie.getId());
        assertFalse(deletedMovieOpt.isPresent(), "The movie should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting all Movies")
    void testDeleteAll() {
        Movie[] movies = getMoviesForGeneralCrudTests();
        movieRepository.saveAll(Arrays.asList(movies));

        List<Movie> foundMovies = movieRepository.findAll();
        assertNotNull(foundMovies, "The movies should exist before deleting");
        assertFalse(foundMovies.isEmpty(), "The movies should exist before deleting");

        movieRepository.deleteAll();
        foundMovies = movieRepository.findAll();
        assertNotNull(foundMovies, "The movies should be deleted and movies list should not be null");
        assertTrue(foundMovies.isEmpty(), "The movies should be deleted and movies list should be empty");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByNameTest")
    @DisplayName("Test finding all Movies by name")
    void testFindByName(String name,
                        int expectedSize,
                        int[] expectedMovieIndexes) {
        Movie[] movies = getMoviesForSpecializedTests();
        movieRepository.saveAll(Arrays.asList(movies));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Movie> foundMovies = movieRepository.findByName(name, pageable);

        assertNotNull(foundMovies, "The found movies list should not be null");
        assertEquals(expectedSize, foundMovies.getTotalElements(), "The size of the found movies should match the expected size");
        for (int i : expectedMovieIndexes) {
            assertTrue(foundMovies.getContent().contains(movies[i]), "The found movies should contain all expected movies");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByGenresContainsTest")
    @DisplayName("Test finding all Movies by genres")
    void testFindByGenresContains(List<Genre> genres,
                                  int expectedSize,
                                  int[] expectedMovieIndexes) {
        Movie[] movies = getMoviesForSpecializedTests();
        movieRepository.saveAll(Arrays.asList(movies));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Movie> foundMovies = movieRepository.findByGenresContains(genres, pageable);

        assertNotNull(foundMovies, "The found movies list should not be null");
        assertEquals(expectedSize, foundMovies.getTotalElements(), "The size of the found movies should match the expected size");
        for (int i : expectedMovieIndexes) {
            assertTrue(foundMovies.getContent().contains(movies[i]), "The found movies should contain all expected movies");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByReleaseDateGreaterThanEqualTest")
    @DisplayName("Test finding all Movies by release date after")
    void testFindByReleaseDateGreaterThanEqual(LocalDate releaseDate,
                                               int expectedSize,
                                               int[] expectedMovieIndexes) {
        Movie[] movies = getMoviesForSpecializedTests();
        movieRepository.saveAll(Arrays.asList(movies));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Movie> foundMovies = movieRepository.findByReleaseDateGreaterThanEqual(releaseDate, pageable);

        assertNotNull(foundMovies, "The found movies list should not be null");
        assertEquals(expectedSize, foundMovies.getTotalElements(), "The size of the found movies should match the expected size");
        for (int i : expectedMovieIndexes) {
            assertTrue(foundMovies.getContent().contains(movies[i]), "The found movies should contain all expected movies");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByReleaseDateLessThanEqualTest")
    @DisplayName("Test finding all Movies by release date before")
    void testFindByReleaseDateLessThanEqual(LocalDate releaseDate,
                                            int expectedSize,
                                            int[] expectedMovieIndexes) {
        Movie[] movies = getMoviesForSpecializedTests();
        movieRepository.saveAll(Arrays.asList(movies));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Movie> foundMovies = movieRepository.findByReleaseDateLessThanEqual(releaseDate, pageable);

        assertNotNull(foundMovies, "The found movies list should not be null");
        assertEquals(expectedSize, foundMovies.getTotalElements(), "The size of the found movies should match the expected size");
        for (int i : expectedMovieIndexes) {
            assertTrue(foundMovies.getContent().contains(movies[i]), "The found movies should contain all expected movies");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByReleaseDateBetweenTest")
    @DisplayName("Test finding all Movies by release date between")
    void testFindByReleaseDateBetween(LocalDate from,
                                      LocalDate to,
                                      int expectedSize,
                                      int[] expectedMovieIndexes) {
        Movie[] movies = getMoviesForSpecializedTests();
        movieRepository.saveAll(Arrays.asList(movies));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Movie> foundMovies = movieRepository.findByReleaseDateBetween(from, to, pageable);

        assertNotNull(foundMovies, "The found movies list should not be null");
        assertEquals(expectedSize, foundMovies.getTotalElements(), "The size of the found movies should match the expected size");
        for (int i : expectedMovieIndexes) {
            assertTrue(foundMovies.getContent().contains(movies[i]), "The found movies should contain all expected movies");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByGenresContainsAndReleaseDateGreaterThanEqualTest")
    @DisplayName("Test finding all Movies by genres and release date after")
    void testFindByGenresContainsAndReleaseDateGreaterThanEqual(List<Genre> genres,
                                                                LocalDate releaseDate,
                                                                int expectedSize,
                                                                int[] expectedMovieIndexes) {
        Movie[] movies = getMoviesForSpecializedTests();
        movieRepository.saveAll(Arrays.asList(movies));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Movie> foundMovies = movieRepository.findByGenresContainsAndReleaseDateGreaterThanEqual(genres, releaseDate, pageable);

        assertNotNull(foundMovies, "The found movies list should not be null");
        assertEquals(expectedSize, foundMovies.getTotalElements(), "The size of the found movies should match the expected size");
        for (int i : expectedMovieIndexes) {
            assertTrue(foundMovies.getContent().contains(movies[i]), "The found movies should contain all expected movies");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByGenresContainsAndReleaseDateLessThanEqualTest")
    @DisplayName("Test finding all Movies by genres and release date before")
    void testFindByGenresContainsAndReleaseDateLessThanEqual(List<Genre> genres,
                                                             LocalDate releaseDate,
                                                             int expectedSize,
                                                             int[] expectedMovieIndexes) {
        Movie[] movies = getMoviesForSpecializedTests();
        movieRepository.saveAll(Arrays.asList(movies));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Movie> foundMovies = movieRepository.findByGenresContainsAndReleaseDateLessThanEqual(genres, releaseDate, pageable);

        assertNotNull(foundMovies, "The found movies list should not be null");
        assertEquals(expectedSize, foundMovies.getTotalElements(), "The size of the found movies should match the expected size");
        for (int i : expectedMovieIndexes) {
            assertTrue(foundMovies.getContent().contains(movies[i]), "The found movies should contain all expected movies");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByGenresContainsAndReleaseDateBetweenTest")
    @DisplayName("Test finding all Movies by genres and release date between")
    void testFindByGenresContainsAndReleaseDateBetween(List<Genre> genres,
                                                       LocalDate from,
                                                       LocalDate to,
                                                       int expectedSize,
                                                       int[] expectedMovieIndexes) {
        Movie[] movies = getMoviesForSpecializedTests();
        movieRepository.saveAll(Arrays.asList(movies));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Movie> foundMovies = movieRepository.findByGenresContainsAndReleaseDateBetween(genres, from, to, pageable);

        assertNotNull(foundMovies, "The found movies list should not be null");
        assertEquals(expectedSize, foundMovies.getTotalElements(), "The size of the found movies should match the expected size");
        for (int i : expectedMovieIndexes) {
            assertTrue(foundMovies.getContent().contains(movies[i]), "The found movies should contain all expected movies");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByActorIdsContainsTest")
    @DisplayName("Test finding all Movies by actor ids contains")
    void testFindByActorIdsContains(String actorId,
                                    int expectedSize,
                                    int[] expectedMovieIndexes) {
        Movie[] movies = getMoviesForSpecializedTests();
        movieRepository.saveAll(Arrays.asList(movies));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Movie> foundMovies = movieRepository.findByActorIdsContains(actorId, pageable);

        assertNotNull(foundMovies, "The found movies list should not be null");
        assertEquals(expectedSize, foundMovies.getTotalElements(), "The size of the found movies should match the expected size");
        for (int i : expectedMovieIndexes) {
            assertTrue(foundMovies.getContent().contains(movies[i]), "The found movies should contain all expected movies");
        }
    }

    private Movie initMovie() {
        return Movie.builder()
                .name("test")
                .surname("test")
                .description("test")
                .directedBy("test")
                .duration(1)
                .releaseDate(LocalDate.now())
                .usersRating(1.0)
                .mpaaRating(MPAA.G)
                .imdbRating(1.0)
                .movieCountry("test")
                .widePictureId("test")
                .posterPictureId("test")
                .galleryPictureIds(List.of("test"))
                .actorIds(List.of("test"))
                .genres(List.of(Genre.ACTION))
                .build();
    }

    private Movie[] getMoviesForGeneralCrudTests() {
        Movie unique1 = new Movie(null, "unique1", "unique1", "", "", 0,
                LocalDate.now(), 0.0, MPAA.G, 0.0, "", "", "",
                List.of(), Set.of(), Set.of());
        Movie unique2 = new Movie(null, "unique2", "unique2", "", "", 0,
                LocalDate.now(), 0.0, MPAA.G, 0.0, "", "", "",
                List.of(), Set.of(), Set.of());
        Movie unique3 = new Movie(null, "unique3", "unique3", "", "", 0,
                LocalDate.now(), 0.0, MPAA.G, 0.0, "", "", "",
                List.of(), Set.of(), Set.of());

        return new Movie[]{unique1, unique2, unique3};
    }

    private Movie[] getMoviesForSpecializedTests() {
        Movie movie1 = new Movie(null, "unique1", "unique4", "", "", 0,
                LocalDate.of(1, 1, 1), 0.0, MPAA.G, 0.0, "",
                "", "", List.of(), Set.of("1", "2", "3"), Set.of(Genre.ACTION, Genre.DRAMA));
        Movie movie2 = new Movie(null, "unique2", "unique3", "", "", 0,
                LocalDate.of(1, 1, 2), 0.0, MPAA.G, 0.0, "",
                "", "", List.of(), Set.of("1"), Set.of(Genre.DETECTIVE, Genre.DRAMA));
        Movie movie3 = new Movie(null, "unique3", "unique2", "", "", 0,
                LocalDate.of(1, 1, 3), 0.0, MPAA.G, 0.0, "",
                "", "", List.of(), Set.of("3", "4"), Set.of(Genre.CARTOON, Genre.COMEDY));
        Movie movie4 = new Movie(null, "unique4", "unique1", "", "", 0,
                LocalDate.of(1, 1, 4), 0.0, MPAA.G, 0.0, "",
                "", "", List.of(), Set.of("4", "5", "1"), Set.of(Genre.DRAMA));

        return new Movie[]{movie1, movie2, movie3, movie4};
    }

    /**
     * Provides arguments for testing create and update methods in {@link MovieRepository}.
     * The arguments are:
     * <ul>
     *     <li>name - the name of the movie</li>
     *     <li>surname - the surname of the movie</li>
     *     <li>shouldThrowException - whether an exception should be thrown due to duplicate name and surname</li>
     * </ul>
     */
    private static Stream<Arguments> provideArgumentsForCreateAndUpdateTests() {
        return Stream.of(
                Arguments.of("Unique", "Unique", false),
                Arguments.of(movie.getName(), "Unique", false),
                Arguments.of("Unique", movie.getSurname(), false),
                Arguments.of(movie.getName(), movie.getSurname(), true)
        );
    }

    /**
     * Provides arguments for testing the findByName method in {@link MovieRepository}.
     * The arguments are:
     * <ul>
     *     <li>name - the name pattern to search for</li>
     *     <li>expectedSize - the expected number of movies matching the pattern</li>
     *     <li>expectedMovieIndexes - the indexes of expected movies in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByNameTest() {
        return Stream.of(
                Arguments.of("u", 4, new int[]{0, 1, 2, 3}),
                Arguments.of("UNIQUE", 4, new int[]{0, 1, 2, 3}),
                Arguments.of("1", 2, new int[]{0, 3})
        );
    }


    /**
     * Provides arguments for testing the findByGenres method in {@link MovieRepository}.
     * The arguments are:
     * <ul>
     *     <li>genres - the list of genres to search for</li>
     *     <li>expectedSize - the expected number of movies matching the genres</li>
     *     <li>expectedMovieIndexes - the indexes of expected movies in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByGenresContainsTest() {
        return Stream.of(
                Arguments.of(List.of(Genre.DOCUMENTARY), 0, new int[]{}),
                Arguments.of(List.of(Genre.DRAMA), 3, new int[]{0, 1, 3}),
                Arguments.of(List.of(Genre.COMEDY), 1, new int[]{2}),
                Arguments.of(List.of(Genre.ACTION, Genre.DETECTIVE), 0, new int[]{})
        );
    }

    /**
     * Provides arguments for testing the findByReleaseDateAfter method in {@link MovieRepository}.
     * The arguments are:
     * <ul>
     *     <li>releaseDate - the release date after which to search for movies</li>
     *     <li>expectedSize - the expected number of movies with a release date after the specified date</li>
     *     <li>expectedMovieIndexes - the indexes of expected movies in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByReleaseDateGreaterThanEqualTest() {
        return Stream.of(
                Arguments.of(LocalDate.of(1, 1, 1), 5, new int[]{1, 2, 3}),
                Arguments.of(LocalDate.of(1, 1, 4), 2, new int[]{})
        );
    }

    /**
     * Provides arguments for testing the findByReleaseDateBefore method in {@link MovieRepository}.
     * The arguments are:
     * <ul>
     *     <li>releaseDate - the release date before which to search for movies</li>
     *     <li>expectedSize - the expected number of movies with a release date before the specified date</li>
     *     <li>expectedMovieIndexes - the indexes of expected movies in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByReleaseDateLessThanEqualTest() {
        return Stream.of(
                Arguments.of(LocalDate.of(1, 1, 1), 1, new int[]{}),
                Arguments.of(LocalDate.of(1, 1, 4), 4, new int[]{0, 1, 2}),
                Arguments.of(LocalDate.of(1, 1, 5), 4, new int[]{0, 1, 2, 3})
        );
    }

    /**
     * Provides arguments for testing the findByReleaseDateBetween method in {@link MovieRepository}.
     * The arguments are:
     * <ul>
     *     <li>from - the start date of the range to search for movies</li>
     *     <li>to - the end date of the range to search for movies</li>
     *     <li>expectedSize - the expected number of movies with a release date within the specified range</li>
     *     <li>expectedMovieIndexes - the indexes of expected movies in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByReleaseDateBetweenTest() {
        return Stream.of(
                Arguments.of(LocalDate.of(1, 1, 1),
                        LocalDate.of(1, 1, 1), 1, new int[]{0}),
                Arguments.of(LocalDate.of(1, 1, 1),
                        LocalDate.of(1, 1, 4), 4, new int[]{0, 1, 2, 3}),
                Arguments.of(LocalDate.of(1, 1, 1),
                        LocalDate.of(1, 1, 5), 4, new int[]{0, 1, 2, 3})
        );
    }

    /**
     * Provides arguments for testing the findByGenresAndReleaseDateAfter method in {@link MovieRepository}.
     * The arguments are:
     * <ul>
     *     <li>genres - the list of genres to search for</li>
     *     <li>releaseDate - the release date after which to search for movies</li>
     *     <li>expectedSize - the expected number of movies matching the genres and release date condition</li>
     *     <li>expectedMovieIndexes - the indexes of expected movies in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByGenresContainsAndReleaseDateGreaterThanEqualTest() {
        return Stream.of(
                Arguments.of(List.of(Genre.ACTION), LocalDate.of(1, 1, 1), 2, new int[]{0}),
                Arguments.of(List.of(Genre.ACTION, Genre.THRILLER), LocalDate.of(1, 1, 1), 0, new int[]{}),
                Arguments.of(List.of(Genre.COMEDY, Genre.DRAMA), LocalDate.of(1, 1, 2), 0, new int[]{}),
                Arguments.of(List.of(Genre.CARTOON), LocalDate.of(1, 1, 3), 1, new int[]{2}),
                Arguments.of(List.of(Genre.CARTOON), LocalDate.of(1, 1, 4), 0, new int[]{}),
                Arguments.of(List.of(Genre.ACTION, Genre.DRAMA), LocalDate.of(1, 1, 4), 0, new int[]{})
        );
    }

    /**
     * Provides arguments for testing the findByGenresAndReleaseDateBefore method in {@link MovieRepository}.
     * The arguments are:
     * <ul>
     *     <li>genres - the list of genres to search for</li>
     *     <li>releaseDate - the release date before which to search for movies</li>
     *     <li>expectedSize - the expected number of movies matching the genres and release date condition</li>
     *     <li>expectedMovieIndexes - the indexes of expected movies in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByGenresContainsAndReleaseDateLessThanEqualTest() {
        return Stream.of(
                Arguments.of(List.of(Genre.ACTION), LocalDate.of(1, 1, 1), 1, new int[]{0}),
                Arguments.of(List.of(Genre.COMEDY, Genre.DRAMA), LocalDate.of(1, 1, 2), 0, new int[]{}),
                Arguments.of(List.of(Genre.CARTOON), LocalDate.of(1, 1, 2), 0, new int[]{}),
                Arguments.of(List.of(Genre.CARTOON), LocalDate.of(1, 1, 3), 1, new int[]{2}),
                Arguments.of(List.of(Genre.ACTION, Genre.DRAMA), LocalDate.of(1, 1, 4), 1, new int[]{0})
        );
    }

    /**
     * Provides arguments for testing the findByGenresAndReleaseDateBetween method in {@link MovieRepository}.
     * The arguments are:
     * <ul>
     *     <li>genres - the list of genres to search for</li>
     *     <li>from the start date of the range to match against the movie's release date</li>
     *     <li>to the end date of the range to match against the movie's release date</li>
     *     <li>expectedSize - the expected number of movies matching the genres and release date condition</li>
     *     <li>expectedMovieIndexes - the indexes of expected movies in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByGenresContainsAndReleaseDateBetweenTest() {
        return Stream.of(
                Arguments.of(List.of(Genre.ACTION), LocalDate.of(1, 1, 1),
                        LocalDate.of(1, 1, 1), 1, new int[]{0}),
                Arguments.of(List.of(Genre.COMEDY, Genre.DRAMA), LocalDate.of(1, 1, 2),
                        LocalDate.of(1, 1, 4), 0, new int[]{}),
                Arguments.of(List.of(Genre.CARTOON), LocalDate.of(1, 1, 1),
                        LocalDate.of(1, 1, 5), 1, new int[]{2})
        );
    }

    /**
     * Provides arguments for testing the findByActorIdsContains method in {@link MovieRepository}.
     * The arguments are:
     * <ul>
     *     <li>actorId - the ID of the actor to search for in movies</li>
     *     <li>expectedSize - the expected number of movies containing the actor with the specified ID</li>
     *     <li>expectedMovieIndexes - the indexes of expected movies in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByActorIdsContainsTest() {
        return Stream.of(
                Arguments.of("0", 0, new int[]{}),
                Arguments.of("1", 3, new int[]{0, 1, 3}),
                Arguments.of("2", 1, new int[]{0}),
                Arguments.of("3", 2, new int[]{0, 2})
        );
    }

}