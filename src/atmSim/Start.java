package atmSim;

import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;

import javax.swing.*;

/**
 * Holds main method for launching application. Sets up custom look and feel, in case of error - default LaF will be used.
 * Application simulates behavior of ATM with simple flow control - reading card,perform action,calculate deposit, etc.
 */
public class Start {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            try {
                FlatNordIJTheme.setup();
                AppFrame.getFrame().setVisible(true);
            } catch (Exception e){
                AppFrame.getFrame().setVisible(true);
            }
        });
    }
}
