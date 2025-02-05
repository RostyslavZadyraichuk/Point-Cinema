package com.zadyraichuk.point_cinema.repository;

import com.zadyraichuk.point_cinema.entity.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Message repository tests")
@DataMongoTest
@ActiveProfiles("test")
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    private static Message message;

    @BeforeEach
    void setUp() {
        message = initMessage();
        message = messageRepository.save(message);
    }

    @AfterEach
    void afterEach() {
        messageRepository.deleteAll();
    }

    @Test
    @DisplayName("Test creating an Message")
    void testCreate() {
        Message messageLocal = new Message("Created");
        assertNull(messageLocal.getId(), "The message has just created should have no ID");

        Message savedMessage = assertDoesNotThrow(() -> messageRepository.save(messageLocal));
        assertNotNull(savedMessage.getId(), "The saved message should have a generated ID");
        assertEquals(messageLocal.getText(), savedMessage.getText(), "The text should match");
    }

    @Test
    @DisplayName("Test finding an Message by ID")
    void testFindById() {
        Optional<Message> foundMessageOpt = messageRepository.findById(message.getId());
        assertTrue(foundMessageOpt.isPresent(), "The message should be found by ID");

        Message foundMessage = foundMessageOpt.get();
        assertEquals(message.getId(), foundMessage.getId(), "The IDs should match");
        assertEquals(message.getText(), foundMessage.getText(), "The text should match");
    }

    @Test
    @DisplayName("Test finding all Messages")
    void testFindAll() {
        Message[] messages = getMessagesForGeneralCrudTests();
        messageRepository.saveAll(Arrays.asList(messages));
        int expectedLength = messages.length + 1;
        List<Message> foundMessages = messageRepository.findAll();

        assertNotNull(foundMessages, "The found list should not be null");
        assertFalse(foundMessages.isEmpty(), "The found list should not be empty");
        assertEquals(expectedLength, foundMessages.size(), "The size of the list should match the number of messages saved");
        assertTrue(foundMessages.containsAll(Arrays.asList(messages)), "The found messages should contain all messages saved before");
    }

    @Test
    @DisplayName("Test updating a Cinema")
    void testUpdate() {
        Message updatedMessage = new Message("UpdatedText");
        updatedMessage.setId(message.getId());
        message = messageRepository.save(updatedMessage);

        assertEquals(updatedMessage.getId(), message.getId(), "The ID should remain the same after update");
        assertEquals(updatedMessage.getText(), message.getText(), "The text should be updated");
    }

    @Test
    @DisplayName("Test save all Messages")
    void testSaveAll() {
        Message[] messages = getMessagesForGeneralCrudTests();

        List<Message> savedMessages = messageRepository.saveAll(Arrays.asList(messages));

        assertNotNull(savedMessages, "The saved messages list should not be null");
        assertEquals(messages.length, savedMessages.size(), "The size of the saved messages should match the input list size");
        Stream<String> messageIds = savedMessages.stream().map(Message::getId);
        assertTrue(messageIds.allMatch(Objects::nonNull), "Each saved message should have a generated ID");

        List<Message> foundMessages = messageRepository.findAll();
        int expectedSize = messages.length + 1;
        assertEquals(expectedSize, foundMessages.size(), "The number of messages found should match the saved messages");
        assertTrue(foundMessages.containsAll(savedMessages), "The found messages should match the saved messages");
    }

    @Test
    @DisplayName("Test deleting an Message")
    void testDelete() {
        messageRepository.delete(message);

        Optional<Message> deletedMessageOpt = messageRepository.findById(message.getId());
        assertFalse(deletedMessageOpt.isPresent(), "The message should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting an Message by ID")
    void testDeleteById() {
        messageRepository.deleteById(message.getId());

        Optional<Message> deletedMessageOpt = messageRepository.findById(message.getId());
        assertFalse(deletedMessageOpt.isPresent(), "The message should be deleted and not found by ID");
    }

    @Test
    @DisplayName("Test deleting all Messages")
    void testDeleteAll() {
        Message[] messages = getMessagesForGeneralCrudTests();
        messageRepository.saveAll(Arrays.asList(messages));

        List<Message> foundMessages = messageRepository.findAll();
        assertNotNull(foundMessages, "The messages should exist before deleting");
        assertFalse(foundMessages.isEmpty(), "The messages should exist before deleting");

        messageRepository.deleteAll();
        foundMessages = messageRepository.findAll();
        assertNotNull(foundMessages, "The messages should be deleted and messages list should not be null");
        assertTrue(foundMessages.isEmpty(), "The messages should be deleted and messages list should be empty");
    }

    private Message initMessage() {
        return new Message("test");
    }

    private Message[] getMessagesForGeneralCrudTests() {
        Message unique1 = new Message("Unique");
        Message unique2 = new Message("Unique");
        Message unique3 = new Message("Unique");

        return new Message[]{unique1, unique2, unique3};
    }

}