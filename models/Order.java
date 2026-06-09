
package models;
import java.util.Objects;

public class Order {
    private String orderId;
    private String traderId;
    private String stock;
    private Side side;
    private OrderType orderType;
    private double price;
    private int quantity;
    private int filledQty;
    private Status status;
    private long timestamp;

    public Order(String orderId, String traderId, String stock,
                 Side side, OrderType orderType,
                 double price, int quantity) {
        this.orderId = orderId;
        this.traderId = traderId;
        this.stock = stock;
        this.side = side;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
        this.filledQty = 0;
        this.status = Status.PENDING;
        this.timestamp = System.nanoTime();
    }

    public int getRemainingQty() {
        return quantity - filledQty;
    }

    // Getters
    public String getOrderId() { return orderId; }
    public String getTraderId() { return traderId; }
    public String getStock() { return stock; }
    public Side getSide() { return side; }
    public OrderType getOrderType() { return orderType; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getFilledQty() { return filledQty; }
    public Status getStatus() { return status; }
    public long getTimestamp() { return timestamp; }

    // Setters
    public void setFilledQty(int qty) { this.filledQty = qty; }
    public void setStatus(Status status) { this.status = status; }
    public void setPrice(double price) { this.price = price; }

    // toString, equals & hashCode (IMPORTANT)
    @Override
    public String toString() {
        return String.format("[%s | %s | %s | %s | %s | price=%.2f | qty=%d | filled=%d | %s]",
                orderId, traderId, stock, side, orderType,
                price, quantity, filledQty, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

}
