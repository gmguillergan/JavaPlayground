
package GUILLERGAN_ENCAPSULATION;

/**
 * ---------------------------------------------------------
 * Subject:     Computer Programming
 * Class:       Test_Guillergan.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        October 5, 2026
 * Description: Creating bank account and get info through Encapsulation
 * ---------------------------------------------------------
 *
 * @author chanchanjeu
 */
public class Test_Guillergan {
    public static void main(String[] args) {
        Guillergan_Account acc = new Guillergan_Account();
        
        acc.setBank("Bank of the Philippine Islands");
        acc.setAcc_no(669121373);
        acc.setName("Gabriel Martin G. Guillergan");
        acc.setEmail("riel@gmguillergan.com");
        acc.setAmount(14396.22f);
        
        System.out.println("You've successfully opened an account. \nWelcome to " + acc.getBank() + "!"
                + "\n\n ******** DETAILS ********"
                + "\nAcc. No       : " + acc.getAcc_no()
                + "\nName          : " + acc.getName()
                + "\nEmail         : " + acc.getEmail()
                + "\nBalance       : PHP " + acc.getAmount());
        
    }
}
