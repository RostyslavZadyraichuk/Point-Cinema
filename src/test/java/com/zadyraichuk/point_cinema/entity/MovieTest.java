package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Movie entity class tests")
class MovieTest {

    private static String id;
    private static String name;
    private static String surname;
    private static String description;
    private static String directedBy;
    private static Integer duration;
    private static LocalDate releaseDate;
    private static Double usersRating;
    private static MPAA mpaaRating;
    private static Double imdbRating;
    private static String movieCountry;
    private static String widePictureId;
    private static String posterPictureId;
    private static List<String> galleryPictureIds;
    private static List<String> actorIds;
    private static List<Genre> genres;

    private Movie movie;

    @BeforeAll
    static void beforeAll() {
        MovieTest.id = "1";
        MovieTest.name = "test";
        MovieTest.surname = "test";
        MovieTest.description = "test";
        MovieTest.directedBy = "test";
        MovieTest.duration = 1;
        MovieTest.releaseDate = LocalDate.of(1, 1, 1);
        MovieTest.usersRating = 1.0;
        MovieTest.mpaaRating = MPAA.G;
        MovieTest.imdbRating = 1.0;
        MovieTest.movieCountry = "test";
        MovieTest.widePictureId = "test";
        MovieTest.posterPictureId = "test";
        MovieTest.galleryPictureIds = Collections.emptyList();
        MovieTest.actorIds = Collections.emptyList();
        MovieTest.genres = Collections.emptyList();
    }

    @BeforeEach
    void setUp() {
        movie = initMovie();
    }

    @Test
    @DisplayName("Test all-args constructor initializes fields correctly")
    void testAllArgsConstructor() {
        String idLocal = "100";
        String nameLocal = "100";
        String surnameLocal = "100";
        String descriptionLocal = "100";
        String directedByLocal = "100";
        Integer durationLocal = 100;
        LocalDate releaseDateLocal = LocalDate.of(100, 1, 1);
        Double usersRatingLocal = 100.0;
        MPAA mpaaRatingLocal = MPAA.PG;
        Double imdbRatingLocal = 100.0;
        String movieCountryLocal = "100";
        String widePictureIdLocal = "100";
        String posterPictureIdLocal = "100";
        List<String> galleryPictureIdsLocal = new ArrayList<>(0);
        List<String> actorIdsLocal = new ArrayList<>(0);
        List<Genre> genresLocal = new ArrayList<>(0);

        movie = new Movie(
                idLocal,
                nameLocal,
                surnameLocal,
                descriptionLocal,
                directedByLocal,
                durationLocal,
                releaseDateLocal,
                usersRatingLocal,
                mpaaRatingLocal,
                imdbRatingLocal,
                movieCountryLocal,
                widePictureIdLocal,
                posterPictureIdLocal,
                galleryPictureIdsLocal,
                actorIdsLocal,
                genresLocal
        );

        assertEquals(idLocal, movie.getId(), "Id does not match the expected value");
        assertEquals(nameLocal, movie.getName(), "Name does not match the expected value");
        assertEquals(surnameLocal, movie.getSurname(), "Surname does not match the expected value");
        assertEquals(descriptionLocal, movie.getDescription(), "Description does not match the expected value");
        assertEquals(directedByLocal, movie.getDirectedBy(), "Directed by does not match the expected value");
        assertEquals(durationLocal, movie.getDuration(), "Duration does not match the expected value");
        assertEquals(releaseDateLocal, movie.getReleaseDate(), "Release date does not match the expected value");
        assertEquals(usersRatingLocal, movie.getUsersRating(), "Users rating does not match the expected value");
        assertEquals(mpaaRatingLocal, movie.getMpaaRating(), "MPAA rating does not match the expected value");
        assertEquals(imdbRatingLocal, movie.getImdbRating(), "IMDB rating does not match the expected value");
        assertEquals(movieCountryLocal, movie.getMovieCountry(), "Country does not match the expected value");
        assertEquals(widePictureIdLocal, movie.getWidePictureId(), "Picture id does not match the expected value");
        assertEquals(posterPictureIdLocal, movie.getPosterPictureId(), "Picture id does not match the expected value");
        assertEquals(galleryPictureIdsLocal, movie.getGalleryPictureIds(), "Picture id does not match the expected value");
        assertEquals(actorIdsLocal, movie.getActorIds(), "Actor id does not match the expected value");
        assertEquals(genresLocal, movie.getGenres(), "Genres does not match the expected value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        movie.setId(id);

        assertEquals(id, movie.getId(), "Id does not match the value set");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals(MovieTest.id, movie.getId(), "Getter for id returned an unexpected value");
        assertEquals(MovieTest.name, movie.getName(), "Name does not match the expected value");
        assertEquals(MovieTest.surname, movie.getSurname(), "Surname does not match the expected value");
        assertEquals(MovieTest.description, movie.getDescription(), "Description does not match the expected value");
        assertEquals(MovieTest.directedBy, movie.getDirectedBy(), "Directed by does not match the expected value");
        assertEquals(MovieTest.duration, movie.getDuration(), "Duration does not match the expected value");
        assertEquals(MovieTest.releaseDate, movie.getReleaseDate(), "Release date does not match the expected value");
        assertEquals(MovieTest.usersRating, movie.getUsersRating(), "Users rating does not match the expected value");
        assertEquals(MovieTest.mpaaRating, movie.getMpaaRating(), "MPAA rating does not match the expected value");
        assertEquals(MovieTest.imdbRating, movie.getImdbRating(), "IMDB rating does not match the expected value");
        assertEquals(MovieTest.movieCountry, movie.getMovieCountry(), "Country does not match the expected value");
        assertEquals(MovieTest.widePictureId, movie.getWidePictureId(), "Picture id does not match the expected value");
        assertEquals(MovieTest.posterPictureId, movie.getPosterPictureId(), "Picture id does not match the expected value");
        assertEquals(MovieTest.galleryPictureIds, movie.getGalleryPictureIds(), "Picture id does not match the expected value");
        assertEquals(MovieTest.actorIds, movie.getActorIds(), "Actor id does not match the expected value");
        assertEquals(MovieTest.genres, movie.getGenres(), "Genres does not match the expected value");
    }

    private Movie initMovie() {
        return Movie.builder()
                .id(MovieTest.id)
                .name(MovieTest.name)
                .surname(MovieTest.surname)
                .description(MovieTest.description)
                .directedBy(MovieTest.directedBy)
                .duration(MovieTest.duration)
                .releaseDate(MovieTest.releaseDate)
                .usersRating(MovieTest.usersRating)
                .mpaaRating(MovieTest.mpaaRating)
                .imdbRating(MovieTest.imdbRating)
                .movieCountry(MovieTest.movieCountry)
                .widePictureId(MovieTest.widePictureId)
                .posterPictureId(MovieTest.posterPictureId)
                .galleryPictureIds(MovieTest.galleryPictureIds)
                .actorIds(MovieTest.actorIds)
                .genres(MovieTest.genres)
                .build();
    }

    @Nested
    @DisplayName("MovieBuilder nested class tests")
    class MovieBuilderTest {

        @Test
        @DisplayName("Test builder initializes fields correctly")
        void testBuilderInitialization() {
            String idLocal = "1000";
            String nameLocal = "1000";
            String surnameLocal = "1000";
            String descriptionLocal = "1000";
            String directedByLocal = "1000";
            Integer durationLocal = 1000;
            LocalDate releaseDateLocal = LocalDate.of(1000, 1, 1);
            Double usersRatingLocal = 1000.0;
            MPAA mpaaRatingLocal = MPAA.PG;
            Double imdbRatingLocal = 1000.0;
            String movieCountryLocal = "1000";
            String widePictureIdLocal = "1000";
            String posterPictureIdLocal = "1000";
            List<String> galleryPictureIdsLocal = new ArrayList<>(0);
            List<String> actorIdsLocal = new ArrayList<>(0);
            List<Genre> genresLocal = new ArrayList<>(0);

            movie = Movie.builder()
                    .id(idLocal)
                    .name(nameLocal)
                    .surname(surnameLocal)
                    .description(descriptionLocal)
                    .directedBy(directedByLocal)
                    .duration(durationLocal)
                    .releaseDate(releaseDateLocal)
                    .usersRating(usersRatingLocal)
                    .mpaaRating(mpaaRatingLocal)
                    .imdbRating(imdbRatingLocal)
                    .movieCountry(movieCountryLocal)
                    .widePictureId(widePictureIdLocal)
                    .posterPictureId(posterPictureIdLocal)
                    .galleryPictureIds(galleryPictureIdsLocal)
                    .actorIds(actorIdsLocal)
                    .genres(genresLocal)
                    .build();

            assertEquals(idLocal, movie.getId(), "Id does not match the expected value");
            assertEquals(nameLocal, movie.getName(), "Name does not match the expected value");
            assertEquals(surnameLocal, movie.getSurname(), "Surname does not match the expected value");
            assertEquals(descriptionLocal, movie.getDescription(), "Description does not match the expected value");
            assertEquals(directedByLocal, movie.getDirectedBy(), "Directed by does not match the expected value");
            assertEquals(durationLocal, movie.getDuration(), "Duration does not match the expected value");
            assertEquals(releaseDateLocal, movie.getReleaseDate(), "Release date does not match the expected value");
            assertEquals(usersRatingLocal, movie.getUsersRating(), "Users rating does not match the expected value");
            assertEquals(mpaaRatingLocal, movie.getMpaaRating(), "MPAA rating does not match the expected value");
            assertEquals(imdbRatingLocal, movie.getImdbRating(), "IMDB rating does not match the expected value");
            assertEquals(movieCountryLocal, movie.getMovieCountry(), "Country does not match the expected value");
            assertEquals(widePictureIdLocal, movie.getWidePictureId(), "Picture id does not match the expected value");
            assertEquals(posterPictureIdLocal, movie.getPosterPictureId(), "Picture id does not match the expected value");
            assertEquals(galleryPictureIdsLocal, movie.getGalleryPictureIds(), "Picture id does not match the expected value");
            assertEquals(actorIdsLocal, movie.getActorIds(), "Actor id does not match the expected value");
            assertEquals(genresLocal, movie.getGenres(), "Genres does not match the expected value");
        }

        @Test
        @DisplayName("Test builder defaults to empty collections if no values are added")
        void testBuilderEmptyCollections() {
            Movie actual = Movie.builder().build();
            List<String> galleryPictureIdsActual = actual.getGalleryPictureIds();
            List<String> actorIdsActual = actual.getActorIds();
            List<Genre> genresActual = actual.getGenres();

            assertNotNull(galleryPictureIdsActual, "Gallery picture ids should not be null");
            assertTrue(galleryPictureIdsActual.isEmpty(), "Gallery picture ids should be empty");
            assertNotNull(actorIdsActual, "Actor ids should not be null");
            assertTrue(actorIdsActual.isEmpty(), "Actor ids should be empty");
            assertNotNull(genresActual, "Genres should not be null");
            assertTrue(genresActual.isEmpty(), "Genres should be empty");
        }

        @Test
        @DisplayName("Test @Singular fields handle multiple values correctly")
        void testSingularFields_whenAdd() {
            List<String> galleryPictureIdsExpected = List.of("gallery1", "gallery2");
            List<String> actorIdsExpected = List.of("actor1", "actor2");
            List<Genre> genresExpected = List.of(Genre.ADVENTURE, Genre.DETECTIVE);

            Movie actual = Movie.builder()
                    .galleryPictureId("gallery1")
                    .galleryPictureId("gallery2")
                    .actorId("actor1")
                    .actorId("actor2")
                    .genre(Genre.ADVENTURE)
                    .genre(Genre.DETECTIVE)
                    .build();

            assertEquals(galleryPictureIdsExpected, actual.getGalleryPictureIds(), "Gallery picture ids do not match the expected values");
            assertEquals(actorIdsExpected, actual.getActorIds(), "Actor ids do not match the expected values");
            assertEquals(genresExpected, actual.getGenres(), "Genres do not match the expected values");
        }

        @Test
        @DisplayName("Test builder defaults to fields if no values are added")
        void testBuilderDefaultFields() {
            movie = Movie.builder().build();

            assertEquals(0, movie.getUsersRating(), "Users rating does not match the expected value");
            assertEquals(0, movie.getImdbRating(), "IMDB rating does not match the expected value");
        }

    }

}