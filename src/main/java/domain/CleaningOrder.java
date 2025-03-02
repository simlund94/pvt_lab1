package domain;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * A class for holding a cleaning order to a room, assigned to one employee.
 *
 * <p>The intended use is for a user with administrative privileges to create and assign orders for
 * employees. The employees will retrieve these orders from the database, and upon completion, update
 * the fields {@code timeFinished} and {@code orderStatus}. </p>
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-27
 */
public class CleaningOrder {

    private final int orderId;

    /*
     * Ordern är gjord unik för en Employee för att hålla komplexiteten nere, men jag förstår
     * att det vore mer rimligt att en CleaningOrder skulle kunna läggas på ett antal städare i ett
     * mer komplett system. Detta skulle kunna uppnås med en många-till-många tabell som kopplar
     * Employees till CleaningOrders.
     */
    private final int employeeId;
    private final int roomId;
    private LocalDateTime timeScheduled;
    private LocalDateTime timeFinished;

    /*
     * Här förstår jag att ha både timeFinished och en OrderStatus enum som endast har SCHEDULED och FINISHED
     * bryter mot 3NF (OrderStatus är transitivt beroende på om timeFinished har ett värde eller inte),
     * men jag tänker mig att OrderStatus i framtiden sannolikt kan expanderas till att visa mer information,
     * såsom IN_PROGRESS för ett mer "live" system och CANCELLED. Detta i sin tur bryter lite mot YAGNI också,
     * men jag vill mest visa att det är något man kan göra, och att ha det implementerat från grunden är
     * enklare än att göra om det senare.
     */
    private OrderStatus orderStatus;

    /**
     * The default ID assigned to objects before they are assigned an id in the database.
     */
    private static final int NO_ID = 0;

    /**
     * Static variable to clarify that null values are allowed for {@code timeFinished}.
     */
    private static final LocalDateTime NOT_FINISHED = null;

    /**
     * Constructor to retrieve a complete CleaningOrder record from the database with a generated id.
     *
     * @param orderId       The orders unique id
     * @param employeeId    The id of the employee it pertains
     * @param roomId        The id of the room to be cleaned
     * @param timeScheduled The time the cleaning is scheduled
     * @param timeFinished  The time when the cleaning was finished
     * @param orderStatus   The current status of the order
     */
    public CleaningOrder(int orderId, int employeeId, int roomId, LocalDateTime timeScheduled, LocalDateTime timeFinished, OrderStatus orderStatus) {
        if (orderId < 0) {
            throw new IllegalArgumentException("OrderId cannot be negative");
        }
        if (employeeId <= 0) {
            throw new IllegalArgumentException("EmployeeId cannot be negative or zero");
        }
        if (roomId <= 0) {
            throw new IllegalArgumentException("RoomId cannot be negative or zero");
        }
        this.orderId = orderId;
        this.employeeId = employeeId;
        this.roomId = roomId;
        setTimeScheduled(timeScheduled);
        setTimeFinished(timeFinished);
        setOrderStatus(orderStatus);
    }

    /**
     * Constructor for creating a new cleaning order to be inserted into the database.
     * Defaults the timeScheduled to null and orderStatus to SCHEDULED.
     *
     * @param employeeId    The id of the employee it pertains
     * @param roomId        The id of the room to be cleaned
     * @param timeScheduled The time the cleaning is scheduled
     */
    public CleaningOrder(int employeeId, int roomId, LocalDateTime timeScheduled) {
        this(NO_ID, employeeId, roomId, timeScheduled, NOT_FINISHED, OrderStatus.SCHEDULED);
    }

    public void setTimeScheduled(LocalDateTime timeScheduled) {
        if (timeScheduled == null) {
            throw new IllegalArgumentException("The scheduled time cannot be null");
        }
        this.timeScheduled = timeScheduled;
    }

    /**
     * Set the time when the cleaning job is finished. Null values allowed for jobs that are not
     * finished. For flexibility, an order can be completed up to a day before it was scheduled.
     * Note! This does not set the OrderStatus field to FINISHED.
     *
     * @param timeFinished the time and date when the job was finished, nullable.
     */
    public void setTimeFinished(LocalDateTime timeFinished) {
        if (timeFinished == null) {
            this.timeFinished = NOT_FINISHED;
        } else if (timeFinished.isBefore(timeScheduled.minusDays(1))) {
            throw new IllegalArgumentException("An order cannot be finished more than a day before its scheduled");
        } else {
            this.timeFinished = timeFinished;
        }
    }

    /**
     * Sets the status of the current order from a set of values in OrderStatus. Setting the status
     * to FINISHED before setting a non-null value to timeFinished will throw an exception.
     *
     * @param orderStatus the status to be set
     * @throws IllegalArgumentException if trying to set FINISHED while timeFinished is null.
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        if (orderStatus == null) {
            throw new IllegalArgumentException("The status of the order cannot be null");
        }
        if (timeFinished == null && orderStatus.equals(OrderStatus.FINISHED)) {
            throw new IllegalArgumentException("Cannot set an order to finished before setting the finished time");
        }
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getRoomId() {
        return roomId;
    }

    public Optional<LocalDateTime> getTimeFinished() {
        return Optional.ofNullable(timeFinished);
    }

    public LocalDateTime getTimeScheduled() {
        return timeScheduled;
    }

    public String toString() {
        return String.format("[OrderId: %d, Status: %s, EmployeeId: %d, RoomId %d, Scheduled: %s, Finished: %s]",
                orderId, orderStatus.toString(), employeeId, roomId,
                timeScheduled, timeFinished == null ? "Not finished" : timeFinished);
    }
}
