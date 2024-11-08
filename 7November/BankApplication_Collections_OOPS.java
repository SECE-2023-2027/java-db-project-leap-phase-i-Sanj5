import java.util.*;

class User {
    private final int pin;
    private final Account account;

    public User(int initialBalance, int pin) {
        this.pin = pin;
        this.account = new Account(initialBalance, pin);
    }

    public int getPin() {
        return pin;
    }

    public Account getAccount() {
        return account;
    }
}

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
public class BankApplication_Collections_OOPS {
    private static ArrayList<User> users = new ArrayList<>();
    private static HashSet<Integer> usedPins = new HashSet<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialize users
        addUser(1000, 1995);
        addUser(1500, 1234);
        addUser(2000, 4321);

        System.out.println("Welcome to the Banking Application\nPlease enter your PIN:");
        int userPin = sc.nextInt();

        User currentUser = authenticateUser(userPin);

        if (currentUser != null) {
            Account account = currentUser.getAccount();
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

    public static void addUser(int initialBalance, int pin) {
        if (usedPins.contains(pin)) {
            System.out.println("Error: Duplicate PIN. Cannot add user.");
        } else {
            users.add(new User(initialBalance, pin));
            usedPins.add(pin);
        }
    }

    public static User authenticateUser(int pin) {
        for (User user : users) {
            if (user.getPin() == pin) {
                return user;
            }
        }
        return null;
    }
}

