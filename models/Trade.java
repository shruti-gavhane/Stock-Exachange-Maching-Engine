package models;

/**
 * Immutable record of an executed trade between a buy and sell order.
 * All fields are final — no setters exist.
 */
public final class Trade {

    private final String tradeId;
    private final String stock;
    private final String buyOrderId;
    private final String sellOrderId;
    private final double price;
    private final int    quantity;
    private final long   timestamp;
    private final String buyerTraderId;
    private final String sellerTraderId;

    /**
     * @param tradeId     Unique trade identifier
     * @param stock       Stock ticker symbol
     * @param buyOrderId  ID of the matched buy order
     * @param sellOrderId ID of the matched sell order
     * @param price       Execution price
     * @param quantity    Number of units traded
     */
    public Trade(String tradeId, String stock,
             String buyOrderId, String sellOrderId,
             String buyerTraderId, String sellerTraderId,
             double price, int quantity) {

    this.tradeId = tradeId;
    this.stock = stock;
    this.buyOrderId = buyOrderId;
    this.sellOrderId = sellOrderId;

    this.buyerTraderId = buyerTraderId;   
    this.sellerTraderId = sellerTraderId; 

    this.price = price;
    this.quantity = quantity;
    this.timestamp = System.currentTimeMillis();
}

    public String getTradeId()     { return tradeId; }
    public String getStock()       { return stock; }
    public String getBuyOrderId()  { return buyOrderId; }
    public String getSellOrderId() { return sellOrderId; }
    public double getPrice()       { return price; }
    public int    getQuantity()    { return quantity; }
    public long   getTimestamp()   { return timestamp; }
    public String getBuyerTraderId() {
    return buyerTraderId;
}

public String getSellerTraderId() {
    return sellerTraderId;
}
    @Override
public String toString() {
    return String.format(
        "[%s | %s | Buyer: %s | Seller: %s | %d @ %.2f]",
        tradeId, stock, buyerTraderId, sellerTraderId, quantity, price
    );
}
}