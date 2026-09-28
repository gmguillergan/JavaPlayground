package Guillergan_InheritanceConcept;

/**
 * ---------------------------------------------------------
 * Subject:     Computer Programming
 * Class:       Car.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        Sep 28, 2026 2:26PM
 * Description: Seatwork #2.1 / Subclass based on Vehicle class...
 * ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

class Car extends Vehicle {
    protected int numberOfDoors;

    public Car(String brand, int year, int numberOfDoors) {
        super(brand, year);
        this.numberOfDoors = numberOfDoors;
    }

    public void displayCarDetails() {
        System.out.println("Brand: " + brand);
        System.out.println("Year: " + year);
        System.out.println("Number Of Doors: " + numberOfDoors);
    }
}