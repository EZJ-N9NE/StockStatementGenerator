package com.sen300stocks;

public class Transaction {
    private String type;
    private String symbol;
    private double pricePerShare;
    private long numberOfShares;

    public Transaction(String type, String symbol, double pricePerShare, long numberOfShares) {
        this.type = type;
        this.symbol = symbol;
        this.pricePerShare = pricePerShare;
        this.numberOfShares = numberOfShares;
    }

    public double getTotalAmount() {
        return pricePerShare * numberOfShares;
    }

    public boolean isBuy() {
        return "buy".equalsIgnoreCase(type);
    }

    public boolean isSell() {
        return "sell".equalsIgnoreCase(type);
    }

    public String getType() { return type; }
    public String getSymbol() { return symbol; }
    public double getPricePerShare() { return pricePerShare; }
    public long getNumberOfShares() { return numberOfShares; }
}
