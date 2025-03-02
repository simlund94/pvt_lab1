package domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.room.GetAllRoomsOnSiteService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the Site class.
 *
 * @author Simon Lundgren
 * @version 1.0
 */
class SiteTest {

    Site siteWithId;
    Site siteWithIdTwin;
    Site siteWithoutId;
    Site siteWithLazyRooms;

    final int validId = 1;
    final int validNoId = 0;
    final String validName = "Högskolan i Gävle";
    final String validAddress = "Kungsbäcksvägen 47";
    final int validPostalCode = 80176;
    final String validPostalArea = "Gävle";
    final String validPropertyDesignation = "GÄVLE KUNGSBÄCK 2:8";
    final List<Room> listOfNoIdRooms = List.of(new Room(50.0, "Description", validId));
    final List<Room> listOfRooms = List.of(new Room(1, 50.0, "Description", validId));

    final int negativeId = -1;
    final String stringOver100Characters = "Högskolan i Gävle som ligger i Gävle bredvid kullen och vid Gavleån fast det är rätt " +
            "så nära boulougnerskogen också jag hoppas detta är långt nog nu.";
    final String emptyString = "";
    final int tooLowPostalCode = 9999;
    final int tooHighPostalCode = 100000;
    final List<Room> emptyList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        siteWithId = new Site(validId, validName, validAddress, validPostalCode, validPostalArea, validPropertyDesignation, listOfNoIdRooms);
        siteWithIdTwin = new Site(validId, validName, validAddress, validPostalCode, validPostalArea, validPropertyDesignation, listOfNoIdRooms);
        siteWithoutId = new Site(validName, validAddress, validPostalCode, validPostalArea, validPropertyDesignation);
        siteWithLazyRooms = new Site(validId, validName, validAddress, validPostalCode, validPostalArea, validPropertyDesignation);
    }

    @AfterEach
    void tearDown() {
        siteWithId = null;
        siteWithIdTwin = null;
        siteWithoutId = null;
    }

    @Test
    void createSiteWithValidId() {
        assertEquals(validId, siteWithId.getId(), "The site should have an validId of " + validId);
    }

    @Test
    void createSiteWithValidNoId() {
        assertEquals(validNoId, siteWithoutId.getId(),
                "The site constructed with the non-validId constructor should have an validId of " + validNoId);
    }

    @Test
    void createSiteWithNegativeId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(negativeId, validName, validAddress, validPostalCode, validPostalArea, validPropertyDesignation, listOfNoIdRooms),
                "Creating a site with a negative validId should throw an exception"
        );
    }

    @Test
    void createSiteWithValidName() {
        assertEquals(validName, siteWithId.getName(), "The name should be " + validName);
    }

    @Test
    void setNullName() {
        siteWithId.setName(null);
        assertNull(siteWithId.getName(), "Setting a name to null should be legal");
    }

    @Test
    void setEmptyName() {
        siteWithId.setName(emptyString);
        assertNull(siteWithId.getName(), "Setting a name to an empty string should set null");
    }

    @Test
    void setTooLongName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> siteWithId.setName(stringOver100Characters),
                "Setting a name over 100 characters should throw an exception"
        );
    }

    @Test
    void createSiteWithValidAddress() {
        assertEquals(validAddress, siteWithId.getAddress(), "The address should be: " + validAddress);
    }

    @Test
    void createSiteWithNullAddress() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(validName, null, validPostalCode, validPostalArea, validPropertyDesignation),
                "Setting a null address should throw an exception"
        );
    }

    @Test
    void createSiteWithEmptyAddress() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(validName, emptyString, validPostalCode, validPostalArea, validPropertyDesignation),
                "Setting an empty address should throw an exception"
        );
    }

    @Test
    void createSiteWithTooLongAddress() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(validName, stringOver100Characters, validPostalCode, validPostalArea, validPropertyDesignation),
                "Setting an address over 100 characters should throw an exception"
        );
    }

    @Test
    void createValidPostalCode() {
        assertEquals(validPostalCode, siteWithId.getPostalCode(), "The postal code should be " + validPostalCode);
    }

    @Test
    void createInvalidPostalCode() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(validName, validAddress, tooLowPostalCode, validPostalArea, validPropertyDesignation),
                "An invalid postal code should throw an exception"
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(validName, validAddress, tooHighPostalCode, validPostalArea, validPropertyDesignation),
                "An invalid postal code should throw an exception"
        );
    }

    @Test
    void createSiteWithValidPostalArea() {
        assertEquals(validPostalArea, siteWithId.getPostalArea(), "The postal area should be: " + validPostalArea);
    }

    @Test
    void createSiteWithNullPostalArea() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(validName, validAddress, validPostalCode, null, validPropertyDesignation),
                "Creating site with null postal area should throw an exception"
        );
    }

    @Test
    void createSiteWithEmptyPostalArea() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(validName, validAddress, validPostalCode, emptyString, validPropertyDesignation),
                "Create a site with an empty postal area should throw an exception"
        );
    }

    @Test
    void createSiteWithTooLongPostalArea() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(validName, validAddress, validPostalCode, stringOver100Characters, validPropertyDesignation),
                "Creating a site with a postal area over 100 characters should throw an exception"
        );
    }

    @Test
    void createSiteWithValidPropertyDesignation() {
        assertEquals(validPropertyDesignation, siteWithId.getPropertyDesignation(), "The property designation should be " + validPropertyDesignation);
    }

    @Test
    void createSiteWithNullPropertyDesignation() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(validName, validAddress, validPostalCode, validPostalArea, null),
                "Creating a site with a null property designation should throw an exception"
        );
    }

    @Test
    void createSiteWithEmptyPropertyDesignation() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(validName, validAddress, validPostalCode, validPostalArea, emptyString),
                "Creating a site with an empty property designation should throw an exception"
        );
    }

    @Test
    void createSiteWithTooLongPropertyDesignation() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Site(validName, validAddress, validPostalCode, validPostalArea, stringOver100Characters),
                "Creating a site with a property designation over 50 characters should throw an exception"
        );
    }

    @Test
    void createSiteWithValidListOfRooms() {
        assertEquals(listOfNoIdRooms, siteWithId.getRooms(), "The list should be equal to " + listOfNoIdRooms);
    }

    @Test
    void createSiteWithLazilyInitializedListOfRooms() {
        GetAllRoomsOnSiteService service = mock(GetAllRoomsOnSiteService.class);
        when(service.execute()).thenReturn(listOfRooms);
        List<Room> result = siteWithLazyRooms.getRooms(service);
        assertEquals(listOfRooms, result, "The list of rooms should be equal with the result.");
    }

    @Test
    void equalsShouldReturnTrueWhenSitesAreEqual() {
        assertEquals(siteWithId, siteWithIdTwin,
                "Two semantically identical sites should return true from equals()");
        assertEquals(siteWithIdTwin, siteWithId,
                "The equals() method should be transitive");
        assertEquals(siteWithId, siteWithId,
                "The equals() method should be reflexive");
    }

    @Test
    void equalsShouldReturnFalseWhenSitesAreUnequal() {
        assertNotEquals(siteWithId, siteWithoutId,
                "Two sites which are not semantically identical should return false from equals()");
        assertNotEquals(siteWithId, validName,
                "Should not be equal when compared to another object");
        assertNotEquals(null, siteWithId,
                "Should not be equal when compared to a null value");
    }

    @Test
    void hashCodeShouldBeEqualForTwoIdenticalSites() {
        assertEquals(siteWithId.hashCode(), siteWithIdTwin.hashCode(),
                "Hash code should be equal for two identical sites");
    }

    @Test
    void hashCodeShouldBeConsistent() {
        int initialHashCode = siteWithId.hashCode();
        assertEquals(initialHashCode, siteWithId.hashCode(), "Hashcode should be consistent");
        assertEquals(initialHashCode, siteWithId.hashCode(), "Hashcode should be consistent");
    }

    @Test
    void hashCodeShouldBeDifferentForTwoDifferentSites() {
        assertNotEquals(siteWithId.hashCode(), siteWithoutId.hashCode(),
                "Hashcode for two different sites should not be the same");
    }

}