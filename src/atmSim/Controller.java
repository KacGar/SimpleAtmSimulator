package atmSim;

import javax.swing.*;
import java.awt.event.*;

/**
 * Handles all actions within application.
 */
public class Controller extends AbstractAction{

    /**
     * Singleton of Controller class (followed with private constructor)
     */
    private static final Action action = new Controller();
    /**
     * Since we're only simulating "reading" from a card, every time we handle same "user"
     */
    private static final User user = new User("1234");
    /**
     * True if used password is correct, used to determine what panel to display
     */
    private static boolean status;
    /**
     * Values 0 or 1 wihch determines if user wants to withdraw or deposit money
     */
    private static int userChoice = 0;
    /**
     * Value of money if user wants to prompt custom amount
     */
    private static String input;

    /**
     * Private constructor which prevents constructor multiple objects
     */
    private Controller(){}

    /**
     * Returns Controller object of {@link Action} type
     * @return Action object
     */
    public static Action getAction(){ return action;}

    /**
     * Setter for input field in Controller class, which is a value prompted by user if he wants to put custom amount to deposit or withdraw
     * @param s Prompted value of type String
     */
    public static void setInput(String s) {input = s;}


    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()){
            case "cardReading" ->{
                switchPanel(AtmPanel.getLoadingScreen());
                setTimer(2000,"login");
            }
            case "login" -> switchPanel(AtmPanel.getLoginPanel());
            case "depositWindow" -> {
                if (status){ switchPanel(AtmPanel.getUserPanel()); }
                else {
                    switchPanel(AtmPanel.getLoginPanel());
                    JOptionPane.showMessageDialog(AtmPanel.getPanel(),"Wrong Password!");
                }
            }
            case "deposit","withdraw" -> {
                userChoice = e.getActionCommand().equals("deposit") ? 1 :  -1;
                switchPanel(AtmPanel.getFinancePanel());
            }
            case "customEntered" ->{
                if (userChoice == 1){
                    double amountToDeposit = Double.parseDouble(input);
                    user.deposit(amountToDeposit);
                    switchPanel(AtmPanel.getConfirmationPanel());
                } else {
                    double amountToWithdraw = Double.parseDouble(input);
                    if (user.withdraw(amountToWithdraw)){
                        switchPanel(AtmPanel.getConfirmationPanel());
                    } else {
                        switchPanel(AtmPanel.getLoadingScreen());
                        JOptionPane.showMessageDialog(AtmPanel.getPanel(), "Unfortunately, your balance is too low.");
                        switchPanel(AtmPanel.getFinancePanel());
                    }
                }
            }
            case "customAmount" -> switchPanel(AtmPanel.getCustomAmountPanel());
            case "yes" -> {
                switchPanel(AtmPanel.getLoadingScreen());
                setTimer(3000,"farewell");
                String actionTaken = userChoice == 1 ? "Amount deposited: " : "Amount withdrawn: ";

                JOptionPane.showMessageDialog(AtmPanel.getPanel(), "Balance :" + user.getSavings() +"\n\n"+
                        actionTaken + user.getTransaction() + "\n\n" +
                        "Current balance : " + user.getNewBalance());
            }
            case "no" -> {
                switchPanel(AtmPanel.getLoadingScreen());
                setTimer(3000,"farewell");
            }
            case "farewell" -> {
                switchPanel(AtmPanel.getFarewellPanel());
                setTimer(3000,"start");
                user.save();
            }
            case "close" -> System.exit(0);
            case "start" -> switchPanel(AtmPanel.start());
            default -> {
                if (userChoice == 1){
                    double amountToDeposit = Double.parseDouble(e.getActionCommand());
                    user.deposit(amountToDeposit);
                    switchPanel(AtmPanel.getConfirmationPanel());
                } else {
                    double amountToWithdraw = Double.parseDouble(e.getActionCommand());
                    if (user.withdraw(amountToWithdraw)){
                        switchPanel(AtmPanel.getConfirmationPanel());
                    } else {
                        switchPanel(AtmPanel.getLoadingScreen());
                        JOptionPane.showMessageDialog(AtmPanel.getPanel(), "Unfortunately, your balance is too low.");
                        switchPanel(AtmPanel.getFinancePanel());
                    }
                }
            }
        }
    }

    /**
     * Switches panel which will be displaying with provided JPanel as a parameter.
     * @param panel JPanel which will be displayed
     */
    private static void switchPanel(JPanel panel){
        AtmPanel.getPanel().removeAll();
        AtmPanel.getPanel().add(panel);
        AtmPanel.getPanel().updateUI();
    }

    /**
     * Private method used internally only for simulating work of "reading" from credit card if it was there any.
     * @param delay Time after Timer will trigger (in milliseconds)
     * @param actionCommand Action command text - which action will be fired in actionPerformed method
     */
    private static void setTimer(int delay, String actionCommand){
        Timer timer = new Timer(delay,Controller.getAction());
        timer.setActionCommand(actionCommand);
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Checks if prompted password is valid.
     * @param password Array of characters from password text field.
     */
    public static void checkPassword(char[] password){
        switchPanel(AtmPanel.getLoadingScreen());

        StringBuilder sb = new StringBuilder();
        for (char c : password){
            sb.append(c);
        }
        status = user.checkPassword(sb.toString());

        Timer timer = new Timer(2000,Controller.getAction());
        timer.setActionCommand("depositWindow");
        timer.setRepeats(false);
        timer.start();
    }

}
