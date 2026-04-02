/*--------------------------------------------------------------------------
GWU CSCI 1112 Spring 2026
author: <your name>, Charles Peeke 

TODO - Summarize this class

--------------------------------------------------------------------------*/

public class Bank {
    // TODO:
    public String bankName;
    private Account[] accounts;
    private Queue transactions;
    private int count;
    
    /// TODO: 
    public Bank(String name) {
        this.bankName = name;
        this.accounts = new Account[10];
        this.transactions = new Queue();
        this.count = 0;
    }

    /// TODO:
    public int createAccount(String name) {
        Account newAcc = new Account(name);
        // Check if accounts is full and reallocate if necessary
        if (this.accounts[this.accounts.length - 1] != null) {
            reallocateAccounts();
        }
        // Add new account to accounts
        for (int i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i] == null) {
                this.accounts[i] = newAcc;
                break;
            }
        }
        // Increment count
        this.count++;
        // Return account number
        return newAcc.getAccountNumber();
    }

    /// TODO:
    public double closeAccount(int accountNumber) {
        // Iterative search accounts for accountNumber
        // If found, remove account from accounts
        // Return remaining balance
        // If not found, do nothing, return -1

        for (int i = 0; i < this.accounts.length; i++) {
            if (this.accounts[i].getAccountNumber() == accountNumber) {
                double remaining = this.accounts[i].getBalance();
                // Shift remaining accounts to the left
                for (int j = i; j < this.accounts.length - 1; j++) {
                    this.accounts[j] = this.accounts[j + 1];
                }
                // Make sure last account is null to avoid duplicates
                this.accounts[this.accounts.length - 1] = null;
                this.count--;
                return remaining;
            }
        }
        return -1;
    }

    /// TODO: 
    public double getBalance(int accountNumber) {
        // Iterative search accounts for accountNumber
        // If found, return balance
        // If not found, do nothing, return -1
        for (int i = 0; i < this.count; i++) {
            if (this.accounts[i].getAccountNumber() == accountNumber) {
                return this.accounts[i].getBalance();
            }
        }
        return -1;
    }

    /// TODO:
    public void deposit(int accountNumber, double amount) {
        if (amount < 0) {
            return;
        }
        
        // Iterative search accounts for accountNumber
        // If found, deposit amount
        // If not found, do nothing

        for (int i = 0; i < this.count; i++) {
            if (this.accounts[i].getAccountNumber() == accountNumber) {
                this.accounts[i].deposit(amount);
            }
        }
    }

    /// TODO:
    public void withdraw(int accountNumber, double amount) {
        if (amount < 0) {
            return;
        }
        
        // Iterative search accounts for accountNumber
        // If found, withdraw amount
        // If not found, do nothing

        for (int i = 0; i < this.count; i++) {
            if (this.accounts[i].getAccountNumber() == accountNumber) {
                this.accounts[i].withdraw(amount);
            }
        }
    }

    // -------------------------------------   
    // Transactions Methods
    /// TODO:
    public void addTransaction(Transaction t) {
        transactions.enqueue(t);
    }

    /// TODO:
    public void processTransaction() {
        Transaction t = transactions.dequeue();
        if (t == null) {
            return;
        }
        // Perform transaction based on operation
        // If operation is deposit, call deposit
        // If operation is withdraw, call withdraw
        // If operation is close, call closeAccount
        // If operation is create, call createAccount

        if (t.getOperation() == 1) {
            deposit(t.getAccountNumber(), t.getAmount());
        } else if (t.getOperation() == 2) {
            withdraw(t.getAccountNumber(), t.getAmount());
        } else if (t.getOperation() == 0) {
            // Iterative search accounts
            // If account is found, close the account
            // If account is not found, create the account

            boolean found = false;
            for (int i = 0; i < this.count; i++) {
                if (this.accounts[i].getAccountNumber() == t.getAccountNumber()) {
                    closeAccount(t.getAccountNumber());
                    found = true;
                    break;
                }
            }
            // If account is not found, create the account
            // Convert Account number to ascii value as account name
            if (!found) {
                // Convert Account number to ascii value
                // convert ascii value to char
                // convert char to string

                int ascii = t.getAccountNumber();
                char c = (char) ascii;
                String name = "" + c;
                createAccount(name);
            }
        }
    }

    /// TODO:
    private void reallocateAccounts () {
        // create new array with double the size
        // copy old array into new array
        // set old array to new array

        Account[] newAccounts = new Account[this.accounts.length * 2];
        for (int i = 0; i < this.accounts.length; i++) {
            newAccounts[i] = this.accounts[i];
        }
        this.accounts = newAccounts;
    }

    // -------------------------------------   
    /// TODO:  
    public String getBankName() {
        return bankName;
    }

    public int getNumberOfAccounts() {
        return this.count;
    }

    public int getNumberOfTransactions() {
        return transactions.size();
    }

    // TODO:
    @Override
    public String toString() {
        String s = "Bank Name: " + bankName + " \n";
        s += "Number of Accounts: " + count + " | ";
        s += "Number of Pending Transactions: " + transactions.size() + "\n";
        s += "Accounts: \n";
        for (int i = 0; i < count; i++) {
            s += accounts[i].toString() + " \n";
        }
        return s;
    }
}
