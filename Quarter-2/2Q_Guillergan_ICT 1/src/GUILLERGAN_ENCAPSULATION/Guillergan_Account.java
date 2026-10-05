
package GUILLERGAN_ENCAPSULATION;

/**
 * ---------------------------------------------------------
 * Subject:     Computer Programming
 * Class:       Guillergan_Account.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        October 5, 2026
 * Description: Initializing variables and functions for Encapsulation
 * ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

public class Guillergan_Account {
    private long acc_no;
    private String name, email, bankName;
    private float amount;
    
    public String getBank() {
        return bankName;
    }
    
    public void setBank(String bankName) {
        this.bankName = bankName;
    }
    public long getAcc_no() {
        return acc_no;
    }
    
    public void setAcc_no(long acc_no) {
        this.acc_no = acc_no;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public float getAmount() {
        return amount;
    }
    
    public void setAmount(float amount) {
        this.amount=amount;
    }
}


