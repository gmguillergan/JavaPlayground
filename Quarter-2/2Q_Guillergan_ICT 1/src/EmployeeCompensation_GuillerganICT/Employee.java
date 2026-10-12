
package EmployeeCompensation_GuillerganICT;

/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming
 * Class:       Employee.java
 * Author:      Gabriel Martin Guillergan
 * Section:     ICT 12-01
 * Date:        October 12, 2026
 * Description: Abstract base class representing an employee with encapsulated fields.
 * ---------------------------------------------------------
 */

public abstract class Employee {
    private String name;
    private String address;
    private int number;

    public Employee(String name, String address, int number) {
        this.name = name;
        this.address = address;
        this.number = number;
    }

    public abstract void displayEmployeeInformation();

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getNumber() {
        return number;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}