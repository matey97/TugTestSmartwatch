package es.uji.geotec.tugtest;

import android.util.Log;

import com.nesl.ntp.GoodClock;

public class NTPTime {

    private static NTPTime instance;

    private GoodClock clock;

    private NTPTime() {
        clock = new GoodClock(5, GoodClock.DEFAULT_UPDATE_INTERVAL);
    }

    public static NTPTime getInstance() {
        if (instance == null) {
            instance = new NTPTime();
        }
        return instance;
    }

    public void sync() {
        Log.d("NTPTime", "starting sync");
        boolean success = clock.singleSync();
        Log.d("NTPTime", "sync succeeded: " + success);
    }

    public long currentTime() {
        return clock.SntpSuceeded ? clock.Now() : System.currentTimeMillis();
    }
}
