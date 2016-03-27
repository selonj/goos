package auctionsniper.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by L.x on 16-3-27.
 */
public class MainWindow extends JFrame {
    public static final String APPLICATION_WINDOW_NAME = "Auction Sniper Application";

    public MainWindow() throws HeadlessException {
        super("Auction Sniper Application");
        setName(APPLICATION_WINDOW_NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();
        setVisible(true);
    }
}
