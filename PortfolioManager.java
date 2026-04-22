/*
 * File: PortfolioManager.java
 * Author: Ryan Baker
 * Purpose: Menu-driven Stock Portfolio Application that records transactions
 *          and displays transaction history + current portfolio holdings.
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDateTime;

public class PortfolioManager {

    // Transaction history list (required by rubric)
    private ArrayList<TransactionHistory> portfolioList = new ArrayList<>();

    public static void main(String[] args) {
        PortfolioManager app = new PortfolioManager();
        app.run();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        String accountName = "Ryan Baker"; // You can change this to any other name if you want
        int option = -1;

        while (option != 0) {
            printMenu(accountName);

            // Validate menu input
            if (!sc.hasNextInt()) {
                System.out.println("Error: Invalid menu option. Enter a number from 0 to 6.\n");
                sc.nextLine(); // clear invalid input
                continue;
            }

            option = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (option) {
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    break;
                case 1:
                    depositCash(sc);
                    break;
                case 2:
                    withdrawCash(sc);
                    break;
                case 3:
                    buyStock(sc);
                    break;
                case 4:
                    sellStock(sc);
                    break;
                case 5:
                    displayTransactionHistory(accountName);
                    break;
                case 6:
                    displayPortfolio();
                    break;
                default:
                    System.out.println("Error: Invalid menu option. Enter a number from 0 to 6.\n");
            }
        }

        sc.close();
    }

    private void printMenu(String name) {
        System.out.println("\n");
        printThickSeparator();
        System.out.println(centerString(name + " Brokerage Account", 70));
        printThickSeparator();
        System.out.println();
        System.out.println("  0 - Exit");
        System.out.println("  1 - Deposit Cash");
        System.out.println("  2 - Withdraw Cash");
        System.out.println("  3 - Buy Stock");
        System.out.println("  4 - Sell Stock");
        System.out.println("  5 - Display Transaction History");
        System.out.println("  6 - Display Portfolio");
        System.out.println();
        System.out.print("Enter option (0 to 6): ");
    }

    // ---------- Helpers ----------

    private String centerString(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int totalPadding = width - text.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    private void printThickSeparator() {
        System.out.println("══════════════════════════════════════════════════════════════════════");
    }

    private void printThinSeparator() {
        System.out.println("──────────────────────────────────────────────────────────────────────");
    }

    private String getValidatedDate(Scanner sc) {
        while (true) {
            System.out.print("Enter transaction date (MM/DD/YYYY): ");
            String date = sc.nextLine().trim();

            // Check format: MM/DD/YYYY
            if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
                System.out.println("Error: Invalid date format. Please use MM/DD/YYYY (e.g., 01/15/2022).\n");
                continue;
            }

            // Parse and validate date components
            String[] parts = date.split("/");
            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            // Validate month
            if (month < 1 || month > 12) {
                System.out.println("Error: Invalid month. Month must be between 01 and 12.\n");
                continue;
            }

            // Validate day based on month
            int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

            // Check for leap year
            if (isLeapYear(year)) {
                daysInMonth[1] = 29;
            }

            if (day < 1 || day > daysInMonth[month - 1]) {
                System.out.println("Error: Invalid day for month " + month + ". Day must be between 01 and "
                        + daysInMonth[month - 1] + ".\n");
                continue;
            }

            // Validate year is reasonable (1900-2100)
            if (year < 1900 || year > 2100) {
                System.out.println("Error: Invalid year. Please use a year between 1900 and 2100.\n");
                continue;
            }

            return date;
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private double getCashBalance() {
        double cash = 0.0;
        for (TransactionHistory t : portfolioList) {
            if (t.getTicker().equalsIgnoreCase("CASH")) {
                cash += t.getQty(); // deposits positive, withdrawals negative
            }
        }
        return cash;
    }

    private double getStockShares(String ticker) {
        double shares = 0.0;
        for (TransactionHistory t : portfolioList) {
            if (t.getTicker().equalsIgnoreCase(ticker)) {
                if (t.getTransType().equalsIgnoreCase("BUY")) {
                    shares += t.getQty();
                } else if (t.getTransType().equalsIgnoreCase("SELL")) {
                    shares -= t.getQty(); // SELL stored as positive shares sold
                }
            }
        }
        return shares;
    }

    // ---------- Menu Actions ----------

    private void depositCash(Scanner sc) {
        String date = getValidatedDate(sc);

        System.out.print("Enter deposit amount: ");
        if (!sc.hasNextDouble()) {
            System.out.println("Error: Deposit amount must be a number.");
            sc.nextLine();
            return;
        }
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Error: Deposit amount must be positive.");
            return;
        }

        // CASH deposit: qty positive, costBasis 1.00
        portfolioList.add(new TransactionHistory("CASH", date, "DEPOSIT", amount, 1.00));
        System.out.println("Deposit successful. CASH balance = " + getCashBalance());
    }

    private void withdrawCash(Scanner sc) {
        String date = getValidatedDate(sc);

        System.out.print("Enter withdrawal amount: ");
        if (!sc.hasNextDouble()) {
            System.out.println("Error: Withdrawal amount must be a number.");
            sc.nextLine();
            return;
        }
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive.");
            return;
        }

        double cash = getCashBalance();
        if (amount > cash) {
            System.out.println("Error: Withdrawal amount cannot be more than CASH available (" + cash + ").");
            return;
        }

        // CASH withdraw: qty negative, costBasis 1.00
        portfolioList.add(new TransactionHistory("CASH", date, "WITHDRAW", -amount, 1.00));
        System.out.println("Withdrawal successful. CASH balance = " + getCashBalance());
    }

    private void buyStock(Scanner sc) {
        String date = getValidatedDate(sc);

        System.out.print("Enter stock ticker: ");
        String ticker = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter quantity of shares to buy: ");
        if (!sc.hasNextDouble()) {
            System.out.println("Error: Quantity must be a number.");
            sc.nextLine();
            return;
        }
        double qty = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter cost basis (price per share): ");
        if (!sc.hasNextDouble()) {
            System.out.println("Error: Cost basis must be a number.");
            sc.nextLine();
            return;
        }
        double price = sc.nextDouble();
        sc.nextLine();

        if (qty <= 0 || price <= 0) {
            System.out.println("Error: Quantity and price must be positive.");
            return;
        }

        double totalCost = qty * price;
        double cash = getCashBalance();

        if (totalCost > cash) {
            System.out.println("Error: Not enough CASH to buy. Need " + totalCost + " but have " + cash + ".");
            return;
        }

        // 1) Stock BUY transaction
        portfolioList.add(new TransactionHistory(ticker, date, "BUY", qty, price));

        // 2) CASH WITHDRAW transaction (automatic)
        portfolioList.add(new TransactionHistory("CASH", date, "WITHDRAW", -totalCost, 1.00));

        System.out.println("Stock purchase successful. CASH balance = " + getCashBalance());
    }

    private void sellStock(Scanner sc) {
        String date = getValidatedDate(sc);

        System.out.print("Enter stock ticker: ");
        String ticker = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter quantity of shares to sell: ");
        if (!sc.hasNextDouble()) {
            System.out.println("Error: Quantity must be a number.");
            sc.nextLine();
            return;
        }
        double qty = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter cost basis (sell price per share): ");
        if (!sc.hasNextDouble()) {
            System.out.println("Error: Cost basis must be a number.");
            sc.nextLine();
            return;
        }
        double price = sc.nextDouble();
        sc.nextLine();

        if (qty <= 0 || price <= 0) {
            System.out.println("Error: Quantity and price must be positive.");
            return;
        }

        double sharesOwned = getStockShares(ticker);
        if (qty > sharesOwned) {
            System.out.println("Error: Not enough shares to sell. You own " + sharesOwned + " shares.");
            return;
        }

        double proceeds = qty * price;

        // 1) Stock SELL transaction (store positive qty sold)
        portfolioList.add(new TransactionHistory(ticker, date, "SELL", qty, price));

        // 2) CASH DEPOSIT transaction (automatic)
        portfolioList.add(new TransactionHistory("CASH", date, "DEPOSIT", proceeds, 1.00));

        System.out.println("Stock sale successful. CASH balance = " + getCashBalance());
    }

    // ---------- Displays ----------

    private void displayTransactionHistory(String name) {
        System.out.println("\n");
        printThickSeparator();
        System.out.println(centerString(name + " Brokerage Account", 70));
        System.out.println(centerString("Transaction History", 70));
        printThickSeparator();
        System.out.println();

        System.out.printf("  %-12s %-10s %-12s %-14s %-15s%n",
                "Date", "Ticker", "Quantity", "Cost Basis", "Trans Type");
        printThinSeparator();

        for (TransactionHistory t : portfolioList) {
            System.out.printf("  %-12s %-10s %-12.1f %-14.2f %-15s%n",
                    t.getTransDate(),
                    t.getTicker(),
                    t.getQty(),
                    t.getCostBasis(),
                    t.getTransType());
        }
        printThickSeparator();
        System.out.println();
    }

    private void displayPortfolio() {
        // Loop through transaction history and compute final holdings
        Map<String, Double> holdings = new LinkedHashMap<>();

        for (TransactionHistory t : portfolioList) {
            String ticker = t.getTicker().toUpperCase();

            if (ticker.equals("CASH")) {
                holdings.put("CASH", holdings.getOrDefault("CASH", 0.0) + t.getQty());
            } else {
                double current = holdings.getOrDefault(ticker, 0.0);

                if (t.getTransType().equalsIgnoreCase("BUY")) {
                    current += t.getQty();
                } else if (t.getTransType().equalsIgnoreCase("SELL")) {
                    current -= t.getQty();
                }

                // Only store if not 0 (optional, but keeps output clean)
                if (Math.abs(current) < 0.0000001) {
                    holdings.remove(ticker);
                } else {
                    holdings.put(ticker, current);
                }
            }
        }

        System.out.println("\n");
        printThickSeparator();
        System.out.println(centerString("Portfolio as of: " + LocalDateTime.now(), 70));
        System.out.println(centerString("Current Holdings", 70));
        printThickSeparator();
        System.out.println();

        System.out.printf("  %-12s %-15s%n", "Ticker", "Quantity");
        printThinSeparator();

        // Ensure CASH prints even if 0
        if (!holdings.containsKey("CASH")) {
            holdings.put("CASH", 0.0);
        }

        for (Map.Entry<String, Double> entry : holdings.entrySet()) {
            System.out.printf("  %-12s %-15.1f%n", entry.getKey(), entry.getValue());
        }
        printThickSeparator();
        System.out.println();
    }
}
