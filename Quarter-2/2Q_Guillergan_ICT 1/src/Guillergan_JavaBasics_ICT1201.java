/**
 * ---------------------------------------------------------
 * Subject:    Computer Programming
 * Class:      Guillergan_JavaBasics_ICT1201
 * Author:     chanchanjeu
 * Section:    ICT 12-01
 * Date:       Sep 5, 2026 12:53:10 PM
 * Description: Worksheet / Laboratory Activity Implementation
 * ---------------------------------------------------------
 *
 * @author chanchanjeu
 */
public class Guillergan_JavaBasics_ICT1201 {
    // Introduction method
    public void introduction(String name, String hobby, int age) {
        System.out.println("Good day I'm " + name);
        System.out.println(" age " + age);
        System.out.println("My hobbies are " + hobby);
        System.out.println("-~-~-~***~-~-~-");
    }
    
    // Main method
    public static void main(String[] args) {
        
        // Create object (Object Instantiation)
        Guillergan_JavaBasics_ICT1201 callClass = new Guillergan_JavaBasics_ICT1201();
        
        callClass.introduction("Gab", "Programming and Sleeping", 16);
        callClass.introduction("Jojo", "Programming and Sleeping (also i guess...)", 17);
    }
}