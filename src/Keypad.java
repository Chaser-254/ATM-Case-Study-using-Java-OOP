import java.util.Scanner;

public class Keypad extends BankDatabase {

    private final Scanner input;

    public Keypad(){
        input = new Scanner(System.in);
    }
    public int getInput(){
        return input.nextInt();
    }
}
