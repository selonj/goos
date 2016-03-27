package test.auctionsniper;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import static auctionsniper.ui.MainWindow.APPLICATION_WINDOW_NAME;
import static auctionsniper.ui.MainWindow.SNIPER_STATUS_LABEL_NAME;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by L.x on 16-3-27.
 */
public class AuctionSniperDriver extends JFrameDriver {
    public AuctionSniperDriver(long timeoutMills) {
        super(new GesturePerformer(), topLevelFrame(named(APPLICATION_WINDOW_NAME), showingOnScreen()), new AWTEventQueueProber(timeoutMills, 300));
    }

    public void showsSniperStatus(String status) {
        new JLabelDriver(this, named(SNIPER_STATUS_LABEL_NAME)).hasText(equalTo(status));
    }
}
