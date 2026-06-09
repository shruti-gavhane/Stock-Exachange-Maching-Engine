package main;

import engines.*;
import models.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc  = new Scanner(System.in);
        StockExchange ex  = new StockExchange();

        while (true) {
            System.out.println("\n══════════════════════════════");
            System.out.println(" 1. Place Order");
            System.out.println(" 2. Cancel Order");
            System.out.println(" 3. Show Order Book");
            System.out.println(" 4. Show Trade History");
            System.out.println(" 5. Exit");
            System.out.print("Choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number 1–5.");
                continue;
            }

            switch (choice) {

                case 1 -> {
                    try {
                        System.out.print("Trader ID : "); String trader = sc.nextLine().trim();
                        System.out.print("Stock     : "); String stock  = sc.nextLine().trim().toUpperCase();
                        System.out.print("BUY/SELL  : "); Side side     = Side.valueOf(sc.nextLine().trim().toUpperCase());
                        System.out.print("LIMIT/MARKET : "); OrderType type = OrderType.valueOf(sc.nextLine().trim().toUpperCase());

                        double price = 0;
                        if (type == OrderType.LIMIT) {
                            System.out.print("Price     : ");
                            price = Double.parseDouble(sc.nextLine().trim());
                        }

                        System.out.print("Qty       : ");
                        int qty = Integer.parseInt(sc.nextLine().trim());

                        ex.placeOrder(trader, stock, side, type, price, qty);

                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 2 -> {
                    System.out.print("Order ID: ");
                    ex.cancelOrder(sc.nextLine().trim());
                }

                case 3 -> ex.showOrderBook();
                case 4 -> ex.showTrades();
                case 5 -> { System.out.println("Goodbye.");sc.close(); return; }

                default -> System.out.println("Please enter a number 1–5.");
            }
        }
    }
}