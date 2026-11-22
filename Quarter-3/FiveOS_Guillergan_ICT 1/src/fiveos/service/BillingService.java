/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming 4
 * Class:       BillingService.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        November 22, 2026
 * Description: Pure calculation logic for POS subtotal, 12% VAT,
 *              20% Senior Citizen Discount, total amount due, and change.
 * ---------------------------------------------------------
 */
package fiveos.service;

import fiveos.model.CartItem;
import java.util.List;

public class BillingService {

    public static final double VAT_RATE = 0.12;
    public static final double SENIOR_DISCOUNT_RATE = 0.20;

    public static class BillSummary {
        private final double subtotal;
        private final double vatAmount;
        private final double discountAmount;
        private final double totalDue;
        private final double cashTendered;
        private final double changeDue;
        private final boolean seniorDiscountApplied;

        public BillSummary(double subtotal, double vatAmount, double discountAmount,
                           double totalDue, double cashTendered, double changeDue,
                           boolean seniorDiscountApplied) {
            this.subtotal = subtotal;
            this.vatAmount = vatAmount;
            this.discountAmount = discountAmount;
            this.totalDue = totalDue;
            this.cashTendered = cashTendered;
            this.changeDue = changeDue;
            this.seniorDiscountApplied = seniorDiscountApplied;
        }

        public double getSubtotal() {
            return subtotal;
        }

        public double getVatAmount() {
            return vatAmount;
        }

        public double getDiscountAmount() {
            return discountAmount;
        }

        public double getTotalDue() {
            return totalDue;
        }

        public double getCashTendered() {
            return cashTendered;
        }

        public double getChangeDue() {
            return changeDue;
        }

        public boolean isSeniorDiscountApplied() {
            return seniorDiscountApplied;
        }
    }

    public double computeSubtotal(List<CartItem> items) {
        double sum = 0.0;
        if (items != null) {
            for (CartItem item : items) {
                sum += item.getLineTotal();
            }
        }
        return roundCurrency(sum);
    }

    public double computeVat(double subtotal) {
        return roundCurrency(subtotal * VAT_RATE);
    }

    public double computeSeniorDiscount(double subtotal, boolean seniorEnabled) {
        if (!seniorEnabled) {
            return 0.0;
        }
        return roundCurrency(subtotal * SENIOR_DISCOUNT_RATE);
    }

    public double computeTotalDue(double subtotal, double vatAmount, double discountAmount) {
        double total = subtotal + vatAmount - discountAmount;
        return roundCurrency(Math.max(0.0, total));
    }

    public double computeChange(double cashTendered, double totalDue) {
        if (cashTendered < totalDue) {
            return 0.0;
        }
        return roundCurrency(cashTendered - totalDue);
    }

    public BillSummary calculateBill(List<CartItem> items, boolean seniorDiscountEnabled, double cashTendered) {
        double subtotal = computeSubtotal(items);
        double vat = computeVat(subtotal);
        double discount = computeSeniorDiscount(subtotal, seniorDiscountEnabled);
        double totalDue = computeTotalDue(subtotal, vat, discount);
        double change = computeChange(cashTendered, totalDue);
        return new BillSummary(subtotal, vat, discount, totalDue, cashTendered, change, seniorDiscountEnabled);
    }

    private double roundCurrency(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }
}
