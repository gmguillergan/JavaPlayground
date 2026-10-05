
package ABSTRACT_GUILLERGANICT1201;

/**
 * ---------------------------------------------------------
 * Subject:     Computer Programming
 * Class:       Abstract_GuillerganICT.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        October 5, 2026
 * Description: Main execution class for testing the Employee and Salary models.
 * ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

public class Abstract_GuillerganICT {

    public static void main(String[] args) {
       
        Salary s = new Salary("Juan Dela Cruz", "Sta. Cruz, Manila", 3, 36000.00);
        Employee e = new Salary("Felipe San Jose", "Mactan, Cebu", 2, 24000.00);

        System.out.println("========================================");
        System.out.println("        EMPLOYEE SALARY SYSTEM          ");
        System.out.println("========================================");

        s.mailCheck();
        e.mailCheck();
        
        System.out.println();
        
        System.out.println("Successfully recorded the following:");
        System.out.println("----------------------------------------");
        
        
        System.out.println("Employee Name     : " + s.getName());
        System.out.println("Address           : " + s.getAddress());
        System.out.println("Employee Number   : " + s.getEmployeeNumber());
        System.out.println("Annual Salary     : " + s.getAnnualSalary());
        
        System.out.println();

        
        System.out.println("Employee Name     : " + e.getName());
        System.out.println("Address           : " + e.getAddress());
        System.out.println("Employee Number   : " + e.getEmployeeNumber());
        System.out.println("Annual Salary     : " + ((Salary) e).getAnnualSalary()); // we cast e because it is employee reference
        
        System.out.println("========================================");
    }
}