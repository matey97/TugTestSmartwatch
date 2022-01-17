package es.uji.geotec.tugtest.sensoring;

import android.content.Context;
import android.hardware.SensorEventListener;

import java.util.HashMap;

import es.uji.geotec.tugtest.listeners.SensorListenerProvider;
import es.uji.geotec.tugtest.records.accumulator.RecordAccumulator;
import es.uji.geotec.tugtest.records.accumulator.RecordAccumulatorProvider;
import es.uji.geotec.tugtest.records.callbacks.AbstractRecordCallback;
import es.uji.geotec.tugtest.records.callbacks.RecordCallbackProvider;

public class CollectorManager {

    private Context context;
    private WearSensorManager wearSensorManager;

    private HashMap<WearSensor, SensorEventListener> listeners;

    public CollectorManager(Context context) {
        this.context = context;
        this.wearSensorManager = new WearSensorManager(context);
        this.listeners = new HashMap<>();
    }

    public boolean startCollectingFrom(SensoringConfiguration sensoringConfiguration) {
        WearSensor wearSensor = sensoringConfiguration.getWearSensor();
        AbstractRecordCallback callback = RecordCallbackProvider.getRecordCallbackFor(
                wearSensor,
                context,
                sensoringConfiguration.getRequesterId(),
                sensoringConfiguration.getSendingPath()
        );

        RecordAccumulator accumulator = RecordAccumulatorProvider.getRecordAccumulatorProviderFor(
                wearSensor,
                callback,
                sensoringConfiguration.getBatchSize()
        );

        switch (wearSensor) {
            case ACCELEROMETER:
            case GYROSCOPE:
                SensorEventListener listener = SensorListenerProvider.getListenerFor(wearSensor, accumulator);
                if (listener == null)
                    return false;

                listeners.put(wearSensor, listener);
                return wearSensorManager.startCollectingFrom(wearSensor, sensoringConfiguration.getSensorDelay(), listener);
            default:
                return false;
        }
    }

    public void stopCollectingFrom(WearSensor wearSensor) {
        switch (wearSensor) {
            case ACCELEROMETER:
            case GYROSCOPE:
                SensorEventListener listener = listeners.get(wearSensor);
                if (listener == null)
                    return;

                listeners.remove(wearSensor);
                wearSensorManager.stopCollectingFrom(wearSensor, listener);
                break;
        }
    }

    public void ensureStopCollecting() {
        wearSensorManager.stopCollectingFromAllSources(listeners);
    }
}
