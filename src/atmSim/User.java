package atmSim;

/**
 * User class helps us to simulate actions performed by ATM, ie. withdrawing / depositing money etc.
 * Balance field is static, therefore, as long application is running we can perform numerous of deposits and changes will be "saved".
 * If application will be closed balance returns to default amount set in a class.
 */
public class User {

    /**
     * Current balance, value changes only by performing save() method
     */
    private static double balance;
    /**
     * New balance value which adds current balance to value prompted in operation (deposit or withdrawal) , value changes in deposit() method. Return to zero with save() method.
     * Note - if withdraw happens then transaction value will be negative (therefore "balance + (- transaction)"
     */
    private static double newBalance;
    /**
     * Holds value deposited / withdraw by user which is later displayed in recipe.
     */
    private static double transaction;
    private String password;

    User(String pswd){
        balance = 600.00;
        password = pswd;
    }

    /**
     * Returns current balance
     * @return Value of current balance
     */
    public double getSavings() {return balance;}

    /**
     * Returns amount deposited / withdraw by user.
     * @return Value which was prompted in deposit or withdrawal operation
     */
    public double getTransaction() {return transaction;}

    /**
     *
     * @return
     */
    public double getNewBalance() {return newBalance;}

    /**
     * Method which will deposit prompted amount of money and sets new balance.
     * @param amount Amount of money to deposit
     */
    public void deposit(double amount){
        newBalance = amount + balance;
        transaction = amount;
    }

    /**
     * Method which allow money withdrawal. Checks if desired amount is available to user, returns TRUE if money is available, otherwise FALSE.
     * @param amount Amount to withdraw
     * @return True if balance is higher, otherwise False
     */
    public boolean withdraw(double amount){
        if (amount > balance){
            return false;
        } else {
            newBalance = balance - amount;
            transaction = amount;
            return true;
        }
    }

    /**
     * Saves current status of balance
     */
    public void save(){
        balance = newBalance;
        newBalance = 0;
    }

    /**
     * Checks if prompted password is correct
     * @param input Password of type String
     * @return True if input matches
     */
    public boolean checkPassword(String input){
        return password.equals(input);
    }
}
