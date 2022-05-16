package es.uji.geotec.tugtest.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.HashSet;
import java.util.Set;

import es.uji.geotec.tugtest.IntentManager;
import es.uji.geotec.tugtest.NTPTime;
import es.uji.geotec.tugtest.NotificationProvider;
import es.uji.geotec.tugtest.sensoring.CollectorManager;
import es.uji.geotec.tugtest.sensoring.SensoringConfiguration;
import es.uji.geotec.tugtest.sensoring.WearSensor;

public class SensorRecordingService extends Service {

    private final IBinder binder = new SensorRecordingBinder();
    public class SensorRecordingBinder extends Binder {
       SensorRecordingService service = SensorRecordingService.this;

        public void startRecordingFor(SensoringConfiguration sensoringConfiguration) {
            service.startRecordingFor(sensoringConfiguration);
        }

        public void stopRecordingFor(WearSensor wearSensor) {
            service.stopRecordingFor(wearSensor);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private static final String TAG = "SensorRecordingService";

    private static final int TIMEOUT = 1000 * 60;

    private PowerManager.WakeLock wakeLock;
    private Set<WearSensor> sensorsBeingRecorded;
    private CollectorManager collectorManager;
    private NTPTime ntpTime;

    @Override
    public void onCreate() {
        super.onCreate();

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "tugtest:sensorrecordingservice");

        sensorsBeingRecorded = new HashSet<>();

        collectorManager = new CollectorManager(this);

        ntpTime = NTPTime.getInstance();
        ntpTime.sync();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (!wakeLock.isHeld()) {
            wakeLock.acquire(TIMEOUT);
        }

        runInForegroundWithNotification();

        sendUpdateUIBroadcast(IntentManager.INTENT_MESSAGE_STARTED);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        collectorManager.ensureStopCollecting();
        super.onDestroy();
    }

    private void runInForegroundWithNotification() {
        NotificationProvider notificationProvider = new NotificationProvider(this);

        int notificationId = notificationProvider.getRecordingServiceNotificationId();
        Notification notification = notificationProvider.getNotificationForRecordingService();

        startForeground(notificationId, notification);
    }

    private void sendUpdateUIBroadcast(String message) {
        Intent intent = IntentManager.intentForUIUpdate(message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void startRecordingFor(SensoringConfiguration sensoringConfiguration) {
        WearSensor sensor = sensoringConfiguration.getWearSensor();
        if (sensorsBeingRecorded.contains(sensor)) {
            Log.d(TAG, "already recording: " + sensor.toString());
            return;
        }

        sensorsBeingRecorded.add(sensor);
        collectorManager.startCollectingFrom(sensoringConfiguration);
        Log.d(TAG, "startRecordingFor: " + sensor.toString());
    }

    private void stopRecordingFor(WearSensor sensor) {
        if (!sensorsBeingRecorded.contains(sensor)) {
            Log.d(TAG, "wasn't being recorded: " + sensor.toString());
        } else {
            Log.d(TAG, "stopRecordingFor: " + sensor.toString());
            collectorManager.stopCollectingFrom(sensor);
            sensorsBeingRecorded.remove(sensor);
        }

        if (sensorsBeingRecorded.size() == 0) {
            Log.d(TAG, "no more sensors being recorded");
            gracefullyStop();
        }
    }

    private void gracefullyStop() {
        stopForeground(true);
        sendUpdateUIBroadcast(IntentManager.INTENT_MESSAGE_ENDED);
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
        stopSelf();
    }
}
