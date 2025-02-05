package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Ticket;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Ticket repository tests")
@DataMongoTest
@ActiveProfiles("test")
class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    private static Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = initTicket();
        ticket = ticketRepository.save(ticket);
    }

    @AfterEach
    void afterEach() {
        ticketRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test creating an Ticket")
    void testCreate(LocalDate date, int placeRow, int placeSeat, String seanceId, boolean shouldThrowException) {
        Ticket ticketLocal = new Ticket(null, date, placeRow, placeSeat, seanceId, "Created", false);
        assertNull(ticketLocal.getId(), "The ticket has just created should have no ID");

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> ticketRepository.save(ticketLocal),
                    "Should have thrown an exception due to duplicate date, place row, place seat and seance ID");
        } else {
            Ticket savedTicket = assertDoesNotThrow(() -> ticketRepository.save(ticketLocal));
            assertNotNull(savedTicket.getId(), "The saved ticket should have a generated ID");
            assertEquals(ticketLocal.getDate(), savedTicket.getDate(), "The date should match");
            assertEquals(ticketLocal.getPlaceRow(), savedTicket.getPlaceRow(), "The place row should match");
            assertEquals(ticketLocal.getPlaceSeat(), savedTicket.getPlaceSeat(), "The place seat should match");
            assertEquals(ticketLocal.getSeanceId(), savedTicket.getSeanceId(), "The seance ID should match");
            assertEquals(ticketLocal.getUserId(), savedTicket.getUserId(), "The user ID should match");
            assertEquals(ticketLocal.getPaymentStatus(), savedTicket.getPaymentStatus(), "The payment status should match");
        }

    }

    @Test
    @DisplayName("Test finding an Ticket by ID")
    void testFindById() {
        Optional<Ticket> foundTicketOpt = ticketRepository.findById(ticket.getId());
        assertTrue(foundTicketOpt.isPresent(), "The ticket should be found by ID");

        Ticket foundTicket = foundTicketOpt.get();
        assertEquals(ticket.getId(), foundTicket.getId(), "The IDs should match");
        assertEquals(ticket.getDate(), foundTicket.getDate(), "The date should match");
        assertEquals(ticket.getPlaceRow(), foundTicket.getPlaceRow(), "The place row should match");
        assertEquals(ticket.getPlaceSeat(), foundTicket.getPlaceSeat(), "The place seat should match");
        assertEquals(ticket.getSeanceId(), foundTicket.getSeanceId(), "The seance ID should match");
        assertEquals(ticket.getUserId(), foundTicket.getUserId(), "The user ID should match");
        assertEquals(ticket.getPaymentStatus(), foundTicket.getPaymentStatus(), "The payment status should match");
    }

    @Test
    @DisplayName("Test finding all Tickets")
    void testFindAll() {
        Ticket[] tickets = getTicketsForGeneralCrudTests();
        ticketRepository.saveAll(Arrays.asList(tickets));
        int expectedLength = tickets.length + 1;
        List<Ticket> foundTickets = ticketRepository.findAll();

        assertNotNull(foundTickets, "The found list should not be null");
        assertFalse(foundTickets.isEmpty(), "The found list should not be empty");
        assertEquals(expectedLength, foundTickets.size(), "The size of the list should match the number of tickets saved");
        assertTrue(foundTickets.containsAll(Arrays.asList(tickets)), "The found tickets should contain all tickets saved before");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForCreateAndUpdateTests")
    @DisplayName("Test updating an Ticket")
    void testUpdate(LocalDate date, int placeRow, int placeSeat, String seanceId, boolean shouldThrowException) {
        Ticket ticketBeforeUpdate = new Ticket(null, LocalDate.now(), 100, 100,
                "BeforeUpdate", "BeforeUpdate", false);
        ticketBeforeUpdate = ticketRepository.save(ticketBeforeUpdate);
        Ticket updatedTicket = new Ticket(null, date, placeRow, placeSeat, seanceId, "Updated", false);
        updatedTicket.setId(ticketBeforeUpdate.getId());

        if (shouldThrowException) {
            assertThrows(DuplicateKeyException.class, () -> ticketRepository.save(updatedTicket),
                    "Should have thrown an exception due to duplicate date, place row, place seat and seance ID");
        } else {
            ticket = assertDoesNotThrow(() -> ticketRepository.save(updatedTicket));
            assertEquals(updatedTicket.getId(), ticket.getId(), "The ID should remain the same after update");
            assertEquals(updatedTicket.getDate(), ticket.getDate(), "The date should be updated");
            assertEquals(updatedTicket.getPlaceRow(), ticket.getPlaceRow(), "The place row should be updated");
            assertEquals(updatedTicket.getPlaceSeat(), ticket.getPlaceSeat(), "The place seat should be updated");
            assertEquals(updatedTicket.getSeanceId(), ticket.getSeanceId(), "The seance ID should be updated");
            assertEquals(updatedTicket.getUserId(), ticket.getUserId(), "The user ID should be updated");
            assertEquals(updatedTicket.getPaymentStatus(), ticket.getPaymentStatus(), "The payment status should be updated");
        }
    }

    @Test
    @DisplayName("Test save all Tickets")
    void testSaveAll() {
        Ticket[] tickets = getTicketsForGeneralCrudTests();

        List<Ticket> savedTickets = ticketRepository.saveAll(Arrays.asList(tickets));

        assertNotNull(savedTickets, "The saved tickets list should not be null");
        assertEquals(tickets.length, savedTickets.size(), "The size of the saved tickets should match the input list size");
        Stream<String> ticketIds = savedTickets.stream().map(Ticket::getId);
        assertTrue(ticketIds.allMatch(Objects::nonNull), "Each saved ticket should have a generated ID");

        List<Ticket> foundTickets = ticketRepository.findAll();
        int expectedSize = tickets.length + 1;
        assertEquals(expectedSize, foundTickets.size(), "The number of tickets found should match the saved tickets");
        assertTrue(foundTickets.containsAll(savedTickets), "The found tickets should match the saved tickets");
    }

    @Test
    @DisplayName("Test deleting an Ticket")
    void testDelete() {
        ticketRepository.delete(ticket);

        Optional<Ticket> deletedTicketOpt = ticketRepository.findById(ticket.getId());
        assertFalse(deletedTicketOpt.isPresent(), "The ticket should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting an Ticket by ID")
    void testDeleteById() {
        ticketRepository.deleteById(ticket.getId());

        Optional<Ticket> deletedTicketOpt = ticketRepository.findById(ticket.getId());
        assertFalse(deletedTicketOpt.isPresent(), "The ticket should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting all Tickets")
    void testDeleteAll() {
        Ticket[] tickets = getTicketsForGeneralCrudTests();
        ticketRepository.saveAll(Arrays.asList(tickets));

        List<Ticket> foundTickets = ticketRepository.findAll();
        assertNotNull(foundTickets, "The tickets should exist before deleting");
        assertFalse(foundTickets.isEmpty(), "The tickets should exist before deleting");

        ticketRepository.deleteAll();
        foundTickets = ticketRepository.findAll();
        assertNotNull(foundTickets, "The tickets should be deleted and tickets list should not be null");
        assertTrue(foundTickets.isEmpty(), "The tickets should be deleted and tickets list should be empty");
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByDateAndSeanceIdTest")
    @DisplayName("Test finding an Ticket by Date and Seance ID")
    void testFindByDate(LocalDate date,
                        String seanceId,
                        int expectedSize,
                        int[] expectedTicketIndexes) {
        Ticket[] tickets = getTicketsForSpecializedTests();
        ticketRepository.saveAll(Arrays.asList(tickets));

        List<Ticket> foundTickets = ticketRepository.findByDateAndSeanceId(date, seanceId);

        assertNotNull(foundTickets, "The result should not be null");
        assertEquals(expectedSize, foundTickets.size(),
                String.format("The result should contain %d tickets with date '%s' and seanceId '%s'", expectedSize, date, seanceId));
        for (int i : expectedTicketIndexes) {
            assertTrue(foundTickets.contains(tickets[i]), "Ticket should be in the result");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFindByUserIdTest")
    @DisplayName("Test finding an Ticket by User ID")
    void testFindByUserId(String userId,
                          int expectedSize,
                          int[] expectedTicketIndexes) {
        Ticket[] tickets = getTicketsForSpecializedTests();
        ticketRepository.saveAll(Arrays.asList(tickets));

        List<Ticket> foundTickets = ticketRepository.findByUserId(userId);

        assertNotNull(foundTickets, "The result should not be null");
        assertEquals(expectedSize, foundTickets.size(),
                String.format("The result should contain %d tickets with userId '%s'", expectedSize, userId));
        for (int i : expectedTicketIndexes) {
            assertTrue(foundTickets.contains(tickets[i]), "Ticket should be in the result");
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForDeleteByDateBeforeTest")
    @DisplayName("Test deleting Tickets by Date is before")
    void testDeleteByDateToBefore(LocalDate date,
                                  int expectedRemovedSize,
                                  int[] expectedRemovedTicketIndexes) {
        Ticket[] tickets = getTicketsForSpecializedTests();
        ticketRepository.saveAll(Arrays.asList(tickets));

        ticketRepository.deleteByDateBefore(date);
        List<Ticket> foundSeances = ticketRepository.findAll();

        assertNotNull(foundSeances, "The result should not be null");
        assertEquals(expectedRemovedSize, tickets.length - foundSeances.size() + 1,
                String.format("The result should contain %d tickets with date before '%s'", expectedRemovedSize, date));
        for (int i : expectedRemovedTicketIndexes) {
            assertFalse(foundSeances.contains(tickets[i]), "Ticket should not be in the result");
        }
    }

    private Ticket initTicket() {
        return Ticket.builder()
                .date(LocalDate.of(1, 1, 1))
                .placeRow(0)
                .placeSeat(0)
                .seanceId("test")
                .userId("test")
                .paymentStatus(true)
                .build();
    }

    private Ticket[] getTicketsForGeneralCrudTests() {
        Ticket unique1 = new Ticket(null, LocalDate.now(), 100, 100, "Unique",
                "Unique", false);
        Ticket unique2 = new Ticket(null, LocalDate.now(), ticket.getPlaceRow(), 100, "Unique",
                "Unique", false);
        Ticket unique3 = new Ticket(null, LocalDate.now(), 100, ticket.getPlaceSeat(), "Unique",
                "Unique", false);

        return new Ticket[]{unique1, unique2, unique3};
    }

    private Ticket[] getTicketsForSpecializedTests() {
        Ticket ticket1 = new Ticket(null, LocalDate.of(1, 1, 1),
                1, 1, "1", "1", false);
        Ticket ticket2 = new Ticket(null, LocalDate.of(1, 1, 1),
                2, 2, "2", "1", false);
        Ticket ticket3 = new Ticket(null, LocalDate.of(2, 2, 2),
                3, 3, "2", "2", false);

        return new Ticket[]{ticket1, ticket2, ticket3};
    }

    /**
     * Provides arguments for creating and updating tests in {@link TicketRepository}.
     * Each argument consists of:
     * <ul>
     *     <li>date - the date to search for tickets by</li>
     *     <li>placeRow - the row number of the ticket to search for</li>
     *     <li>placeSeat - the seat number of the ticket to search for</li>
     *     <li>seanceId - the identifier of the seance to search for tickets by</li>
     *     <li>shouldThrowException - whether the test should expect a DuplicateKeyException</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForCreateAndUpdateTests() {
        return Stream.of(
                Arguments.of(LocalDate.of(100, 1, 1), 100, 100, "Unique", false),
                Arguments.of(ticket.getDate(), 100, 100, "Unique", false),
                Arguments.of(LocalDate.of(100, 1, 1), ticket.getPlaceRow(), 100, "Unique", false),
                Arguments.of(LocalDate.of(100, 1, 1), 100, ticket.getPlaceSeat(), "Unique", false),
                Arguments.of(LocalDate.of(100, 1, 1), 100, 100, ticket.getSeanceId(), false),
                Arguments.of(ticket.getDate(), ticket.getPlaceRow(), 100, "Unique", false),
                Arguments.of(ticket.getDate(), 100, ticket.getPlaceSeat(), "Unique", false),
                Arguments.of(ticket.getDate(), 100, 100, ticket.getSeanceId(), false),
                Arguments.of(ticket.getDate(), ticket.getPlaceRow(), ticket.getPlaceSeat(), "Unique", false),
                Arguments.of(ticket.getDate(), ticket.getPlaceRow(), 100, ticket.getSeanceId(), false),
                Arguments.of(ticket.getDate(), ticket.getPlaceRow(), ticket.getPlaceSeat(), ticket.getSeanceId(), true)
        );
    }

    /**
     * Provides arguments for testing the testFindByDateAndSeanceId method in {@link TicketRepository}.
     * The arguments are:
     * <ul>
     *     <li>date - the date to search for tickets by</li>
     *     <li>seanceId - the identifier of the seance to search for tickets by</li>
     *     <li>expectedSize - the expected number of tickets with the specified date and seanceId</li>
     *     <li>expectedTicketIndexes - the indexes of expected tickets in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByDateAndSeanceIdTest() {
        return Stream.of(
                Arguments.of(LocalDate.of(1, 1, 1), "1", 1, new int[]{0}),
                Arguments.of(LocalDate.of(1, 1, 1), "2", 1, new int[]{1}),
                Arguments.of(LocalDate.of(2, 2, 2), "1", 0, new int[]{}),
                Arguments.of(LocalDate.of(2, 2, 2), "2", 1, new int[]{2}),
                Arguments.of(LocalDate.of(3, 3, 3), "1", 0, new int[]{}),
                Arguments.of(LocalDate.of(1, 1, 1), "3", 0, new int[]{})
        );
    }

    /**
     * Provides arguments for testing the testFindByUserId method in {@link TicketRepository}.
     * The arguments are:
     * <ul>
     *     <li>userId - the identifier of the user to search for tickets by</li>
     *     <li>expectedSize - the expected number of tickets with the specified userId</li>
     *     <li>expectedTicketIndexes - the indexes of expected tickets in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForFindByUserIdTest() {
        return Stream.of(
                Arguments.of("0", 0, new int[]{}),
                Arguments.of("1", 2, new int[]{0, 1}),
                Arguments.of("2", 1, new int[]{2})
        );
    }

    /**
     * Provides arguments for testing the deleteByDateBefore method in {@link TicketRepository}.
     * The arguments are:
     * <ul>
     *     <li>date - the date before which to delete tickets</li>
     *     <li>expectedRemovedSize - the expected number of removed tickets</li>
     *     <li>expectedRemovedTicketIndexes - the indexes of expected removed tickets in the test data</li>
     * </ul>
     *
     * @return a stream of arguments for parameterized tests
     */
    private static Stream<Arguments> provideArgumentsForDeleteByDateBeforeTest() {
        return Stream.of(
                Arguments.of(LocalDate.of(1, 1, 1), 0, new int[]{}),
                Arguments.of(LocalDate.of(2, 2, 2), 3, new int[]{0, 1}),
                Arguments.of(LocalDate.of(3, 3, 3), 4, new int[]{0, 1, 2})
        );
    }

}