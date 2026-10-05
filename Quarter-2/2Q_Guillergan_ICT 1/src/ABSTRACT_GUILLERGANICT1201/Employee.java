
package ABSTRACT_GUILLERGANICT1201;

/**
 * ---------------------------------------------------------
 * Subject:     Computer Programming
 * Class:       Employee.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        October 5, 2026
 * Description: Abstract base class representing an employee.
 * ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

public abstract class Employee {
    protected String name;
    protected String address;
    protected int employeeNumber;

    public Employee(String name, String address, int employeeNumber) {
        this.name = name;
        this.address = address;
        this.employeeNumber = employeeNumber;
    }
    

    public void mailCheck() {
        System.out.println();
        System.out.println("Mailing a check to " + this.name + " at " + this.address);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }
}

class Salary extends Employee {
    private double annualSalary;

    public Salary(String name, String address, int employeeNumber, double annualSalary) {
        super(name, address, employeeNumber);
        setAnnualSalary(annualSalary);
    }

    public double getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(double newSalary) {
        if (newSalary >= 0.0) {
            this.annualSalary = newSalary;
        }
    }
    
    // Added function for future use hehe
    public double computePay() {
        return annualSalary / 12;
    }
}
