package engines;

import datastructures.OrderBook;
import datastructures.TradeHistory;
import models.Order;
import models.Side;
import models.Status;
import models.Trade;

/**
 * Core price-time priority matching engine.
 * Continuously matches the best bid against the best ask
 * while the spread is zero or negative (bid >= ask).
 */
public class MatchingEngine {

    private final OrderBook    orderBook;
    private final TradeHistory tradeHistory;
    private   int   tradeCounter = 1;

    /**
     *  orderBook the shared order book to match against
      tradeHistory the shared trade history to record into
     */
    public MatchingEngine(OrderBook orderBook, TradeHistory tradeHistory) {
        this.orderBook    = orderBook;
        this.tradeHistory = tradeHistory;
    }

    /**
     * Runs one full matching pass.
     * Keeps matching until no crossable pair exists.
     * Trade price is always the resting (sell) order's price — standard practice.
     */
    public void match() {
        while (true) {
            Order buy  = orderBook.getBestBuy();
            Order sell = orderBook.getBestSell();

            // Stop if either side is empty or spread hasn't crossed
            if (buy == null || sell == null)         break;
            if (buy.getPrice() < sell.getPrice())    break;

            int    qty   = Math.min(buy.getRemainingQty(), sell.getRemainingQty());
            double price = sell.getPrice();   // resting order sets the price

            // Update fill quantities
            buy.setFilledQty(buy.getFilledQty()   + qty);
            sell.setFilledQty(sell.getFilledQty() + qty);

            // Record the trade
           Trade trade = new Trade(
    "TRD" + (tradeCounter++),
    buy.getStock(),
    buy.getOrderId(),
    sell.getOrderId(),
    buy.getTraderId(),
    sell.getTraderId(),
    price,
    qty
);

tradeHistory.addTrade(trade);

                System.out.println("\nTrade Successful:");
                System.out.println("Stock : " + trade.getStock());
                System.out.println("Qty   : " + trade.getQuantity());
                System.out.println("Price : " + trade.getPrice());
                System.out.println("Buyer : " + trade.getBuyerTraderId());
                System.out.println("Seller: " + trade.getSellerTraderId());
                System.out.println("---------------------------------");

            // Update statuses and remove fully filled orders
            updateAndRemove(buy,  Side.BUY);
            updateAndRemove(sell, Side.SELL);
        }
    }

    // ── Private helpers ───────────────────────────────────────
    
    private void updateAndRemove(Order order, Side side) {
        if (order.getRemainingQty() == 0) {
            order.setStatus(Status.FILLED);
            orderBook.removeTop(side);
        } else {
            order.setStatus(Status.PARTIAL);
        }
    }
}