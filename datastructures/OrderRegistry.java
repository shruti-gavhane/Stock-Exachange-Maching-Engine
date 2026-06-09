package datastructures;

import models.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * Fast O(1) lookup of orders by ID.
 * Also handles removal so cancelled orders don't leak memory.
 */
public class OrderRegistry {

    private final Map<String, Order> map = new HashMap<>();

    /** Registers a new order. */
    public void add(Order order) {
        map.put(order.getOrderId(), order);
    }

    /**
     * Retrieves an order by ID.
     *
     * @return the Order, or null if not found
     */
    public Order get(String id) {
        return map.get(id);
    }

    /**
     * Removes an order from the registry (call on cancellation).
     *
     * @param id the order ID to remove
     */
    public void remove(String id) {
        map.remove(id);
    }
}