public class ATM {
    private boolean userAuthenticated;
    private int currentAccountNumber;
    private final Screen screen;
    private final Keypad keypad;
    private final CashDispenser cashDispenser;
    private final DepositSlot depositSlot;
    private final BankDatabase bankDatabase;


    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int DEPOSIT = 3;
    private static final int EXIT = 4;


    public ATM() {
        userAuthenticated = false;
        currentAccountNumber = 0;
        screen = new Screen();
        keypad = new Keypad();
        cashDispenser = new CashDispenser();
        depositSlot = new DepositSlot();
        bankDatabase = new BankDatabase();
    }

    public void run() {
        while (true) {
            while (!userAuthenticated) {
                screen.displayMessageLine("\nWelcome!");
                authenticateUser();
            }
            performTransactions();
            userAuthenticated = false;
            currentAccountNumber = 0;
            screen.displayMessageLine("\nThank you!");
        }
    }
        private void authenticateUser(){
            screen.displayMessageLine("/nPlease enter your account number: ");
            int accountNumber = keypad.getInput();
            screen.displayMessage("\nEnter your PIN: ");
            int pin = keypad.getInput();

            userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);

            if (userAuthenticated) {
                currentAccountNumber = accountNumber;
            } else
                screen.displayMessageLine("Invalid account number or PIN. Please try Again!");
        }
        private void performTransactions() {
            Transaction currentTransaction = null;

            boolean userExited = false;

            while (!userExited) {
                int mainMenuSelection = displayMainMenu();

                switch (mainMenuSelection) {
                    case BALANCE_INQUIRY, WITHDRAWAL, DEPOSIT -> {
                        assert false;
                        currentTransaction.run();
                    }
                    case EXIT -> {
                        screen.displayMessageLine("\nExiting the system!");
                        userExited = true;
                    }
                    default -> screen.displayMessageLine("\nYou did not enter a valid selection. Try again.");
                }
            }
        }
        private int displayMainMenu () {
            screen.displayMessageLine("\nMain menu:");
            screen.displayMessageLine("\1 - View my balance");
            screen.displayMessageLine("2 - Withdraw cash");
            screen.displayMessageLine("3 - Deposit funds");
            screen.displayMessageLine("4 - Exit\n");

            return keypad.getInput();
        }
        private Transaction createTransaction(int type){

            return switch (type) {
                case BALANCE_INQUIRY -> new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
                case WITHDRAWAL -> new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
                case DEPOSIT -> new Deposit(currentAccountNumber, screen, (Keypad) bankDatabase, keypad, depositSlot);
                default -> null;
            };
        }

    public void execute() {
    }
}