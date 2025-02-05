package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Seance entity class tests")
class SeanceTest {

    private static String id;
    private static LocalTime startSeance;
    private static LocalTime endSeance;
    private static LocalDate dateFrom;
    private static LocalDate dateTo;
    private static Double ticketPrice;
    private static String hallId;
    private static String movieId;
    private static Language seanceLang;
    private static Set<Day> days;

    private Seance seance;

    @BeforeAll
    static void beforeAll() {
        SeanceTest.id = "1";
        SeanceTest.startSeance = LocalTime.of(1, 1, 1);
        SeanceTest.endSeance = LocalTime.of(1, 1, 1);
        SeanceTest.dateFrom = LocalDate.of(1, 1, 1);
        SeanceTest.dateTo = LocalDate.of(1, 1, 1);
        SeanceTest.ticketPrice = 1.0;
        SeanceTest.hallId = "1";
        SeanceTest.movieId = "1";
        SeanceTest.seanceLang = Language.UA;
        SeanceTest.days = Collections.emptySet();
    }

    @BeforeEach
    void setUp() {
        seance = initSeance();
    }

    @Test
    @DisplayName("Test all-args constructor initializes fields correctly")
    void testAllArgsConstructor() {
        String idLocal = "100";
        LocalTime startSeanceLocal = LocalTime.of(2, 1, 1);
        LocalTime endSeanceLocal = LocalTime.of(2, 1, 1);
        LocalDate dateFromLocal = LocalDate.of(100, 1, 1);
        LocalDate dateToLocal = LocalDate.of(100, 1, 1);
        Double ticketPriceLocal = 100.0;
        String hallIdLocal = "100";
        String movieIdLocal = "100";
        Language seanceLangLocal = Language.PL;
        Set<Day> daysLocal = Collections.emptySet();

        seance = new Seance(
                idLocal,
                startSeanceLocal,
                endSeanceLocal,
                dateFromLocal,
                dateToLocal,
                ticketPriceLocal,
                hallIdLocal,
                movieIdLocal,
                seanceLangLocal,
                daysLocal
        );

        assertEquals(idLocal, seance.getId(), "Id does not match the expected value");
        assertEquals(startSeanceLocal, seance.getStartSeance(), "Start seance does not match the expected value");
        assertEquals(endSeanceLocal, seance.getEndSeance(), "End seance does not match the expected value");
        assertEquals(dateFromLocal, seance.getDateFrom(), "Date from does not match the expected value");
        assertEquals(dateToLocal, seance.getDateTo(), "Date to does not match the expected value");
        assertEquals(ticketPriceLocal, seance.getTicketPrice(), "Ticket price does not match the expected value");
        assertEquals(hallIdLocal, seance.getHallId(), "Hall id does not match the expected value");
        assertEquals(movieIdLocal, seance.getMovieId(), "MovieId does not match the expected value");
        assertEquals(seanceLangLocal, seance.getSeanceLanguage(), "Seance lang does not match the expected value");
        assertEquals(daysLocal, seance.getDays(), "Days does not match the expected value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        seance.setId(id);

        assertEquals(id, seance.getId(), "Id does not match the value set");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals(SeanceTest.id, seance.getId(), "Getter for id returned an unexpected value");
        assertEquals(SeanceTest.startSeance, seance.getStartSeance(), "Getter for start seance returned an unexpected value");
        assertEquals(SeanceTest.endSeance, seance.getEndSeance(), "Getter for end seance returned an unexpected value");
        assertEquals(SeanceTest.dateFrom, seance.getDateFrom(), "Getter for dateFrom returned an unexpected value");
        assertEquals(SeanceTest.dateTo, seance.getDateTo(), "Getter for dateTo returned an unexpected value");
        assertEquals(SeanceTest.ticketPrice, seance.getTicketPrice(), "Getter for ticketPrice returned an unexpected value");
        assertEquals(SeanceTest.hallId, seance.getHallId(), "Getter for hallId returned an unexpected value");
        assertEquals(SeanceTest.movieId, seance.getMovieId(), "Getter for movieId returned an unexpected value");
        assertEquals(SeanceTest.seanceLang, seance.getSeanceLanguage(), "Getter for seanceLang returned an unexpected value");
        assertEquals(SeanceTest.days, seance.getDays(), "Getter for days returned an unexpected value");
    }

    private Seance initSeance() {
        return Seance.builder()
                .id(SeanceTest.id)
                .startSeance(SeanceTest.startSeance)
                .endSeance(SeanceTest.endSeance)
                .dateFrom(SeanceTest.dateFrom)
                .dateTo(SeanceTest.dateTo)
                .ticketPrice(SeanceTest.ticketPrice)
                .hallId(SeanceTest.hallId)
                .movieId(SeanceTest.movieId)
                .seanceLanguage(SeanceTest.seanceLang)
                .days(SeanceTest.days)
                .build();
    }

    @Nested
    @DisplayName("SeanceBuilder nested class tests")
    class SeanceBuilderTest {

        @Test
        @DisplayName("Test builder initializes fields correctly")
        void testBuilderInitialization() {
            String idLocal = "1000";
            LocalTime startSeanceLocal = LocalTime.of(3, 1, 1);
            LocalTime endSeanceLocal = LocalTime.of(3, 1, 1);
            LocalDate dateFromLocal = LocalDate.of(1000, 1, 1);
            LocalDate dateToLocal = LocalDate.of(1000, 1, 1);
            Double ticketPriceLocal = 1000.0;
            String hallIdLocal = "1000";
            String movieIdLocal = "1000";
            Language seanceLangLocal = Language.EN;
            Set<Day> daysLocal = Collections.emptySet();

            seance = Seance.builder()
                    .id(idLocal)
                    .startSeance(startSeanceLocal)
                    .endSeance(endSeanceLocal)
                    .dateFrom(dateFromLocal)
                    .dateTo(dateToLocal)
                    .ticketPrice(ticketPriceLocal)
                    .hallId(hallIdLocal)
                    .movieId(movieIdLocal)
                    .seanceLanguage(seanceLangLocal)
                    .days(daysLocal)
                    .build();

            assertEquals(idLocal, seance.getId(), "Id does not match the expected value");
            assertEquals(startSeanceLocal, seance.getStartSeance(), "Start seance does not match the expected value");
            assertEquals(endSeanceLocal, seance.getEndSeance(), "End seance does not match the expected value");
            assertEquals(dateFromLocal, seance.getDateFrom(), "Date from does not match the expected value");
            assertEquals(dateToLocal, seance.getDateTo(), "Date to does not match the expected value");
            assertEquals(ticketPriceLocal, seance.getTicketPrice(), "Ticket price does not match the expected value");
            assertEquals(hallIdLocal, seance.getHallId(), "Hall id does not match the expected value");
            assertEquals(movieIdLocal, seance.getMovieId(), "MovieId does not match the expected value");
            assertEquals(seanceLangLocal, seance.getSeanceLanguage(), "Seance lang does not match the expected value");
            assertEquals(daysLocal, seance.getDays(), "Days does not match the expected value");
        }

        @Test
        @DisplayName("Test builder defaults to empty collections if no values are added")
        void testBuilderEmptyCollections() {
            Seance actual = Seance.builder().build();
            Set<Day> daysActual = actual.getDays();

            assertNotNull(daysActual, "Days should not be null");
            assertTrue(daysActual.isEmpty(), "Days should be empty");
        }

        @Test
        @DisplayName("Test @Singular fields handle multiple values correctly")
        void testSingularFields_whenAdd() {
            Set<Day> daysExpected = Set.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY);

            Seance actual = Seance.builder()
                    .day(Day.MONDAY)
                    .day(Day.TUESDAY)
                    .day(Day.WEDNESDAY)
                    .build();

            assertEquals(daysExpected, actual.getDays(), "Days do not match the expected values");
        }

        @Test
        @DisplayName("Test builder defaults to fields if no values are added")
        void testBuilderDefaultFields() {
            seance = Seance.builder().build();
            Language languageExpected = Language.UA;

            assertEquals(languageExpected, seance.getSeanceLanguage(), "Seance language does not match the expected value");
        }

    }

}