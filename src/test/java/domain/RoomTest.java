package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    Room roomWithId;
    Room roomWithoutId;

    final String roomDescription = "Arkiv";
    final int id = 1;
    final double sizeInSqm = 50.0;

    final String tooLongDescription = "A very long and unnecessary description of a room that is unnecessary";
    final int noId = 0;
    final int negativeId = -1;
    final double negativeSize = -1.0;
    final String emptyDescription = "";

    @BeforeEach
    void setUp() {
        roomWithId = new Room(id, sizeInSqm, roomDescription);
        roomWithoutId = new Room(sizeInSqm, roomDescription);
    }

    @AfterEach
    void tearDown() {
        roomWithId = null;
        roomWithoutId = null;
    }

    @Test
    void getId() {
        assertEquals(
                id,
                roomWithId.getId(),
                "Room id should be " + id
        );
    }

    @Test
    void getIdFromRoomWithoutId() {
        assertEquals(
                noId,
                roomWithoutId.getId(),
                "Rooms created with the non-id constructor should have an id of 0 representing no id");
    }

    @Test
    void getSize() {
        assertEquals(
                sizeInSqm,
                roomWithId.getSizeInSqm(),
                "Expected room size 50.0, but it was " + roomWithId.getSizeInSqm()
        );
    }

    @Test
    void getDescription() {
        assertEquals(
                roomDescription,
                roomWithId.getDescription(),
                String.format("Expected room with description: '%s', but got '%s'", roomDescription, roomWithId.getDescription())
        );
    }

    @Test
    void createRoomWithNegativeId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(negativeId, sizeInSqm, roomDescription),
                "Creating a room with a negative id should throw an exception"
        );
    }

    @Test
    void createRoomWithNegativeSize() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(id, negativeSize, roomDescription),
                "Creating a room with a negative size should throw an exception"
        );
    }

    @Test
    void createRoomWithNullDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(id, sizeInSqm, null),
                "Creating a room with a null description should throw an exception"
        );
    }

    @Test
    void createRoomWithEmptyDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(id, sizeInSqm, emptyDescription),
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
}