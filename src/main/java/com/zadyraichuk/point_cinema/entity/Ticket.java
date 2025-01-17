package com.zadyraichuk.point_cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

/**
 * Represents a ticket entity in the cinema booking system.
 * This class is mapped to the "ticket" collection in the database.
 * <p>
 * This class uses Lombok annotations to reduce boilerplate code:
 * <ul>
 * <li>{@code @Getter} generates getters for all fields.</li>
 * <li>{@code @Setter} generates a setter for the {@code id} field.</li>
 * <li>{@code @AllArgsConstructor} generates a constructor for all fields.</li>
 * <li>{@code @Builder} implements builder pattern.</li>
 * </ul>
 * </p>
 *
 * @author Rostyslav Zadyraichuk
 * @version 0.1
 */
@Document(collection = "ticket")
@AllArgsConstructor
@Getter
@Builder
public class Ticket {

    /**
     * The unique identifier of the ticket.
     * This field is indexed and must be unique.
     */
    @Id
    @Indexed(unique = true)
    @Setter
    private String id;

    /**
     * The date when the ticket was created or booked.
     * Defaults to the current date.
     */
    @Builder.Default
    private final LocalDate date = LocalDate.now();

    /**
     * The row number of the seat for which the ticket is booked.
     */
    @Field(name = "place_row")
    private final Integer placeRow;

    /**
     * The seat number within the row for which the ticket is booked.
     */
    @Field(name = "place_seat")
    private final Integer placeSeat;

    /**
     * The identifier of the seance (movie screening) for which the ticket is booked.
     */
    @Field(name = "seance_id")
    private final String seanceId;

    /**
     * The identifier of the user who booked the ticket.
     */
    @Field(name = "user_id")
    private final String userId;

    /**
     * The payment status of the ticket.
     * Defaults to {@code false}, indicating the ticket is unpaid.
     */
    @Field(name = "payment_status")
    @Builder.Default
    private final Boolean paymentStatus = false;

}
