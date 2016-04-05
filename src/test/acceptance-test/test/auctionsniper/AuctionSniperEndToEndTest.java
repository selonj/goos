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
    public void sniperJoinsAnAuctionUtilAuctionClosesThenLosesAuction() throws Exception {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.hasJoiningRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);

        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

    @Test
    public void sniperMakesAHighPriceButLoses() throws Exception {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.hasJoiningRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);

        auction.reportPrice(1000, 98, "other bidder");
        application.showsSniperIsBidding();
        auction.hasReceivedBid(1098,ApplicationRunner.SNIPER_XMPP_ID);

        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }
}
