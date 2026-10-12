
package EmployeeCompensation_GuillerganICT;

import java.util.Scanner;

/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming
 * Class:       EmployeeCompensation_GuillerganICT.java
 * Author:      Gabriel Martin G. Guillergan
 * Section:     ICT 12-01
 * Date:        October 12, 2026
 * Description: Main runner class demonstrating Polymorphism and Scanner input for compensation computation.
 * ---------------------------------------------------------
 */

public class EmployeeCompensation_GuillerganICT {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("   EMPLOYEE COMPENSATION SYSTEM SETUP");
        System.out.println("=========================================");

        // User Inputs
        System.out.print("Enter Employee Name   : ");
        String name = scanner.nextLine();

        System.out.print("Enter Address         : ");
        String address = scanner.nextLine();

        System.out.print("Enter Employee Number : ");
        int number = scanner.nextInt();

        System.out.print("Enter Monthly Salary  : PHP ");
        double salary = scanner.nextDouble();

        System.out.println();

        // Polymorphism: Employee reference pointing to a Compensation object
        Employee emp = new Compensation(name, address, number, salary);

        // Display Employee Details
        emp.displayEmployeeInformation();

        // Cast to Compensation to access compensation-specific methods
        if (emp instanceof Compensation) {
            Compensation comp = (Compensation) emp;

            System.out.println("=========================================");
            System.out.println("         COMPENSATION BREAKDOWN");
            System.out.println("=========================================");
            System.out.printf("Monthly Salary             : PHP %,12.2f%n", comp.getMonthlySalary());
            System.out.printf("5%% Monthly Bonus           : PHP %,12.2f%n", comp.getMonthlyBonus());
            System.out.printf("Monthly Gross Compensation : PHP %,12.2f%n", comp.getMonthlyCompensation());
            System.out.println("-----------------------------------------");
            System.out.printf("Annual Basic Salary        : PHP %,12.2f%n", comp.getAnnualBasicSalary());
            System.out.printf("13th Month Pay             : PHP %,12.2f%n", comp.get13thMonthPay());
            System.out.printf("Annual Bonus (Total)       : PHP %,12.2f%n", comp.getAnnualBonus());
            System.out.printf("Total Annual Compensation  : PHP %,12.2f%n", comp.getAnnualCompensation());
            System.out.println("=========================================");
        }
    }
}