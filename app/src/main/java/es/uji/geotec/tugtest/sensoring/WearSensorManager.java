package es.uji.geotec.tugtest.sensoring;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WearSensorManager {

    private static WearSensor[] SENSORS = new WearSensor[] {
            WearSensor.ACCELEROMETER,
            WearSensor.GYROSCOPE,
    };

    private Context context;
    private SensorManager sensorManager;

    public WearSensorManager(Context context) {
        this.context = context;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public List<WearSensor> availableSensors() {
        List<WearSensor> availableSensors = new ArrayList<>();
        for (WearSensor sensor : SENSORS) {
            if (isSensorAvailable(sensor))
                availableSensors.add(sensor);
        }

        return availableSensors;
    }

    public boolean isSensorAvailable(WearSensor sensor) {
        boolean hasFeature = context.getPackageManager().hasSystemFeature(sensor.getSensorFeature());

        int sensorType = sensor.getSensorType();
        if (sensorType == -1)
            return hasFeature;

        boolean hasSensor = sensorManager.getDefaultSensor(sensor.getSensorType()) != null;

        return hasFeature || hasSensor;
    }

    public boolean startCollectingFrom(WearSensor wearSensor, int samplingPeriod, SensorEventListener listener) {
        if (!isSensorAvailable(wearSensor) || listener == null)
            return false;

        Sensor sensor = getWearSensor(wearSensor);
        return sensorManager.registerListener(listener, sensor, samplingPeriod);
    }

    public void stopCollectingFrom(WearSensor wearSensor, SensorEventListener listener) {
        if (listener == null)
            return;

        Sensor sensor = getWearSensor(wearSensor);
        sensorManager.unregisterListener(listener, sensor);
    }


    public void stopCollectingFromAllSources(HashMap<WearSensor, SensorEventListener> listeners) {
        for (WearSensor wearSensor : listeners.keySet()) {
            stopCollectingFrom(wearSensor, listeners.get(wearSensor));
        }
    }

    private Sensor getWearSensor(WearSensor sensor) {
        return sensorManager.getDefaultSensor(sensor.getSensorType());
    }
}
