package service;

import db.DatabaseConnector;
import db.H2DbConn;
import domain.CleaningOrder;
import domain.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.cleaningorder.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for Cleaning Order services. Testing all levels between ServiceRunner to an H2 database.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-04-05
 */
public class CleaningOrderServicesIT {

    ServiceRunner runner;
    DatabaseConnector h2DbConn;

    final LocalDateTime startTime = LocalDateTime.of(2025, 4, 15, 12, 0);
    final LocalDateTime finishedTime = LocalDateTime.of(2025, 4, 15, 13, 0);
    final int idNotInDatabase = 12;
    final List<CleaningOrder> cleaningOrdersInDatabase = List.of(
            new CleaningOrder(1, 1, 1,
                    LocalDateTime.of(2025, 5, 18, 13, 0, 0),
                    LocalDateTime.of(2025, 5, 18, 15, 0, 0),
                    OrderStatus.FINISHED),
            new CleaningOrder(2, 2, 2,
                    LocalDateTime.of(2025, 5, 18, 14, 0, 0),
                    LocalDateTime.of(2025, 5, 18, 16, 0, 0),
                    OrderStatus.FINISHED),
            new CleaningOrder(3, 2, 3,
                    LocalDateTime.of(2025, 5, 18, 14, 0, 0),
                    null,
                    OrderStatus.SCHEDULED)
    );

    final CleaningOrder cleaningOrderBeforeSaving = new CleaningOrder(2, 1, startTime);
    final CleaningOrder cleaningOrderAfterSaving = new CleaningOrder(4, 2, 1, startTime, null, OrderStatus.SCHEDULED);

    @BeforeEach
    void setUp() throws SQLException {
        h2DbConn = new H2DbConn();
        h2DbConn.open();
        initializeDatabaseSchema();
        runner = new ServiceRunner(h2DbConn);

    }

    @AfterEach
    void tearDown() throws SQLException {
        h2DbConn.close();
        h2DbConn = null;
        runner = null;
    }

    @Test
    void saveAndGetCleaningOrderSuccessfully() throws SQLException {
        initializeTestData();
        ServiceCommand<CleaningOrder> service = new SaveCleaningOrderService(cleaningOrderBeforeSaving);
        CleaningOrder cleaningOrderSaved = runner.execute(service);
        assertNotNull(cleaningOrderSaved);
        assertEquals(cleaningOrderAfterSaving, cleaningOrderSaved,
                "The return from the save service should match the given object");

        service = new GetCleaningOrderByIdService(cleaningOrderAfterSaving.getOrderId());
        CleaningOrder cleaningOrderRetrieved = runner.execute(service);
        assertNotNull(cleaningOrderRetrieved);
        assertEquals(cleaningOrderRetrieved, cleaningOrderSaved,
                "The retrieved cleaning order should match the cleaning order saved");
    }

    @Test
    void getNonExistantCleaningOrder_ShouldThrowException() {
        ServiceCommand<CleaningOrder> service = new GetCleaningOrderByIdService(idNotInDatabase);
        assertThrows(CleaningManagerServiceException.class,
                () -> runner.execute(service),
                "Trying to retrieve a CO which does not exist should throw an exception");
    }

    @Test
    void getAllCleaningOrdersSuccessfully() throws SQLException {
        initializeTestData();
        ServiceCommand<List<CleaningOrder>> service = new GetAllCleaningOrdersService();
        List<CleaningOrder> cleaningOrdersRetrieved = runner.execute(service);
        assertEquals(cleaningOrdersInDatabase, cleaningOrdersRetrieved,
                "The cleaning orders retrieved should match the given list of CO");
    }

    @Test
    void getAllCleaningOrdersFromEmptyDatabase_ShouldReturnEmptyList() {
        ServiceCommand<List<CleaningOrder>> service = new GetAllCleaningOrdersService();
        List<CleaningOrder> cleaningOrdersRetrieved = runner.execute(service);

        assertNotNull(cleaningOrdersRetrieved);
        assertEquals(cleaningOrdersRetrieved, Collections.emptyList(),
                "Retrieving all cleaning orders from an empty database should return an empty list");
    }

    @Test
    void getAndUpdateCleaningOrderSuccessfully() throws SQLException {
        initializeTestData();
        ServiceCommand<CleaningOrder> service = new GetCleaningOrderByIdService(3);
        CleaningOrder cleaningOrderRetrieved = runner.execute(service);
        assertEquals(cleaningOrdersInDatabase.get(2), cleaningOrderRetrieved);

        cleaningOrderRetrieved.setTimeFinished(LocalDateTime.of(2025, 5, 18, 15, 0, 0));
        cleaningOrderRetrieved.setOrderStatus(OrderStatus.FINISHED);
        service = new UpdateCleaningOrderService(cleaningOrderRetrieved);
        CleaningOrder cleaningOrderUpdated = runner.execute(service);
        assertEquals(cleaningOrderRetrieved,cleaningOrderUpdated);
    }

    @Test
    void updateNonExistantCleaningOrder_ShouldThrowException() {
        // test data not initialized
        ServiceCommand<CleaningOrder> service = new UpdateCleaningOrderService(cleaningOrderBeforeSaving);
        assertThrows(
                CleaningManagerServiceException.class,
                () -> runner.execute(service),
                "Updating a CO not in the database should throw an exception"
        );
    }

    @Test
    void deleteCleaningOrderSuccessfully_ShouldReturnTrue() throws SQLException {
        initializeTestData();
        ServiceCommand<Boolean> service = new DeleteCleaningOrderService(cleaningOrdersInDatabase.getFirst());
        boolean deleteSuccessful = runner.execute(service);
        List<CleaningOrder> cleaningOrdersRetrieved = runner.execute(new GetAllCleaningOrdersService());

        assertTrue(deleteSuccessful);
        assertFalse(cleaningOrdersRetrieved.contains(cleaningOrdersInDatabase.getFirst()),
                "The cleaning order should be deleted from the database");
    }

    @Test
    void deleteNonExistantCleaningOrder_ShouldReturnFalse() {
        // test data not initialized
        ServiceCommand<Boolean> service = new DeleteCleaningOrderService(cleaningOrdersInDatabase.getFirst());
        boolean deleteSuccessful = runner.execute(service);

        assertFalse(deleteSuccessful, "Trying to delete a cleaning order not in the database should return false");
    }

    @Test
    void deleteEmployeeTwice_SecondAttemptShouldReturnFalse() throws SQLException {
        initializeTestData();
        CleaningOrder employeeToDelete = cleaningOrdersInDatabase.getFirst();
        ServiceCommand<Boolean> service = new DeleteCleaningOrderService(employeeToDelete);
        assertTrue(runner.execute(service));
        assertFalse(runner.execute(service), "The second attempt to delete the same CO should return false");
    }

    // Kör ett SQL-script som populerar H2-databasen med testdata när det är nödvändigt
    private void initializeTestData() throws SQLException {
        h2DbConn.prepareStatement("RUNSCRIPT FROM 'classpath:db/testdata.sql'").execute();
    }

    // Kör ett SQL-script som skapar databasens schema, en kopia av den produktionsdatabasen.
    private void initializeDatabaseSchema() throws SQLException {
        h2DbConn.prepareStatement("RUNSCRIPT FROM 'classpath:db/schema.sql'").execute();
    }
}
