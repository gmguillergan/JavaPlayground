/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming 4
 * Class:       ReceiptFormatter.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        November 22, 2026
 * Description: Formats a clean thermal-style text receipt for FiveOS POS.
 * ---------------------------------------------------------
 */
package fiveos.service;

import fiveos.model.CartItem;
import fiveos.service.BillingService.BillSummary;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReceiptFormatter {

    private static final int RECEIPT_WIDTH = 42;

    public String formatReceipt(List<CartItem> items, BillSummary summary, String transId, String cashierInfo) {
        return formatReceipt(items, summary, transId, "Terminal #01", "Gab");
    }

    public String formatReceipt(List<CartItem> items, BillSummary summary, String transId, String terminal, String cashier) {
        StringBuilder sb = new StringBuilder();
        String divider = repeatChar('-', RECEIPT_WIDTH);
        String doubleDivider = repeatChar('=', RECEIPT_WIDTH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        sb.append(doubleDivider).append("\n");
        sb.append(centerText("39ERS REFRESHMENT KIOSK", RECEIPT_WIDTH)).append("\n");
        sb.append(centerText("Unit B, L.O Manabat Bldg, Burol,", RECEIPT_WIDTH)).append("\n");
        sb.append(centerText("Molino I, City of Bacoor, Cavite", RECEIPT_WIDTH)).append("\n");
        sb.append(centerText("https://39ers-molino1.com", RECEIPT_WIDTH)).append("\n");
        sb.append(doubleDivider).append("\n");
        sb.append(String.format("Date/Time: %s\n", sdf.format(new Date())));
        sb.append(String.format("Terminal : %s\n", terminal));
        sb.append(String.format("Cashier  : %s\n", cashier));
        sb.append(String.format("Trans ID : %s\n", transId));
        sb.append(divider).append("\n");
        sb.append(String.format("%-20s %4s %7s %8s\n", "ITEM", "QTY", "PRICE", "TOTAL"));
        sb.append(divider).append("\n");

        int totalItemsCount = 0;
        for (CartItem item : items) {
            String name = truncate(item.getProduct().getName(), 20);
            int qty = item.getQuantity();
            double price = item.getProduct().getPrice();
            double lineTotal = item.getLineTotal();
            totalItemsCount += qty;

            sb.append(String.format("%-20s %4d %7.2f %8.2f\n", name, qty, price, lineTotal));
        }

        sb.append(divider).append("\n");
        sb.append(formatKeyValue("Items Count", String.valueOf(totalItemsCount))).append("\n");
        sb.append(formatCurrencyLine("Subtotal", summary.getSubtotal())).append("\n");
        sb.append(formatCurrencyLine("VAT (12%)", summary.getVatAmount())).append("\n");
        if (summary.isSeniorDiscountApplied()) {
            sb.append(formatCurrencyLine("Senior Disc. (20%)", -summary.getDiscountAmount())).append("\n");
        } else {
            sb.append(formatCurrencyLine("Senior Disc. (20%)", 0.00)).append("\n");
        }
        sb.append(doubleDivider).append("\n");
        sb.append(formatCurrencyLine("TOTAL AMOUNT DUE", summary.getTotalDue())).append("\n");
        sb.append(divider).append("\n");
        sb.append(formatCurrencyLine("Cash Tendered", summary.getCashTendered())).append("\n");
        sb.append(formatCurrencyLine("Change Due", summary.getChangeDue())).append("\n");
        sb.append(doubleDivider).append("\n");
        sb.append(centerText("For your own records. Not a BIR invoice.", RECEIPT_WIDTH)).append("\n\n");
        sb.append(centerText("Thank you for refreshing at 39ers", RECEIPT_WIDTH)).append("\n");
        sb.append(centerText("COME AGAIN!", RECEIPT_WIDTH)).append("\n");
        sb.append(doubleDivider);

        return sb.toString();
    }

    private String formatCurrencyLine(String label, double value) {
        String amountStr;
        if (value < 0) {
            amountStr = String.format("-PHP %.2f", Math.abs(value));
        } else {
            amountStr = String.format("PHP %.2f", value);
        }
        return formatKeyValue(label, amountStr);
    }

    private String formatKeyValue(String label, String value) {
        int spaces = RECEIPT_WIDTH - label.length() - value.length();
        if (spaces < 1) {
            spaces = 1;
        }
        return label + repeatChar(' ', spaces) + value;
    }

    private String truncate(String text, int maxLen) {
        if (text == null) {
            return "";
        }
        if (text.length() <= maxLen) {
            return text;
        }
        return text.substring(0, maxLen - 1) + ".";
    }

    private String centerText(String text, int width) {
        if (text == null || text.length() >= width) {
            return text;
        }
        int leftPadding = (width - text.length()) / 2;
        return repeatChar(' ', leftPadding) + text;
    }

    private String repeatChar(char c, int count) {
        StringBuilder sb = new StringBuilder(Math.max(0, count));
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
