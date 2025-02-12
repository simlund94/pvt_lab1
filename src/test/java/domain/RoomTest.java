package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    Room roomWithId;
    Room roomWithoutId;

    String roomDescription = "Arkiv";

    @BeforeEach
    void setUp() {
        roomWithId = new Room(1, 50.0, roomDescription);
        roomWithoutId = new Room(50.0, roomDescription);
    }

    @AfterEach
    void tearDown() {
        roomWithId = null;
        roomWithoutId = null;
    }

    @Test
    void getId() {
        assertEquals(
                1,
                roomWithId.getId(),
                "Expected room id 1, but it was " + roomWithId.getId()
        );
    }

    @Test
    void getIdFromRoomWithoutId() {
        assertEquals(
                0,
                roomWithoutId.getId(),
                "Rooms created with the non-id constructor should have an id of 0 representing no id");
    }

    @Test
    void getSize() {
        assertEquals(
                50.0,
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
                () -> new Room(-1, 25.0, "A room"),
                "Creating a room with a negative id should throw an exception"
        );
    }

    @Test
    void createRoomWithNegativeSize() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(1, -1.0, "A room"),
                "Creating a room with a negative size should throw an exception"
        );
    }

    @Test
    void createRoomWithNullDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(1, 1.0, null),
                "Creating a room with a null description should throw an exception"
        );
    }

    @Test
    void createRoomWithEmptyDescription() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(1, 1.0, ""),
                "Creating a room with an empty description should throw an exception"
        );
    }


}