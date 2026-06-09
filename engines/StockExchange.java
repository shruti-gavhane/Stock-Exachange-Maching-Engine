package engines;

import datastructures.*;
import models.*;

import java.util.*;

public class StockExchange {

    private final Map<String, OrderBook> books = new HashMap<>();
    private final OrderRegistry registry = new OrderRegistry();
    private final TradeHistory tradeHistory = new TradeHistory();

    private final Map<String, MatchingEngine> engines = new HashMap<>();

    private OrderBook getBook(String stock) {
        return books.computeIfAbsent(stock, s -> new OrderBook());
    }

    private MatchingEngine getEngine(String stock) {
        return engines.computeIfAbsent(stock,
                s -> new MatchingEngine(getBook(s), tradeHistory));
    }

    public synchronized void placeOrder(String trader, String stock,
                           Side side, OrderType type,
                           double price, int qty) {
       
    if (trader == null || trader.isBlank())
            throw new IllegalArgumentException("Trader ID cannot be empty.");
        if (stock == null || stock.isBlank())
            throw new IllegalArgumentException("Stock symbol cannot be empty.");
        if (qty <= 0)
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        if (type == OrderType.LIMIT && price <= 0)
            throw new IllegalArgumentException("Limit price must be greater than zero.");

        String id = UUID.randomUUID().toString();
        Order order = new Order(id, trader, stock, side, type, price, qty);

        registry.add(order);

        if (type == OrderType.MARKET) {
            if (side == Side.BUY) order.setPrice(Double.MAX_VALUE);
            else order.setPrice(0);
        }

        OrderBook book = getBook(stock);
        MatchingEngine engine = getEngine(stock);

        book.addOrder(order);
        engine.match();

        // Cancel remaining market order
        if (type == OrderType.MARKET && order.getRemainingQty() > 0) {
            order.setStatus(Status.CANCELLED);
            book.removeOrder(order);
            registry.remove(id);
        }
    }

    public synchronized void cancelOrder(String id) {

        Order order = registry.get(id);
        if (order == null) {
            System.out.println("Order not found: " + id);
            return;
        }

        if (order.getStatus() == Status.FILLED ||
            order.getStatus() == Status.CANCELLED) {
            System.out.println("Cannot cancel — already " + order.getStatus());
            return;
        }

        OrderBook book = getBook(order.getStock());
        MatchingEngine engine = getEngine(order.getStock());

        order.setStatus(Status.CANCELLED);
        book.removeOrder(order);
        registry.remove(id);

        engine.match(); // FIX

        System.out.println("Cancelled: " + order);
    }

    public void showOrderBook() {
    System.out.println("\n===== ALL ORDER BOOKS =====");
    for (String stock : books.keySet()) {
        System.out.println("\nStock: " + stock);
        books.get(stock).printOrderBook();
    }
}

public void showTrades() {
    tradeHistory.printTrades();
}
}

