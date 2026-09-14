package Guillergan_Employee;
/**
 * ---------------------------------------------------------
 Subject:    Computer Programming
 Class:      Employee.java
 Author:     chanchanjeu
 Section:    ICT 12-01
 Date:       Sep 14, 2026 2:56:27PM
 Description: Worksheet 2.4 / Creating Inheritance with Method Overriding
 ---------------------------------------------------------
 *
 * @author chanchanjeu
 */
class Employee {
    protected final String name;
    protected final double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public double calculateBonus() {
        return salary * 0.10;
    }

    public void displayInfo() {
        System.out.println("Name    : " + name);
        System.out.println("Salary  : $" + salary);
        System.out.println("Bonus   : $" + calculateBonus());
    }
}
