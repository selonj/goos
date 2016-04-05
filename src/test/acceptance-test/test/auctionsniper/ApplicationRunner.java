package test.auctionsniper;

import auctionsniper.Main;
import auctionsniper.ui.MainWindow;

import static auctionsniper.Main.AUCTION_RESOURCE;
import static auctionsniper.ui.MainWindow.STATUS_BIDDING;
import static auctionsniper.ui.MainWindow.STATUS_JOINING;
import static auctionsniper.ui.MainWindow.STATUS_LOST;
import static test.auctionsniper.FakeAuctionServer.XMPP_HOSTNAME;

/**
 * Created by L.x on 16-3-27.
 */
public class ApplicationRunner {
    private static final String SNIPER_ID = "sniper";
    private static final String SNIPER_PASSWORD = "sniper";
    private static final int TIMEOUT_ONE_SECOND_LATER = 1000;
    public static final String SNIPER_XMPP_ID = String.format("%s@%s/%s", SNIPER_ID, XMPP_HOSTNAME, AUCTION_RESOURCE);
    private AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer auction) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.itemId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        driver = new AuctionSniperDriver(TIMEOUT_ONE_SECOND_LATER);
        driver.showsSniperStatus(STATUS_JOINING);
    }

    public void showsSniperIsBidding() {
        driver.showsSniperStatus(STATUS_BIDDING);
    }

    public void showsSniperHasLostAuction() {
        driver.showsSniperStatus(STATUS_LOST);
    }

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }
}
