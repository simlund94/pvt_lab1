package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private Room roomWithId;
    private Room roomWithoutId;

    @BeforeEach
    void setUp() {
        roomWithId = new Room(1, 50, "Description");
        roomWithoutId = new Room(50, "Description");
    }

    @AfterEach
    void tearDown() {
        roomWithId = null;
        roomWithoutId = null;
    }
}