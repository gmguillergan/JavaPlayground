
package Lesson2_4;
/**
 * ---------------------------------------------------------
 * Subject:     Computer Programming
 * Class:       Encapsulation and Abstraction
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        October 5, 2026
 * Description: Demonstrating Encapsulation and Abstraction
 *              in Java using an ATM / Bank Account model.
 * ---------------------------------------------------------
 *
 * @author chanchanjeu
 */

// Abstraction
abstract class BankService {
    private String serviceName;

    public BankService(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    // Users know WHAT these do, but not HOW each specific account handles it.
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
}

// Encapsulation

class SavingsAccount extends BankService {
    // This is encapsulation and cannot be directly altered from the outside.
    private String accountNumber;
    private double balance;

    public SavingsAccount(String accountNumber, double initialDeposit) {
        super("UPHBank Portal");
        this.accountNumber = accountNumber;
        
        // Controlled initialization via validation
        if (initialDeposit >= 0) {
            this.balance = initialDeposit;
        } else {
            this.balance = 0.0;
        }
    }

    // Getter for account number (read-only; no setter provided for immutability)
    public String getAccountNumber() {
        return accountNumber;
    }

    // Getter for balance (read-only directly; mutations must pass business checks)
    public double getBalance() {
        return balance;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Successfully deposited: $%.2f | New Balance: $%.2f%n", amount, balance);
        } else {
            System.out.println("Deposit failed: Amount must be greater than zero.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal failed: Amount must be greater than zero.");
        } else if (amount > balance) {
            System.out.println("Withdrawal failed: Insufficient funds.");
        } else {
            balance -= amount;
            System.out.printf("Successfully withdrew: $%.2f | Remaining Balance: $%.2f%n", amount, balance);
        }
    }
}

// Main Class
public class Encapsulation_Abstraction {
    public static void main(String[] args) {
        BankService ops = new SavingsAccount("SA-258398225", 500.00);

        System.out.println("Service: " + ops.getServiceName());
        System.out.println("----------------------------------------");

        // Valid operations
        ops.deposit(250.00);
        ops.withdraw(100.00);

        // Encapsulation prevents unauthorized operations
        ops.withdraw(800.00); // Exceeds balance
        ops.deposit(-50.00);  // Invalid value
    }
}