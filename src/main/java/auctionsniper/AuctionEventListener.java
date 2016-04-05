package auctionsniper;

import java.util.EventListener;

/**
 * Created by L.x on 16-4-5.
 */
public interface AuctionEventListener extends EventListener {
    void auctionClosed();

    void currentPrice(int price, int increment);
}
