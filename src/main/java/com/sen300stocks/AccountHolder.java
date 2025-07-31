package com.sen300stocks;

import java.util.ArrayList;
import java.util.List;

public class AccountHolder {
    private String name;
    private String ssn;
    private String email;
    private String phone;
    private long accountNumber;
    private double beginningBalance;
    private List<Transaction> transactions;

    public AccountHolder(String name, String ssn, String email, String phone, long accountNumber, double beginningBalance) {
        this.name = name;
        this.ssn = ssn;
        this.email = email;
        this.phone = phone;
        this.accountNumber = accountNumber;
        this.beginningBalance = beginningBalance;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public String getName() { return name; }
    public String getSsn() { return ssn; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public long getAccountNumber() { return accountNumber; }
    public double getBeginningBalance() { return beginningBalance; }
    public List<Transaction> getTransactions() { return transactions; }
}

