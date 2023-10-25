import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ATM {
    private Map<String, Account> accounts;

    public ATM() {
        accounts = new HashMap<>();
    }

    public void createAccount(String accountNumber, String pin, double balance) {
        accounts.put(accountNumber, new Account(accountNumber, pin, balance));
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean isValidAccount(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }
}

class Account {
    private String accountNumber;
    private String pin;
    private double balance;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean validatePin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class ProfessionalATMSimulation {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.createAccount("12345", "1234", 1000.0);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM");

        String accountNumber = null;
        String enteredPin = null;
        Account account = null;

        // Perform a single login
        while (account == null) {
            System.out.print("Enter your account number: ");
            accountNumber = scanner.next();

            if (!atm.isValidAccount(accountNumber)) {
                System.out.println("Invalid account number. Please try again.");
                continue;
            }

            System.out.print("Enter your PIN: ");
            enteredPin = scanner.next();

            account = atm.getAccount(accountNumber);

            if (account != null && !account.validatePin(enteredPin)) {
                System.out.println("Invalid PIN. Please try again.");
                account = null; // Reset the account to prompt login again
            }
        }

        System.out.println("Login successful. Welcome, " + accountNumber);

        while (true) {
            displayMainMenu();

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance(account);
                    break;
                case 2:
                    depositMoney(account, scanner);
                    break;
                case 3:
                    withdrawMoney(account, scanner);
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM");
                    break;
                default:
                    System.out.println("Invalid option");
            }

            System.out.print("Do you want to perform another transaction? (yes/no): ");
            String continueChoice = scanner.next();

            if (!continueChoice.equalsIgnoreCase("yes")) {
                System.out.println("Logging out. Goodbye, " + accountNumber);
                break;
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\nOptions:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void checkBalance(Account account) {
        System.out.println("Your balance is: $" + account.getBalance());
    }

    private static void depositMoney(Account account, Scanner scanner) {
        System.out.print("Enter the amount to deposit: $");
        double depositAmount = scanner.nextDouble();
        account.deposit(depositAmount);
        System.out.println("Deposited $" + depositAmount);
    }

    private static void withdrawMoney(Account account, Scanner scanner) {
        System.out.print("Enter the amount to withdraw: $");
        double withdrawAmount = scanner.nextDouble();
        if (account.withdraw(withdrawAmount)) {
            System.out.println("Withdrawn $" + withdrawAmount);
        } else {
            System.out.println("Insufficient balance");
        }
    }
}
