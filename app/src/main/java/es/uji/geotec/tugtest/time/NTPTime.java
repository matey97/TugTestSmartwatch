package es.uji.geotec.tugtest.time;

import android.util.Log;

import com.nesl.ntp.GoodClock;

import es.uji.geotec.backgroundsensors.time.TimeProvider;

public class NTPTime extends TimeProvider {

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

    public boolean sync() {
        Log.d("NTPTime", "starting sync");
        boolean success = clock.singleSync();
        Log.d("NTPTime", "sync succeeded: " + success);
        return success;
    }

    @Override
    public long getTimestamp() {
        return clock.SntpSuceeded ? clock.Now() : System.currentTimeMillis();
    }
}
