/* 
 * File: TransactionHistory.java
 * Author: Ryan Baker
 * Purpose: Stores a single transaction record for a stock or CASH.
 */

public class TransactionHistory {

    // Private fields
    private String ticker;      // Stock ticker or "CASH"
    private String transDate;   // Date when transaction occurred (e.g., "9/20/2022")
    private String transType;   // BUY, SELL, DEPOSIT, WITHDRAW
    private double qty;         // Quantity (CASH uses dollars, stock uses shares)
    private double costBasis;   // Stock price per share; CASH always 1.00

    // Default constructor
    public TransactionHistory() {
        this.ticker = "";
        this.transDate = "";
        this.transType = "";
        this.qty = 0.0;
        this.costBasis = 0.0;
    }

    // Overloaded constructor
    public TransactionHistory(String ticker, String transDate, String transType, double qty, double costBasis) {
        this.ticker = ticker;
        this.transDate = transDate;
        this.transType = transType;
        this.qty = qty;
        this.costBasis = costBasis;
    }

    // Getters and setters
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getCostBasis() {
        return costBasis;
    }

    public void setCostBasis(double costBasis) {
        this.costBasis = costBasis;
    }
}
