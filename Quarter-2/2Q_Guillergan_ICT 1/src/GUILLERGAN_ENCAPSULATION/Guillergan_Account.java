
package GUILLERGAN_ENCAPSULATION;

/**
 * ---------------------------------------------------------
 * Subject:     Computer Programming
 * Class:       Guillergan_Account.java
 * Author:      Gabriel Martin Guillergan
 * Section:     ICT 12-01
 * Date:        October 5, 2026
 * Description: Initializing variables and functions for Encapsulation
 * ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

public class Guillergan_Account {
    private long accNo;
    private String name, email, bankName;
    private double amount;
    
    public String getBank() {
        return bankName;
    }
    
    public void setBank(String bankName) {
        this.bankName = bankName;
    }

    public long getAcc_no() {
        return accNo;
    }
    
    public void setAcc_no(long accNo) {
        this.accNo = accNo;
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
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        if (amount >= 0) {
            this.amount = amount;
        }
    }
}


