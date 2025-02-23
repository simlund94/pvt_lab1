package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite for the Room class.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
class RoomTest {

    Room roomWithId;
    Room roomWithIdTwin;
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
        roomWithIdTwin = new Room(validId, validSize, validRoomDescription, validSiteId);
        roomWithoutId = new Room(validSize, validRoomDescription, validSiteId);
    }

    @AfterEach
    void tearDown() {
        roomWithId = null;
        roomWithIdTwin = null;
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
    void getSizeShouldReturnValidSize() {
        assertEquals(
                validSize,
                roomWithId.getSizeInSqm(),
                "Expected room size 50.0, but it was " + roomWithId.getSizeInSqm()
        );
    }

    @Test
    void getDescriptionShouldReturnValidDescription() {
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
    void createRoomWithNegativeSiteId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Room(validId, validSize, validRoomDescription, negativeId),
                "A negative site validId should throw an exception"
        );
    }

    @Test
    void equalsShouldReturnTrueWhenRoomsAreEqual() {
        assertEquals(roomWithId, roomWithIdTwin,
                "Two semantically identical rooms should return true from equals()");
        assertEquals(roomWithIdTwin, roomWithId,
                "The equals() method should be transitive");
        assertEquals(roomWithId, roomWithId,
                "The equals() method should be reflexive");
    }

    @Test
    void equalsShouldReturnFalseWhenRoomsAreUnequal() {
        assertNotEquals(roomWithId, roomWithoutId,
                "Two rooms which are not semantically identical should return false from equals()");
        assertNotEquals(roomWithId, tooLongDescription,
                "Should not be equal when compared to another object");
        assertNotEquals(roomWithId, null,
                "Should not be equal when compared to a null value");
    }

    @Test
    void hashCodeShouldBeEqualForTwoIdenticalEmployees() {
        assertEquals(roomWithId.hashCode(), roomWithIdTwin.hashCode(),
                "Hash code should be equal for two identical rooms");
    }

    @Test
    void hashCodeShouldBeConsistent() {
        int initialHashCode = roomWithId.hashCode();
        assertEquals(initialHashCode, roomWithId.hashCode(), "Hashcode should be consistent");
        assertEquals(initialHashCode, roomWithId.hashCode(), "Hashcode should be consistent");
    }

    @Test
    void hashCodeShouldBeDifferentForTwoDifferentRooms() {
        assertNotEquals(roomWithId.hashCode(), roomWithoutId.hashCode(),
                "Hashcode for two different rooms should not be the same");
    }
}