
package Abstract_GuillerganICT;

/**
 * --------------------------------------------------------- 
 * Subject:     Computer Programming 
 * Class:       Abstract_GuillerganICT.java
 * Author:      Gabriel Martin Guillergan
 * Section:     ICT 12-01
 * Date:        October 12, 2026
 * Description: Main execution class for testing the Employee and Salary models.
 * ---------------------------------------------------------
 *
 * @author chanchanjeu
 */
public class Abstract_GuillerganICT {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("     EMPLOYEE SALARY INFORMATION");
        System.out.println("========================================\n");

        // ze reference using salary class
        Salary s = new Salary("Maria Santos", "Imus, Cavite", 101, 240000.00);

        // ze reference using employee class
        Employee e = new Salary("Juan Dela Cruz", "Sta. Cruz, Manila", 102, 360000.00);

        System.out.println("\n========================================");
        System.out.println("CALL MAILCHECK USING SALARY REFERENCE");
        System.out.println("========================================\n");
        s.mailCheck();

        System.out.println("\n========================================");
        System.out.println("CALL MAILCHECK USING EMPLOYEE REFERENCE");
        System.out.println("========================================\n");
        e.mailCheck();

        System.out.println("\n========================================");
        System.out.println("          PROGRAM END");
        System.out.println("========================================");
    }
}
