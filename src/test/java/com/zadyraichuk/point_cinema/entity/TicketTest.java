package com.zadyraichuk.point_cinema.entity;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Ticket entity class tests")
class TicketTest {

    private static String id;
    private static LocalDate date;
    private static Integer placeRow;
    private static Integer placeSeat;
    private static String seanceId;
    private static String userId;
    private static Boolean paymentStatus;

    private Ticket ticket;

    @BeforeAll
    static void beforeAll() {
        TicketTest.id = "1";
        TicketTest.date = LocalDate.of(1, 1, 1);
        TicketTest.placeRow = 1;
        TicketTest.placeSeat = 1;
        TicketTest.seanceId = "1";
        TicketTest.userId = "1";
        TicketTest.paymentStatus = false;
    }

    @BeforeEach
    void setUp() {
        ticket = initTicket();
    }

    @Test
    @DisplayName("Test all-args constructor initializes fields correctly")
    void testAllArgsConstructor() {
        String idLocal = "100";
        LocalDate dateLocal = LocalDate.of(100, 1, 1);
        Integer placeRowLocal = 100;
        Integer placeSeatLocal = 100;
        String seanceIdLocal = "100";
        String userIdLocal = "100";
        Boolean paymentStatusLocal = true;

        ticket = new Ticket(
                idLocal,
                dateLocal,
                placeRowLocal,
                placeSeatLocal,
                seanceIdLocal,
                userIdLocal,
                paymentStatusLocal
        );

        assertEquals(idLocal, ticket.getId(), "Id does not match the expected value");
        assertEquals(dateLocal, ticket.getDate(), "Date does not match the expected value");
        assertEquals(placeRowLocal, ticket.getPlaceRow(), "Row does not match the expected value");
        assertEquals(placeSeatLocal, ticket.getPlaceSeat(), "Seat does not match the expected value");
        assertEquals(seanceIdLocal, ticket.getSeanceId(), "Id does not match the expected value");
        assertEquals(userIdLocal, ticket.getUserId(), "User does not match the expected value");
        assertEquals(paymentStatusLocal, ticket.getPaymentStatus(), "Payment status does not match the expected value");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "10", "100"})
    @DisplayName("Test setId method with multiple values")
    void testSetId(String id) {
        ticket.setId(id);

        assertEquals(id, ticket.getId(), "Id does not match the value set");
    }

    @Test
    @DisplayName("Test getter methods for all fields")
    void testGetterMethods() {
        assertEquals(TicketTest.id, ticket.getId(), "Getter for id returned an unexpected value");
        assertEquals(TicketTest.date, ticket.getDate(), "Getter for Date returned an unexpected value");
        assertEquals(TicketTest.placeRow, ticket.getPlaceRow(), "Getter for Row returned an unexpected value");
        assertEquals(TicketTest.placeSeat, ticket.getPlaceSeat(), "Getter for Seat returned an unexpected value");
        assertEquals(TicketTest.seanceId, ticket.getSeanceId(), "Getter for Id returned an unexpected value");
        assertEquals(TicketTest.userId, ticket.getUserId(), "Getter for User returned an unexpected value");
        assertEquals(TicketTest.paymentStatus, ticket.getPaymentStatus(), "Getter for Payment status returned an unexpected value");
    }

    private Ticket initTicket() {
        return Ticket.builder()
                .id(TicketTest.id)
                .date(TicketTest.date)
                .placeRow(TicketTest.placeRow)
                .placeSeat(TicketTest.placeSeat)
                .seanceId(TicketTest.seanceId)
                .userId(TicketTest.userId)
                .paymentStatus(TicketTest.paymentStatus)
                .build();
    }

    @Nested
    @DisplayName("TicketBuilder nested class tests")
    class TicketBuilderTest {

        @Test
        @DisplayName("Test builder initializes fields correctly")
        void testBuilderInitialization() {
            String idLocal = "1000";
            LocalDate dateLocal = LocalDate.of(1000, 1, 1);
            Integer placeRowLocal = 1000;
            Integer placeSeatLocal = 1000;
            String seanceIdLocal = "1000";
            String userIdLocal = "1000";
            Boolean paymentStatusLocal = true;

            ticket = Ticket.builder()
                    .id(idLocal)
                    .date(dateLocal)
                    .placeRow(placeRowLocal)
                    .placeSeat(placeSeatLocal)
                    .seanceId(seanceIdLocal)
                    .userId(userIdLocal)
                    .paymentStatus(paymentStatusLocal)
                    .build();

            assertEquals(idLocal, ticket.getId(), "Id does not match the expected value");
            assertEquals(dateLocal, ticket.getDate(), "Date does not match the expected value");
            assertEquals(placeRowLocal, ticket.getPlaceRow(), "Row does not match the expected value");
            assertEquals(placeSeatLocal, ticket.getPlaceSeat(), "Seat does not match the expected value");
            assertEquals(seanceIdLocal, ticket.getSeanceId(), "Id does not match the expected value");
            assertEquals(userIdLocal, ticket.getUserId(), "User does not match the expected value");
            assertEquals(paymentStatusLocal, ticket.getPaymentStatus(), "Payment status does not match the expected value");
        }

        @Test
        @DisplayName("Test builder defaults to payment status and date if no values are added")
        void testBuilderDefaultPaymentStatus() {
            ticket = Ticket.builder().build();
            LocalDate actualDate = ticket.getDate();
            boolean actualPaymentStatus = ticket.getPaymentStatus();

            assertEquals(LocalDate.now(), actualDate, "Date should be today by default");
            assertFalse(actualPaymentStatus, "Payment status should be false by default");
        }

    }

}