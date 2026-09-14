package Guillergan_Employee;
/**
 * ---------------------------------------------------------
 Subject:    Computer Programming
 Class:      Manager.java
 Author:     chanchanjeu
 Section:    ICT 12-01
 Date:       Sep 14, 2026 2:58:54PM
 Description: Worksheet 2.4 / Creating Inheritance with Method Overriding
 ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

class Manager extends Employee {
    private String department;

    public Manager(String name, double salary, String department) {
        super(name, salary);
        this.department = department;
    }

    @Override
    public double calculateBonus() {
        return salary * 0.15;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Department: " + department);
    }
}