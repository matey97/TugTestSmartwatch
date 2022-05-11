package es.uji.geotec.tugtest;

import com.nesl.ntp.GoodClock;

public class NTPTime {

    private static NTPTime instance;

    private GoodClock clock;

    private NTPTime() {
        clock = new GoodClock();
    }

    public static NTPTime getInstance() {
        if (instance == null) {
            instance = new NTPTime();
        }
        return instance;
    }

    public void sync() {
        clock.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public long currentTime() {
        return clock.SntpSuceeded ? clock.Now() : System.currentTimeMillis();
    }

    public void disableSync() {
        clock.stop();
    }
}
