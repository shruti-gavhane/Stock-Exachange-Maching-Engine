package datastructures;

import models.Order;
import models.Side;

import java.util.*;

public class OrderBook {

    private final TreeMap<Double, LinkedList<Order>> bids =
            new TreeMap<>(Collections.reverseOrder());

    private final TreeMap<Double, LinkedList<Order>> asks =
            new TreeMap<>();

    public synchronized void addOrder(Order order) {
        TreeMap<Double, LinkedList<Order>> map = mapFor(order.getSide());
        map.putIfAbsent(order.getPrice(), new LinkedList<>());
        map.get(order.getPrice()).add(order);
    }

    public synchronized Order getBestBuy() {
        if (bids.isEmpty()) return null;
        return bids.firstEntry().getValue().peek();
    }

    public synchronized Order getBestSell() {
        if (asks.isEmpty()) return null;
        return asks.firstEntry().getValue().peek();
    }

    public synchronized void removeTop(Side side) {
        TreeMap<Double, LinkedList<Order>> map = mapFor(side);
        if (map.isEmpty()) return;

        Map.Entry<Double, LinkedList<Order>> entry = map.firstEntry();
        entry.getValue().poll();

        if (entry.getValue().isEmpty()) {
            map.remove(entry.getKey());
        }
    }

    public synchronized void removeOrder(Order order) {
        TreeMap<Double, LinkedList<Order>> map = mapFor(order.getSide());
        LinkedList<Order> list = map.get(order.getPrice());

        if (list != null) {
            list.remove(order);
            if (list.isEmpty()) {
                map.remove(order.getPrice());
            }
        }
    }

    public synchronized void printOrderBook() {
        System.out.println("\n--- ORDER BOOK ---");

        System.out.println("BIDS:");
        bids.forEach((price, list) -> {
            int totalQty = list.stream().mapToInt(Order::getRemainingQty).sum();
            System.out.println(price + " -> " + totalQty);
        });

        System.out.println("ASKS:");
        asks.forEach((price, list) -> {
            int totalQty = list.stream().mapToInt(Order::getRemainingQty).sum();
            System.out.println(price + " -> " + totalQty);
        });
    }

    private TreeMap<Double, LinkedList<Order>> mapFor(Side side) {
        return side == Side.BUY ? bids : asks;
    }
}


