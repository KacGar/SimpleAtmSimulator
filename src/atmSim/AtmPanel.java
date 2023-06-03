package atmSim;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * Main class which has all contents of application. Every panel content is available through public static methods.
 */
public class AtmPanel extends JPanel {

    /**
     * Singleton (followed with private constructor)
     */
    private static JPanel panel = new AtmPanel();

    /**
     * Private constructor which initializes default panel displayed in application
     */
    private AtmPanel(){
        var startPanel = start();
        setLayout(new BorderLayout());
        add(startPanel,BorderLayout.CENTER);
        this.updateUI();
    }

    /**
     * Start panel which every user see before using ATM, the button INSERT CARD act's as user enters card into a reader.
     * @return JPanel object
     */
    public static JPanel start() {
        JLabel label = new JLabel("Please insert you card.");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(40f));

        JButton btn = new JButton("INSERT CARD");
        btn.setFont(btn.getFont().deriveFont(30f));
        btn.setFocusable(false);
        btn.setActionCommand("cardReading");
        btn.addActionListener(Controller.getAction());

        var panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(label,BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(btn);

        panel.add(topPanel);
        panel.add(bottomPanel);
        return panel;
    }

    //getters of every panel
    public static JPanel getPanel(){return panel;}
    public static JPanel getLoadingScreen(){return new AtmPanel.LoadingScreen();}
    public static JPanel getLoginPanel(){return new AtmPanel.LoginPanel();}
    public static JPanel getUserPanel(){return new AtmPanel.UserPanel();}
    public static JPanel getFinancePanel(){return new AtmPanel.FinancePanel();}
    public static JPanel getCustomAmountPanel() {return new AtmPanel.CustomAmountPanel();}
    public static JPanel getConfirmationPanel(){return new AtmPanel.ConfirmationPanel();}
    public static JPanel getFarewellPanel(){return new AtmPanel.FarewellPanel();}

    /**
     * Panel which acts as "loading screen" to simulate work done in background.
     */
    private static class LoadingScreen extends JPanel{

        LoadingScreen(){
            JLabel label = new JLabel("Please wait");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(label.getFont().deriveFont(40f));

            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());
            topPanel.add(label,BorderLayout.CENTER);

            JLabel label2 = new JLabel("processing");
            label2.setHorizontalAlignment(SwingConstants.CENTER);
            label2.setFont(label.getFont().deriveFont(40f));

            JPanel middlePanel = new JPanel();
            middlePanel.setLayout(new BorderLayout());
            middlePanel.add(label2,BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new FlowLayout());
            JProgressBar jProgressBar = new JProgressBar();
            jProgressBar.setString("Pending...");
            jProgressBar.setStringPainted(true);
            jProgressBar.setPreferredSize(new Dimension(200,30));
            jProgressBar.setIndeterminate(true);

            bottomPanel.add(jProgressBar);

            setLayout(new GridLayout(0,1));
            add(topPanel);
            add(middlePanel);
            add(bottomPanel);
        }
    }

    /**
     * Panel with input field where user enters PIN number
     */
    private static class LoginPanel extends JPanel{

        private LoginPanel(){
            JLabel label = new JLabel("Enter your PIN number");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(label.getFont().deriveFont(40f));

            JPasswordField jPasswordField = new JPasswordField(4);
            jPasswordField.setFont(jPasswordField.getFont().deriveFont(30f));
            jPasswordField.setEchoChar('*');
            jPasswordField.setActionCommand("password");
            jPasswordField.addActionListener(Controller.getAction());

            PlainDocument document = (PlainDocument) jPasswordField.getDocument();
            document.setDocumentFilter(new DocumentFilter(){
                @Override
                public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs){
                    try {
                        String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                        if (string.length() < 5) {
                            super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
                        }
                        if (string.length() == 4){
                           Controller.checkPassword(jPasswordField.getPassword());
                        }
                    }catch (BadLocationException e){
                        e.printStackTrace();
                    }


                }
            });

            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());
            topPanel.add(label,BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new FlowLayout());
            bottomPanel.add(jPasswordField);

            setLayout(new GridLayout(0,1));
            add(topPanel);
            add(bottomPanel);
        }
    }

    /**
     * Panel where user decides to either deposit or withdraw money
     */
    private static class UserPanel extends JPanel{

        UserPanel(){
           setLayout(new GridBagLayout());
           GridBagConstraints g = new GridBagConstraints();

            JLabel label = new JLabel("DEPOSIT");
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setFont(label.getFont().deriveFont(40f));

            JLabel label2 = new JLabel("WITHDRAW");
            label2.setHorizontalAlignment(SwingConstants.RIGHT);
            label2.setFont(label.getFont().deriveFont(40f));

            JButton btn1 = new JButton(" ");
            btn1.setFocusable(false);
            btn1.addActionListener(Controller.getAction());
            btn1.setActionCommand("deposit");
            btn1.setFont(btn1.getFont().deriveFont(30f));
            var panelBtn1 = new JPanel();
            panelBtn1.setLayout(new BorderLayout());
            panelBtn1.add(btn1, BorderLayout.CENTER);

            JButton btn2 = new JButton(" ");
            btn2.setFocusable(false);
            btn2.addActionListener(Controller.getAction());
            btn2.setActionCommand("withdraw");
            btn2.setFont(btn2.getFont().deriveFont(30f));
            var panelBtn2 = new JPanel();
            panelBtn2.setLayout(new BorderLayout());
            panelBtn2.add(btn2, BorderLayout.CENTER);

            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 1;
            g.weightx = 0.5;
            g.weighty = 0;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(panelBtn1,g);

            g.gridx = 1;
            g.weightx = 1;
            g.fill = GridBagConstraints.WEST;
            add(label,g);
            g.gridx = 2;
            g.weightx = 1;
            g.fill = GridBagConstraints.EAST;
            add(label2,g);
            g.gridx = 3;
            g.weightx = 0.5;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(panelBtn2,g);
        }
    }

    /**
     * Panel where predefined amounts are displayed with ability to input custom amount - here user decides what amount of money to deposit or withdraw
     */
    private static class FinancePanel extends JPanel{

        FinancePanel(){
            setLayout(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();

            JLabel label = new JLabel("50");
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setFont(label.getFont().deriveFont(40f));

            JLabel label2 = new JLabel("100");
            label2.setHorizontalAlignment(SwingConstants.LEFT);
            label2.setFont(label.getFont().deriveFont(40f));

            JLabel label3 = new JLabel("200");
            label3.setHorizontalAlignment(SwingConstants.LEFT);
            label3.setFont(label.getFont().deriveFont(40f));

            JLabel label4 = new JLabel("250");
            label4.setHorizontalAlignment(SwingConstants.RIGHT);
            label4.setFont(label.getFont().deriveFont(40f));

            JLabel label5 = new JLabel("500");
            label5.setHorizontalAlignment(SwingConstants.RIGHT);
            label5.setFont(label.getFont().deriveFont(40f));

            JLabel label6 = new JLabel("Different amount");
            label6.setHorizontalAlignment(SwingConstants.RIGHT);
            label6.setFont(label.getFont().deriveFont(40f));

            JButton btn1 = new JButton(" ");
            btn1.setFocusable(false);
            btn1.addActionListener(Controller.getAction());
            btn1.setActionCommand("50");
            btn1.setFont(btn1.getFont().deriveFont(30f));
            var panelBtn1 = new JPanel();
            panelBtn1.setLayout(new BorderLayout());
            panelBtn1.add(btn1, BorderLayout.CENTER);

            JButton btn2 = new JButton(" ");
            btn2.setFocusable(false);
            btn2.addActionListener(Controller.getAction());
            btn2.setActionCommand("100");
            btn2.setFont(btn2.getFont().deriveFont(30f));
            var panelBtn2 = new JPanel();
            panelBtn2.setLayout(new BorderLayout());
            panelBtn2.add(btn2, BorderLayout.CENTER);

            JButton btn3 = new JButton(" ");
            btn3.setFocusable(false);
            btn3.addActionListener(Controller.getAction());
            btn3.setActionCommand("200");
            btn3.setFont(btn3.getFont().deriveFont(30f));
            var panelBtn3 = new JPanel();
            panelBtn3.setLayout(new BorderLayout());
            panelBtn3.add(btn3, BorderLayout.CENTER);

            JButton btn4 = new JButton(" ");
            btn4.setFocusable(false);
            btn4.addActionListener(Controller.getAction());
            btn4.setActionCommand("250");
            btn4.setFont(btn4.getFont().deriveFont(30f));
            var panelBtn4 = new JPanel();
            panelBtn4.setLayout(new BorderLayout());
            panelBtn4.add(btn4, BorderLayout.CENTER);

            JButton btn5 = new JButton(" ");
            btn5.setFocusable(false);
            btn5.addActionListener(Controller.getAction());
            btn5.setActionCommand("500");
            btn5.setFont(btn5.getFont().deriveFont(30f));
            var panelBtn5 = new JPanel();
            panelBtn5.setLayout(new BorderLayout());
            panelBtn5.add(btn5, BorderLayout.CENTER);

            JButton btn6 = new JButton(" ");
            btn6.setFocusable(false);
            btn6.addActionListener(Controller.getAction());
            btn6.setActionCommand("customAmount");
            btn6.setFont(btn6.getFont().deriveFont(30f));
            var panelBtn6 = new JPanel();
            panelBtn6.setLayout(new BorderLayout());
            panelBtn6.add(btn6, BorderLayout.CENTER);


            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 1;
            g.weightx = 0.5;
            g.weighty = 0.2;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(panelBtn1,g);
            g.gridx = 1;
            g.weightx = 1;
            g.fill = GridBagConstraints.WEST;
            add(label,g);
            g.gridx = 2;
            g.weightx = 1;
            g.fill = GridBagConstraints.EAST;
            add(label2,g);
            g.gridx = 3;
            g.weightx = 0.5;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(panelBtn2,g);
            //next row
            g.gridx = 0;
            g.gridy = 1;
            g.gridwidth = 1;
            g.weightx = 0.5;
            g.weighty = 0.2;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(panelBtn3,g);
            g.gridx = 1;
            g.weightx = 1;
            g.fill = GridBagConstraints.WEST;
            add(label3,g);
            g.gridx = 2;
            g.weightx = 1;
            g.fill = GridBagConstraints.EAST;
            add(label4,g);
            g.gridx = 3;
            g.weightx = 0.5;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(panelBtn4,g);
            //next row
            g.gridx = 0;
            g.gridy = 2;
            g.gridwidth = 1;
            g.weightx = 0.5;
            g.weighty = 0.2;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(panelBtn5,g);
            g.gridx = 1;
            g.weightx = 1;
            g.fill = GridBagConstraints.WEST;
            add(label5,g);
            g.gridx = 2;
            g.weightx = 1;
            g.fill = GridBagConstraints.EAST;
            add(label6,g);
            g.gridx = 3;
            g.weightx = 0.5;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(panelBtn6,g);
        }
    }

    /**
     * Panel where user can enter his custom amount he wants to deposit or withdraw
     */
    private static class CustomAmountPanel extends JPanel{
        CustomAmountPanel(){
            JLabel label = new JLabel("Enter value");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(label.getFont().deriveFont(40f));

            JLabel label2 = new JLabel("Next  ");
            label2.setHorizontalAlignment(SwingConstants.RIGHT);
            label2.setFont(label2.getFont().deriveFont(40f));

            JTextField jTextField = new JTextField(6);
            jTextField.setFont(jTextField.getFont().deriveFont(30f));

            PlainDocument document = (PlainDocument) jTextField.getDocument();
            document.setDocumentFilter(new DocumentFilter(){
                @Override
                public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs){
                    try {
                        String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                        if (string.length() < 7) {
                            super.replace(fb, offset, length, text, attrs);
                            Controller.setInput(string);
                        }
                    }catch (BadLocationException e){
                        e.printStackTrace();
                    }
                }
            });

            JButton btn1 = new JButton(" ");
            btn1.setFocusable(false);
            btn1.addActionListener(Controller.getAction());
            btn1.setActionCommand("customEntered");
            btn1.setFont(btn1.getFont().deriveFont(30f));
            var panelBtn1 = new JPanel();
            panelBtn1.setLayout(new BorderLayout());
            panelBtn1.add(btn1, BorderLayout.CENTER);

            setLayout(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();

            g.gridx = 0;
            g.gridy = 0;
            g.weightx = 1;
            g.weighty = 0.2;
            g.gridwidth = 4;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(label,g);
            g.gridy = 1;
            g.weighty = 0.2;
            g.fill = GridBagConstraints.CENTER;
            add(jTextField,g);

            g.gridx = 2;
            g.gridy = 2;
            g.weightx = 1;
            g.weighty = 0.6;
            g.gridwidth = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(label2,g);
            g.gridx = 3;
            g.weightx = 0.1;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(panelBtn1,g);
        }
    }

    /**
     * Panel where user is asked if wants a recipe for current operation
     */
    private static class ConfirmationPanel extends JPanel {

        ConfirmationPanel(){
            setLayout(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();

            JLabel label = new JLabel("Print confirmation of this transaction?");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(label.getFont().deriveFont(40f));

            JLabel label2 = new JLabel("YES");
            label2.setHorizontalAlignment(SwingConstants.CENTER);
            label2.setFont(label2.getFont().deriveFont(40f));

            JLabel label3 = new JLabel("NO");
            label3.setHorizontalAlignment(SwingConstants.CENTER);
            label3.setFont(label3.getFont().deriveFont(40f));

            JButton btn1 = new JButton(" ");
            btn1.setFocusable(false);
            btn1.addActionListener(Controller.getAction());
            btn1.setActionCommand("yes");
            btn1.setFont(btn1.getFont().deriveFont(30f));
            var panelBtn1 = new JPanel();
            panelBtn1.setLayout(new BorderLayout());
            panelBtn1.add(btn1, BorderLayout.CENTER);

            JButton btn2 = new JButton(" ");
            btn2.setFocusable(false);
            btn2.addActionListener(Controller.getAction());
            btn2.setActionCommand("no");
            btn2.setFont(btn2.getFont().deriveFont(30f));
            var panelBtn2 = new JPanel();
            panelBtn2.setLayout(new BorderLayout());
            panelBtn2.add(btn2, BorderLayout.CENTER);

            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 4;
            g.weightx = 1;
            g.weighty = 0.2;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(label,g);

            g.gridy = 1;
            g.weightx = 0.4;
            g.gridwidth = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(panelBtn1,g);
            g.gridx = 1;
            g.weightx = 1;
            g.gridwidth = 1;
            g.fill = GridBagConstraints.WEST;
            add(label2,g);

            g.gridx = 2;
            g.weightx = 1;
            g.fill = GridBagConstraints.EAST;
            add(label3,g);
            g.gridx = 3;
            g.weightx = 0.4;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(panelBtn2,g);

        }
    }

    /**
     * Last farewell panel after which start panel will appear
     */
    private static class FarewellPanel extends JPanel{

        FarewellPanel(){
            JLabel label = new JLabel("Thank you very much!");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(label.getFont().deriveFont(40f));

            JLabel label2 = new JLabel("Have a nice day!");
            label2.setHorizontalAlignment(SwingConstants.CENTER);
            label2.setFont(label2.getFont().deriveFont(40f));

            setLayout(new GridLayout(0,1));
            add(label);
            add(label2);
        }
    }
}
