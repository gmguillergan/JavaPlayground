package Guillergan_InheritanceConcept;

/**
 * ---------------------------------------------------------
 Subject:    Computer Programming
 Class:      Main.java
 Author:     chanchanjeu
 Section:    ICT 12-01
 Date:       Sep 28, 2026 2:40PM
 Description: Seatwork #2.1 / Main method to call classes
 ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

public class Main {

    public static void main(String[] args) {
        ECar byd = new ECar("Seal 5", 2026, 4);
        byd.startEngine();
        byd.displayCarDetails();
    }
    
}
