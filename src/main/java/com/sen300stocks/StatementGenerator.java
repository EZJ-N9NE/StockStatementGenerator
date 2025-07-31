package com.sen300stocks;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StatementGenerator {

    public static void generateHTMLReport(AccountHolder holder, String statementDate) throws Exception {
        List<Transaction> transactions = holder.getTransactions();

        double cash = holder.getBeginningBalance();
        double stockHoldings = 0.0;

        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Statement for ")
                .append(holder.getName())
                .append("</title>")
                .append("<style>")
                .append("body { font-family: Arial; }")
                .append("table { border-collapse: collapse; width: 100%; }")
                .append("th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }")
                .append("th { background-color: #f2f2f2; }")
                .append("</style></head><body>");

        html.append("<h1>Monthly Statement</h1>")
                .append("<p><strong>Date:</strong> ").append(statementDate).append("</p>")
                .append("<p><strong>Name:</strong> ").append(holder.getName()).append("</p>")
                .append("<p><strong>SSN:</strong> ").append(holder.getSsn()).append("</p>")
                .append("<p><strong>Email:</strong> ").append(holder.getEmail()).append("</p>")
                .append("<p><strong>Phone:</strong> ").append(holder.getPhone()).append("</p>")
                .append("<p><strong>Account #:</strong> ").append(holder.getAccountNumber()).append("</p>");

        html.append("<h2>Transactions</h2>")
                .append("<table><tr>")
                .append("<th>Type</th>")
                .append("<th>Symbol</th>")
                .append("<th>Price/Share</th>")
                .append("<th>Shares</th>")
                .append("<th>Total</th>")
                .append("</tr>");

        for (Transaction t : transactions) {
            double total = t.getTotalAmount();
            if (t.isBuy()) {
                cash -= total;
                stockHoldings += total;
            } else if (t.isSell()) {
                cash += total;
                stockHoldings -= total;
            }

            html.append("<tr>")
                    .append("<td>").append(t.getType()).append("</td>")
                    .append("<td>").append(t.getSymbol()).append("</td>")
                    .append("<td>$").append(String.format("%.2f", t.getPricePerShare())).append("</td>")
                    .append("<td>").append(t.getNumberOfShares()).append("</td>")
                    .append("<td>$").append(String.format("%.2f", total)).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>");

        html.append("<h3>Ending Cash: $").append(String.format("%.2f", cash)).append("</h3>");
        html.append("<h3>Stock Holdings: $").append(String.format("%.2f", stockHoldings)).append("</h3>");

        html.append("</body></html>");

        Files.createDirectories(Paths.get("statements"));
        String fileName = "statements/statement_" + holder.getAccountNumber() + ".html";
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write(html.toString());
        }

        System.out.println("✅ Created statement: " + fileName);
    }
}
