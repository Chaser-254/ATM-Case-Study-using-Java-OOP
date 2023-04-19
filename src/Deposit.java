public class Deposit extends Transaction{

    private final Keypad keypad;
    private final DepositSlot depositSlot;
    private static final int CANCELED = 0;

    public Deposit(int userAccountNumber,Screen atmScreen,Keypad atmKeypad,BankDatabase atmBankDatabase,DepositSlot atmDepositSlot){
        super(userAccountNumber, atmScreen, atmBankDatabase);

        keypad = atmKeypad;
        depositSlot = atmDepositSlot;
    }

    @Override
    public void execute(){
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        double amount = promptForDepositAmount();

        if (amount != CANCELED){
            screen.displayMessage("\nPlease insert a deposit envelope containing ");
            screen.displayDollarAmount(amount);
            screen.displayMessageLine(".");

            boolean envelopeReceived = depositSlot.isEnvelopeReceived();

            if (envelopeReceived){
                screen.displayMessageLine("""

                        Your envelope has been received.
                        Note: The money just deposited will not be available until we verify the amount of any enclosed cash and your check is clear.""");

                bankDatabase.credit(getAccountNumber(), amount);
            }
            else{
                screen.displayMessageLine("\nYou did not insert an " +
                        "envelope, so the ATM has canceled your transaction.");
            }
        }
        else{
            screen.displayMessageLine("\ncanceling transaction...");
        }
    }
    private double promptForDepositAmount(){
        Screen screen = getScreen();
        screen.displayMessage("\nPlease enter a deposit amount in " +
                "CENTS (or 0 to cancel): ");
        int input = keypad.getInput();

        if (input == CANCELED)
            return CANCELED;
        else{
            return (double) input / 100;
        }
    }
}
