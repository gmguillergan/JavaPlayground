package Guillergan_Employee;
/**
 * ---------------------------------------------------------
 Subject:    Computer Programming
 Class:      Employee.java
 Author:     chanchanjeu
 Section:    ICT 12-01
 Date:       Sep 14, 2026 3:00:34PM
 Description: Worksheet 2.4 / Creating Inheritance with Method Overriding
 ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

class Developer extends Employee {
    private String programmingLanguage;

    public Developer(String name, double salary, String programmingLanguage) {
        super(name, salary);
        this.programmingLanguage = programmingLanguage;
    }

    @Override
    public double calculateBonus() {
        return salary * 0.12;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Language: " + programmingLanguage);
    }
}