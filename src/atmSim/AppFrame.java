package atmSim;

import javax.swing.*;
import java.awt.*;

/**
 * Main frame window of application. Initializes and defines properties of frame window. Only one frame should exist therefore singleton format is used.
 */
public class AppFrame extends JFrame {

    private static final AppFrame frame = new AppFrame();

    /**
     * Private constructor which initializes default properties of frame object
     */
    private AppFrame(){
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        width = width / 2;
        height = height / 2;
        setSize(width,height);
        setMinimumSize(new Dimension(600,200));
        setTitle("ATM Simulator");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(AtmPanel.getPanel(), BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    /**
     * Getter of application frame object
     * @return JFrame object
     */
    public static final JFrame getFrame(){return frame;}


}
