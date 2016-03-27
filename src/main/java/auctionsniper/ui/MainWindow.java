package auctionsniper.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by L.x on 16-3-27.
 */
public class MainWindow extends JFrame {
    public static final String APPLICATION_WINDOW_NAME = "Auction Sniper Application";
    public static final String SNIPER_STATUS_LABEL_NAME = "Auction Sniper Status";
    public static final String STATUS_JOINING = "JOINING";
    public static final String STATUS_LOST = "LOST";

    private JLabel sniperStatus = makeSniperStatusLabel(STATUS_JOINING);

    private JLabel makeSniperStatusLabel(String initialStatus) {
        JLabel status = new JLabel(initialStatus);
        status.setName(SNIPER_STATUS_LABEL_NAME);
        status.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return status;
    }

    public MainWindow() throws HeadlessException {
        super("Auction Sniper Application");
        setName(APPLICATION_WINDOW_NAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(sniperStatus);

        pack();
        setVisible(true);
    }

    public void showStatus(String status) {
        sniperStatus.setText(status);
    }
}
