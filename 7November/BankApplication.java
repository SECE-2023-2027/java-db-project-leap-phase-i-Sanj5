import java.util.*;


abstract class BankAccount {
    private int balance;
    private final int pin;

    public BankAccount(int initialBalance, int pin) {
        this.balance = initialBalance;
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    protected void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPin() {
        return pin;
    }

    public abstract void deposit(Scanner sc);

    public abstract void withdraw(Scanner sc);
}


class Account extends BankAccount {
    public Account(int initialBalance, int pin) {
        super(initialBalance, pin);
    }

    @Override
    public void deposit(Scanner sc) {
        System.out.println("Enter amount to be deposited:");
        int amount = sc.nextInt();
        setBalance(getBalance() + amount);
        System.out.println("Amount deposited successfully. Current Balance: " + getBalance());
    }

    @Override
    public void withdraw(Scanner sc) {
        System.out.println("Enter amount to be withdrawn:");
        int amount = sc.nextInt();
        if (amount <= getBalance()) {
            setBalance(getBalance() - amount);
            System.out.println("Amount withdrawn successfully. Current Balance: " + getBalance());
        } else {
            System.out.println("Insufficient Balance");
        }
    }
}

public class BankApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Account account = new Account(1000, 1995);

        System.out.println("Welcome to the Banking Application\nPlease enter your PIN:");
        int userPin = sc.nextInt();

        if (userPin == account.getPin()) {
            while (true) {
                System.out.println("1. Deposit\n2. Withdraw\n3. Check Balance\n4. Exit");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        account.deposit(sc);
                        break;

                    case 2:
                        account.withdraw(sc);
                        break;

                    case 3:
                        System.out.println("Current Balance: " + account.getBalance());
                        break;

                    case 4:
                        System.out.println("Thank you! Exiting successfully...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid option! Please choose again.");
                        break;
                }
            }
        } else {
            System.out.println("Incorrect PIN");
        }
        
    }
}
