package test.auctionsniper;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static auctionsniper.Main.AUCTION_RESOURCE;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by L.x on 16-3-27.
 */
public class FakeAuctionServer {
    public static final String XMPP_HOSTNAME = "localhost";
    private static final String ITEM_ID_AS_LOGIN = "auction-%s";
    private static final String AUCTION_PASSWORD = "auction";

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
        connection.login(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
        connection.getChatManager().addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createLocally) {
                currentChat = chat;
                chat.addMessageListener(messageListener);
            }
        });
    }

    public void hasJoiningRequestFromSniper() throws InterruptedException {
        messageListener.receivesAMessage();
    }

    public void announceClosed() throws XMPPException {
        currentChat.sendMessage(new Message());
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

        public void receivesAMessage() throws InterruptedException {
            Message message = messages.poll(1, TimeUnit.SECONDS);
            assertThat(message, is(notNullValue()));
        }
    }
}
