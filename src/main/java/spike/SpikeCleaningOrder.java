package spike;

import domain.CleaningOrder;
import domain.OrderStatus;
import service.ServiceRunner;
import service.cleaningorder.*;

import java.sql.SQLException;
import java.time.*;
import java.util.List;

/**
 * Demonstrator for service layer classes that mainly interact with the Cleaning Order class.
 *
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-27
 */
public class SpikeCleaningOrder {

    public static void main(String[] args) throws SQLException {
        ServiceRunner runner = new ServiceRunner();

        LocalDateTime dateTime1 = LocalDateTime.of(2025, 4, 15, 12, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2025, 4, 15, 13, 0);

        CleaningOrder order1 = new CleaningOrder(1, 1, dateTime1);
        System.out.println("Saving order to database, before and after:");
        System.out.println(order1);
        order1 = runner.execute(new SaveCleaningOrderService(order1));
        System.out.println(order1);
        System.out.println();

        System.out.println("Updating the order with new information:");
        order1.setTimeFinished(dateTime2);
        order1.setOrderStatus(OrderStatus.FINISHED);
        order1 = runner.execute(new UpdateCleaningOrderService(order1));
        System.out.println(order1);
        System.out.println();

        boolean result = runner.execute(new DeleteCleaningOrderService(order1));
        System.out.printf("Order with id: %d deleted: %s\n", order1.getOrderId(), result);
        System.out.println();

        System.out.println("All orders:");
        List<CleaningOrder> allOrders = runner.execute(new GetAllCleaningOrdersService());
        allOrders.forEach(System.out::println);
        System.out.println();

        System.out.println("Retrieving order with id 2 from database:");
        CleaningOrder retrievedOrder = runner.execute(new GetCleaningOrderByIdService(2));
        System.out.println(retrievedOrder);
        System.out.println();

        System.out.println("Retrieving all orders that are assigned to employee with id 1");
        List<CleaningOrder> cleaningOrders = runner.execute(new GetCleaningOrdersByEmployeeService(1));
        cleaningOrders.forEach(System.out::println);
        System.out.println();

        System.out.println("Retrieving all orders that are assigned to the room with id 1");
        cleaningOrders = runner.execute(new GetCleaningOrdersByRoomService(1));
        cleaningOrders.forEach(System.out::println);
    }
}