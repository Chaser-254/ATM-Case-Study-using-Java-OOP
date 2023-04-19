import java.util.Objects;

public class BankDatabase {

    private final Account[] accounts;

    public BankDatabase(){
        accounts = new Account[2];
        accounts[0] = new Account(1234,54321,1000.0,1200.0);
        accounts[1] = new Account(98765,56789,200.0,200.0);
    }
    private Account getAccount(int accountNumber){
        for (Account currentAccount : accounts){
            if (currentAccount.getAccountNumber() == accountNumber)
                return currentAccount;
        }
        return null;
    }
    public boolean authenticateUser(int userAccountNumber, int userPIN){
        Account userAccount = getAccount(userAccountNumber);

        if (userAccount != null)
            return userAccount.validatePIN(userPIN);
        else
            return false;
    }
    public double getAvailableBalance(int userAccountNumber){
        return Objects.requireNonNull(getAccount(userAccountNumber)).getAvailableBalance();
    }
    public double getTotalBalance(int userAccountNumber){
        return Objects.requireNonNull(getAccount(userAccountNumber)).getTotalBalance();
    }
    public void credit(int userAccountNumber, double amount){
        Objects.requireNonNull(getAccount(userAccountNumber)).credit(amount);
    }
    public void debit(int userAccountNumber, double amount){
        Objects.requireNonNull(getAccount(userAccountNumber)).debit(amount);
    }
}
