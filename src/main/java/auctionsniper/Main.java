package auctionsniper;

import auctionsniper.ui.MainWindow;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import static auctionsniper.ui.MainWindow.STATUS_LOST;
import static java.lang.String.format;

/**
 * Created by L.x on 16-3-27.
 */
public class Main implements AuctionEventListener {
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_RESOURCE = "Auction";
    private static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

    private static final int HOSTNAME_ARG = 0;
    private static final int SNIPER_ID_ARG = 1;
    private static final int PASSWORD_ARG = 2;
    private static final int ITEM_ID_ARG = 3;
    public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Bid: %d;";
    public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";

    private MainWindow ui;
    private Chat notToBeGCd;

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

    private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
        disconnectWhenUICloses(connection);

        Chat chat = connection.getChatManager().createChat(auctionId(itemId, connection), new AuctionMessageTranslator(this));

        notToBeGCd = chat;
        chat.sendMessage(JOIN_COMMAND_FORMAT);
    }

    private void disconnectWhenUICloses(final XMPPConnection connection) {
        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                connection.disconnect();
            }
        });
    }

    @Override
    public void auctionClosed() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.showStatus(STATUS_LOST);
            }
        });
    }

    @Override
    public void currentPrice(int price, int increment) {

    }

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.joinAuction(connection(args[HOSTNAME_ARG], args[SNIPER_ID_ARG], args[PASSWORD_ARG]), args[ITEM_ID_ARG]);
    }

    private static String auctionId(String itemId, XMPPConnection connection) {
        return format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }

    private static XMPPConnection connection(String hostName, String sniperId, String password) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostName);
        connection.connect();
        connection.login(sniperId, password, AUCTION_RESOURCE);
        return connection;
    }
}
