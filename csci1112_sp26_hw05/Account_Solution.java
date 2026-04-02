/*--------------------------------------------------------------------------
GWU CSCI 1112 Spring 2026
author: <your name>, Charles Peeke 

TODO - Summarize this class

--------------------------------------------------------------------------*/

import java.text.DecimalFormat;

public class Account {
    // TODO:
    public String accountName;
    public int accountNumber;
    public double balance;

    // TODO: 
    public Account(String name) {
        this.accountName = name;
        this.balance = 0;
        assignAccountNumber();
    }

    // TODO:
    private void assignAccountNumber() {
        // TODO: 
        // Assign account number
        // The accountNumber is the sum of the ascii values of the account name
        for (int i = 0; i < this.accountName.length(); i++) {
            this.accountNumber += (int) this.accountName.charAt(i);
        }
    }

    // Getters and setters
    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit and Withdraw Methods

    // TODO: 
    public void deposit(double amount) {
        // TODO: 
        this.balance += amount;
    }

    // TODO: 
    public void withdraw(double amount) {
        // TODO:
        this.balance -= amount;
    }

    // TODO:
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        String s = "";
        s += "Account Name: " + this.accountName + " \t";
        s += "Account Number: " + this.accountNumber + " \t";
        s += "Balance: " + df.format(this.balance);
        return s;
    }
}
