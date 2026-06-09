package datastructures;

import models.Trade;
import java.util.*;

/**
 * Stores recent trades in a bounded Deque (most recent first).
 * Uses ArrayDeque as a circular buffer — oldest trades are evicted
 * when MAX_SIZE is reached.
 */
public class TradeHistory {

    private final Deque<Trade> trades  = new ArrayDeque<>();
    private final int          MAX_SIZE;

    /** Creates a TradeHistory with a default capacity of 100. */
    public TradeHistory() {
        this.MAX_SIZE = 100;
    }

    /**
     * Creates a TradeHistory with a custom capacity.
     *
     * @param maxSize maximum number of trades to retain
     */
    public TradeHistory(int maxSize) {
        this.MAX_SIZE = maxSize;
    }

    /**
     * Prepends the trade to the front of the history.
     * If capacity is exceeded, the oldest trade is evicted.
     *
     * @param trade the executed trade to record
     */
    public void addTrade(Trade trade) {
        trades.addFirst(trade);
        if (trades.size() > MAX_SIZE) {
            trades.removeLast();
        }
    }

    /** Prints all retained trades, most recent first. */
    public void printTrades() {
        System.out.println("\n─── TRADE HISTORY ────────────");
        if (trades.isEmpty()) {
            System.out.println("  (no trades yet)");
        } else {
            trades.forEach(t -> System.out.println("  " + t));
        }
        System.out.println("──────────────────────────────");
    }
}