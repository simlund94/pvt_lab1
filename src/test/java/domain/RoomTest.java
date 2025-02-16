package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    Room roomWithId;
    Room roomWithoutId;

    final String validRoomDescription = "Arkiv";
    final int validId = 1;
    final double validSize = 50.0;
    final int validSiteId = 1;

    final String tooLongDescription = "A very long and unnecessary description of a room that is unnecessary";
    final int noId = 0;
    final int negativeId = -1;
    final double negativeSize = -1.0;
    final String emptyDescription = "";

    @BeforeEach
    void setUp() {
        roomWithId = new Room(validId, validSize, validRoomDescription, validSiteId);
        roomWithoutId = new Room(validSize, validRoomDescription);
    }

    @AfterEach
    void tearDown() {
        roomWithId = null;
        roomWithoutId = null;
    }

    @Test
    void getId() {
        assertEquals(
                validId,
                roomWithId.getId(),
                "Room validId should be " + validId
        );
    }

    @Test
    void getIdFromRoomWithoutId() {
        assertEquals(
                noId,
                roomWithoutId.getId(),
                "Rooms created with the non-validId constructor should have an validId of 0 representing no validId");
    }

    @Test
    void getSize() {
        assertEquals(
                validSize,
                roomWithId.getSizeInSqm(),
                "Expected room size 50.0, but it was " + roomWithId.getSizeInSqm()
        );
    }

    @Test
    void getDescription() {
        assertEquals(
                validRoomDescription,
                roomWithId.getDescription(),
                String.format("Expected room with description: '%s', but got '%s'", validRoomDescription, roomWithId.getDescription())
        );
    }

    @Test
    void createRoomWithNegativeId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(negativeId, validSize, validRoomDescription, validSiteId),
                "Creating a room with a negative validId should throw an exception"
        );
    }

    @Test
    void createRoomWithNegativeSize() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(validId, negativeSize, validRoomDescription, validSiteId),
                "Creating a room with a negative size should throw an exception"
        );
    }

    @Test
    void createRoomWithNullDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(validId, validSize, null, validSiteId),
                "Creating a room with a null description should throw an exception"
        );
    }

    @Test
    void createRoomWithEmptyDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(validId, validSize, emptyDescription, validSiteId),
                "Creating a room with an empty description should throw an exception"
        );
    }

    @Test
    void setTooLongDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> roomWithId.setDescription(tooLongDescription),
                "A description longer than 50 characters should throw an exception"
        );
    }

    @Test
    void createRoomWithValidSiteId() {
        assertEquals(validSiteId, roomWithId.getSiteId(), "The site validId should be " + validSiteId);
    }

    @Test
    void createRoomWithNoSiteId() {
        assertEquals(noId, roomWithoutId.getSiteId(), "The site validId with no validId should be " + noId);
    }

    @Test
    void createRoomWithNegativeSiteId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(validId, validSize, validRoomDescription, negativeId),
                "A negative site validId shoulw throw an exception"
        );
    }
}