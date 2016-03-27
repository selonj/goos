package test.auctionsniper;

import org.junit.After;
import org.junit.Test;

/**
 * Created by L.x on 16-3-24.
 */
public class AuctionSniperEndToEndTest {
    private FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private ApplicationRunner application = new ApplicationRunner();

    @After
    public void stopServer() throws Exception {
        auction.stop();
    }

    @After
    public void stopApplication() throws Exception {
        application.stop();
    }

    @Test
    public void snipersJoinsAnAuctionUtilAuctionClosesThenLosesAuction() throws Exception {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.hasJoiningRequestFromSniper();

        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }
}
