package Guillergan_InheritanceConcept;

/**
 * ---------------------------------------------------------
 Subject:    Computer Programming
 Class:      Vehicle.java
 Author:     chanchanjeu
 Section:    ICT 12-01
 Date:       Sep 28, 2026 2:22PM
 Description: Seatwork #2.1 / The parent class (Superclass)
 ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

public class Vehicle {

    protected String brand;
    protected int year;
    
    public Vehicle(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }
    
    public void startEngine() {
        System.out.println("Starting engine... ");
    }
}
