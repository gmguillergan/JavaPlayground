import java.util.Scanner;

/**
 * ---------------------------------------------------------
 * Subject:    Computer Programming
 * Class:      Guillergan_Operator_ICT1201
 * Author:     chanchanjeu
 * Section:    ICT 12-01
 * Date:       Sep 5, 2026 12:19:50 PM
 * Description: Worksheet / Laboratory Activity Implementation
 * ---------------------------------------------------------
 *
 * @author chanchanjeu
 */
public class Guillergan_Operator_ICT1201 {
    
    // Method to compare two grades
    public static void compareGrades(double grade1, double grade2) {
        if (grade1 > grade2) {
            System.out.println("First grade is higher.");
        } else if (grade1 < grade2) {
            System.out.println("Second grade is higher.");
        } else {
            System.out.println("Both grades are equal.");
        }
    }
    
    // Method to check if the grade is passing
    public static boolean isPassing(double grade) {
        return grade >= 75;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== STUDENT GRADE CHECKER =====");

        // Input grades
        System.out.print("Enter first grade: ");
        double grade1 = scanner.nextDouble();

        System.out.print("Enter second grade: ");
        double grade2 = scanner.nextDouble();

        // Call method to compare grades
        compareGrades(grade1, grade2);

        // Check pass/fail
        System.out.println("Is first grade passing? " + isPassing(grade1));
        System.out.println("Is second grade passing? " + isPassing(grade2));

        scanner.close();
    }
}