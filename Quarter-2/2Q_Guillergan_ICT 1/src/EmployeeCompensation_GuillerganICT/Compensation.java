
package EmployeeCompensation_GuillerganICT;

/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming
 * Class:       Compensation.java
 * Author:      Gabriel Martin Guillergan
 * Section:     ICT 12-01
 * Date:        October 12, 2026
 * Description: Subclass extending Employee that computes bonuses, 13th-month pay, and annual packages.
 * ---------------------------------------------------------
 */

public class Compensation extends Employee {
    private double monthlySalary;

    public Compensation(String name, String address, int number, double monthlySalary) {
        super(name, address, number);
        setMonthlySalary(monthlySalary);
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    // Setter with validation
    public void setMonthlySalary(double monthlySalary) {
        if (monthlySalary >= 0) {
            this.monthlySalary = monthlySalary;
        } else {
            System.out.println("Invalid salary! Defaulting to 0.0");
            this.monthlySalary = 0.0;
        }
    }

    // 5% Monthly Bonus
    public double getMonthlyBonus() {
        return monthlySalary * 0.05;
    }

    // Monthly Gross Compensation
    public double getMonthlyCompensation() {
        return monthlySalary + getMonthlyBonus();
    }

    // Annual Basic Salary
    public double getAnnualBasicSalary() {
        return monthlySalary * 12;
    }

    // Annual Bonus
    public double getAnnualBonus() {
        return getMonthlyBonus() * 12;
    }

    // 13th Month Pay
    public double get13thMonthPay() {
        return getAnnualBasicSalary() / 12;
    }

    // Annual Compensation
    public double getAnnualCompensation() {
        return getAnnualBasicSalary() + getAnnualBonus() + get13thMonthPay();
    }

    // Method overriding
    @Override
    public void displayEmployeeInformation() {
        System.out.println("=========================================");
        System.out.println("        EMPLOYEE INFORMATION");
        System.out.println("=========================================");
        System.out.println("Name            : " + getName());
        System.out.println("Address         : " + getAddress());
        System.out.println("Employee Number : " + getNumber());
    }
}