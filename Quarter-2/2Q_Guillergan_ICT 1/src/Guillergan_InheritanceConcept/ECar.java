package Guillergan_InheritanceConcept;

/**
 * ---------------------------------------------------------
 Subject:    Computer Programming
 Class:      ECar.java
 Author:     chanchanjeu
 Section:    ICT 12-01
 Date:       Sep 28, 2026 2:35PM
 Description: Seatwork #2.1 / Another Subclass based on Car class...
 ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

class ECar extends Car {
    private int batteryCapacity = 1500;
    
    public ECar(String brand, int year, int numberOfDoors){
        super(brand, year, numberOfDoors);
        this.batteryCapacity = batteryCapacity;
    }
    
    @Override
    public void displayCarDetails() {
        super.displayCarDetails();
        System.out.println("Battery Capacity: " + batteryCapacity);
    }
}
