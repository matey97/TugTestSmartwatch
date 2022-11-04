package es.uji.geotec.tugtest.tug;

import android.content.Intent;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import es.uji.geotec.backgroundsensors.collection.CollectorManager;
import es.uji.geotec.backgroundsensors.service.NTPSyncedSensorRecordingService;
import es.uji.geotec.backgroundsensors.time.NTPTimeProvider;
import es.uji.geotec.tugtest.R;
import es.uji.geotec.tugtest.vibration.VibratorManager;
import es.uji.geotec.tugtest.intent.IntentManager;
import es.uji.geotec.wearossensors.collection.WearCollectorManager;

public class TugTestSensorRecordingService extends NTPSyncedSensorRecordingService {

    private PreferencesManager preferencesManager;
    private VibratorManager vibratorManager;

    @Override
    public void onCreate() {
        super.onCreate();

        preferencesManager = new PreferencesManager(this);
        vibratorManager = new VibratorManager(this);

        if (!ntpSynced && preferencesManager.getApplicationMode() == ApplicationMode.COLLECTION) {
            abortCollection();
            return;
        }

        vibratorManager.oneShotVibration(100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int flag = super.onStartCommand(intent, flags, startId);
        sendUpdateUIBroadcast(IntentManager.INTENT_MESSAGE_STARTED);

        return flag;
    }

    @Override
    public CollectorManager getCollectorManager() {
        return new WearCollectorManager(this, NTPTimeProvider.getInstance());
    }

    @Override
    protected void gracefullyStop() {
        sendUpdateUIBroadcast(IntentManager.INTENT_MESSAGE_ENDED);
        super.gracefullyStop();
    }

    private void sendUpdateUIBroadcast(String message) {
        Intent intent = IntentManager.intentForUIUpdate(message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void abortCollection() {
        vibratorManager.oneShotVibration(500);
        Toast.makeText(this, R.string.ntp_sync_error, Toast.LENGTH_LONG).show();

        gracefullyStop();
    }
}
