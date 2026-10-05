package Guillergan_Employee;

/**
 * ---------------------------------------------------------
 Subject:    Computer Programming
 Class:      Employee.java
 Author:     chanchanjeu
 Section:    ICT 12-01
 Date:       September 14, 2026
 Description: Worksheet 2.4 / Creating Inheritance with Method Overriding
 ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

class Main {
    public static void main(String[] args) {
        Manager manager = new Manager("Bob", 80000, "Sales");
        Developer dev = new Developer("Carol", 70000, "Java");
        
        System.out.println("=== Manager Info ===");
        manager.displayInfo();
        
        System.out.println("\n=== Developer Info ===");
        dev.displayInfo();
    }
    
}
