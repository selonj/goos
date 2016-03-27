package auctionsniper;

import auctionsniper.ui.MainWindow;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by L.x on 16-3-27.
 */
public class Main {

    public static final String SNIPER_STATUS_LABEL_NAME = "Auction Sniper Status";
    public static final String STATUS_JOINING = "JOINING";
    public static final String STATUS_LOST = "LOST";
    public static final String AUCTION_RESOURCE = "Auction";
    private MainWindow ui;

    public Main() throws InvocationTargetException, InterruptedException {
        startUserInterface();
    }

    private void startUserInterface() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ui = new MainWindow();
            }
        });
    }

    public static void main(String... args) throws Exception {
        Main main = new Main();
    }
}
