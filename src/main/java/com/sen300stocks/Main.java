package com.sen300stocks;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        String jsonFile = "C:\\Neumont\\Q4\\OpenSource\\StockStatementGenerator\\data\\stock_transations.by.account.holder.json";
        JSONParser parser = new JSONParser();
        JSONArray accounts = (JSONArray) parser.parse(new FileReader(jsonFile));
        String statementDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        for (Object obj : accounts) {
            JSONObject accountJson = (JSONObject) obj;

            long accountNumber = (Long) accountJson.get("account_number");
            String ssn = (String) accountJson.get("ssn");
            String firstName = (String) accountJson.get("first_name");
            String lastName = (String) accountJson.get("last_name");
            String email = (String) accountJson.get("email");
            String phone = (String) accountJson.get("phone");

            String fullName = firstName + " " + lastName;

            // Handle balance with "$" and commas
            String balanceStr = ((String) accountJson.get("beginning_balance"))
                    .replace("$", "")
                    .replace(",", "")
                    .trim();
            double beginningBalance = Double.parseDouble(balanceStr);

            AccountHolder holder = new AccountHolder(fullName, ssn, email, phone, accountNumber, beginningBalance);

            JSONArray transactionsJson = (JSONArray) accountJson.get("stock_trades");
            if (transactionsJson != null) {
                for (Object transObj : transactionsJson) {
                    JSONObject t = (JSONObject) transObj;

                    String type = ((String) t.get("type")).toLowerCase();
                    String symbol = (String) t.get("stock_symbol");

                    long shares = (Long) t.get("count_shares");

                    String priceStr = ((String) t.get("price_per_share"))
                            .replace("$", "")
                            .replace(",", "")
                            .trim();
                    double price = Double.parseDouble(priceStr);

                    Transaction transaction = new Transaction(type, symbol, price, shares);
                    holder.addTransaction(transaction);
                }
            }

            StatementGenerator.generateHTMLReport(holder, statementDate);
        }

        System.out.println("✅ All statements generated.");
    }
}