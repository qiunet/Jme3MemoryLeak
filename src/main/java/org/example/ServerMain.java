package org.example;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/***
 *
 * @author qiunet
 * ${DATE} ${TIME}
 */
public class ServerMain extends SimpleApplication {
    private static final ScheduledExecutorService service = new ScheduledThreadPoolExecutor(10);
    public static void main(String[] args) throws Exception {
        ServerMain server = new ServerMain();
        server.settings = new AppSettings(true);
        server.settings.setUseInput(false);
        server.settings.setFrameRate(20);
        server.settings.setAudioRenderer(null);
        server.settings.setRenderer(null);
        server.start(JmeContext.Type.Headless);


        AtomicInteger counter = new AtomicInteger();
        int i = 0;
        for (;;) {
            Room room = new Room(server, i ++);
            counter.incrementAndGet();
            service.schedule(() -> {
                counter.decrementAndGet();
                room.destroy();
            }, ThreadLocalRandom.current().nextInt(10, 50), TimeUnit.SECONDS);
            System.out.println("Create total count: "+i+", Current alive count:" + counter.get());
            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 900));
        }
    }

    @Override
    public void simpleInitApp() {
        this.setPauseOnLostFocus(false);
        this.setDisplayStatView(false);
        this.setShowSettings(false);
        this.inputEnabled = false;
    }
}