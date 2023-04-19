public class CashDispenser {

    private final static int INITIAL_COUNT = 500;
    private int count;

    public CashDispenser(){
        count = INITIAL_COUNT;
    }
    public void dispenseCash(int amount){
        count -= amount;
    }
    public boolean isSufficientCashAvailable(int amount){
        int billsRequired = amount / 20;

        return count >= billsRequired;
    }
}
