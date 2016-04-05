package test.auctionsniper;

import auctionsniper.Main;
import org.hamcrest.Matcher;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static auctionsniper.Main.AUCTION_RESOURCE;
import static auctionsniper.Main.BID_COMMAND_FORMAT;
import static auctionsniper.Main.JOIN_COMMAND_FORMAT;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by L.x on 16-3-27.
 */
public class FakeAuctionServer {
    public static final String XMPP_HOSTNAME = "localhost";
    private static final String AUCTION_PASSWORD = "auction";
    private static final String PRICE_EVENT_FORMAT = "SOLVersion: 1.1; Event: PRICE; Price: %d; Increment: %d; Bidder: %s;";
    private static final String CLOSE_EVENT_FORMAT = "SOLVersion: 1.1; Event: CLOSE;";

    public final String itemId;
    private Chat currentChat;
    private XMPPConnection connection;
    private final SingleMessageListener messageListener = new SingleMessageListener();

    public FakeAuctionServer(String itemId) {
        this.itemId = itemId;
        connection = new XMPPConnection(XMPP_HOSTNAME);
    }

    public void startSellingItem() throws XMPPException {
        connection.connect();
        connection.login(format(Main.ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
        connection.getChatManager().addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createLocally) {
                currentChat = chat;
                chat.addMessageListener(messageListener);
            }
        });
    }

    public void hasJoiningRequestFromSniper(String sniperId) throws InterruptedException {
        receivesAMessageMatching(sniperId, equalTo(JOIN_COMMAND_FORMAT));
    }

    public void announceClosed() throws XMPPException {
        currentChat.sendMessage(CLOSE_EVENT_FORMAT);
    }

    public void reportPrice(int price, int increment, String bidder) throws XMPPException {
        currentChat.sendMessage(format(PRICE_EVENT_FORMAT, price, increment, bidder));
    }

    public void hasReceivedBid(int bid, String sniperId) throws InterruptedException {
        receivesAMessageMatching(sniperId, equalTo(format(BID_COMMAND_FORMAT, bid)));
    }

    private void receivesAMessageMatching(String sniperId, Matcher<String> messageBodyMatcher) throws InterruptedException {
        messageListener.receivesAMessage(messageBodyMatcher);
        assertThat(currentChat.getParticipant(), equalTo(sniperId));
    }

    public void stop() {
        connection.disconnect();
    }

    private static class SingleMessageListener implements MessageListener {
        private BlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(1);

        @Override
        public void processMessage(Chat chat, Message message) {
            messages.add(message);
        }

        public void receivesAMessage(Matcher<? super String> messageBodyMatcher) throws InterruptedException {
            Message message = messages.poll(1, TimeUnit.SECONDS);
            assertThat(message, is(notNullValue()));
            assertThat(message.getBody(), messageBodyMatcher);
        }
    }
}
