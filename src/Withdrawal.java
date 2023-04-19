public class Withdrawal extends Transaction {
    private final Keypad keypad;
    private final CashDispenser cashDispenser;

    private final static int CANCELED = 6;

    public Withdrawal(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad, CashDispenser atmCashDispenser) {
        super(userAccountNumber, atmScreen, atmBankDatabase);

        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
    }

    @Override
    public void execute() {

        boolean cashDispensed = false;
        double availableBalance;

        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        do

        {
            int amount = displayMenuOfAmounts();

            if (amount != CANCELED) {
                availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());

                if (amount <= availableBalance) {
                    if (cashDispenser.isSufficientCashAvailable(amount)) {
                        bankDatabase.debit(getAccountNumber(), amount);

                        cashDispenser.dispenseCash(amount);
                        cashDispensed = true;

                        screen.displayMessageLine("\nYour cash has been" +
                                "dispensed. Please take your cash now.");
                    } //end if
                    else {
                        screen.displayMessageLine(
                                """

                                        Insufficient cash available in the ATM.
                                        Please choose a smaller amount.""");
                    }
                } else {
                    screen.displayMessageLine("""

                            Insufficient funds in your account.
                            Please choose a smaller amount.""");
                }
            } else {
                screen.displayMessageLine("\nCancelling transaction...");
                return;
            }
        }while(!cashDispensed);
    }
    private int displayMenuOfAmounts(){
        int userChoice = 0;

        Screen screen = getScreen();

        int[] amount = {0,20,40,60,100,200};

        while (userChoice == 0){
            screen.displayMessageLine("\nWithdrawal Menu:");
            screen.displayMessageLine("1 - $20");
            screen.displayMessageLine("2 - $40");
            screen.displayMessageLine("3 - $60");
            screen.displayMessageLine("4 - $100");
            screen.displayMessageLine("5 - $;200");
            screen.displayMessageLine("6 - Cancel transaction");
            screen.displayMessageLine("\nChoose a withdrawal amount: ");

            int input = keypad.getInput();

            switch (input) {
                case 1, 2, 3, 4, 5 -> userChoice = amount[input];
                default -> screen.displayMessageLine("\nInvalid selection. Try again.");
            }
        }
        return userChoice;
    }
}


