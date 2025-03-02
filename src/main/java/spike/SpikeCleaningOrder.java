package spike;

import db.DbConn;
import domain.CleaningOrder;
import domain.OrderStatus;
import service.cleaningorder.*;

import java.sql.SQLException;
import java.time.*;
import java.util.List;

/**
 * Demonstrator for service layer classes that mainly interact with the Cleaning Order class.
 * @author Simon Lundgren
 * @version 1.0
 * Created on: 2025-02-27
 */
public class SpikeCleaningOrder {

    public static void main(String[] args) throws SQLException {
        LocalDateTime dateTime1 = LocalDateTime.of(2025, 4, 15, 12, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2025, 4, 15, 13, 0);

        CleaningOrder order1 = new CleaningOrder(1, 1, dateTime1);
        System.out.println("Saving order to database, before and after:");
        System.out.println(order1);
        order1 = new SaveCleaningOrderService(order1).execute();
        System.out.println(order1);
        System.out.println();

        System.out.println("Updating the order with new information:");
        order1.setTimeFinished(dateTime2);
        order1.setOrderStatus(OrderStatus.FINISHED);
        order1 = new UpdateCleaningOrderService(order1).execute();
        System.out.println(order1);
        System.out.println();

        boolean result = new DeleteCleaningOrderService(order1).execute();
        System.out.printf("Order with id: %d deleted: %s\n", order1.getOrderId(), result);
        System.out.println();

        System.out.println("All orders:");
        List<CleaningOrder> allOrders = new GetAllCleaningOrdersService().execute();
        allOrders.forEach(System.out::println);
        System.out.println();

        System.out.println("Retrieving order with id 2 from database:");
        CleaningOrder retrievedOrder = new GetCleaningOrderByIdService(2).execute();
        System.out.println(retrievedOrder);
    }
}