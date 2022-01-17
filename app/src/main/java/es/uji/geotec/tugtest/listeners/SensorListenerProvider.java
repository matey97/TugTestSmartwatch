package es.uji.geotec.tugtest.listeners;

import android.hardware.SensorEventListener;

import es.uji.geotec.tugtest.records.accumulator.RecordAccumulator;
import es.uji.geotec.tugtest.sensoring.WearSensor;


public class SensorListenerProvider {
    private SensorListenerProvider() {
    }

    public static SensorEventListener getListenerFor(
            WearSensor wearSensor,
            RecordAccumulator recordAccumulator
    ) {
        switch (wearSensor) {
            case ACCELEROMETER:
            case GYROSCOPE:
                return new TriAxialSensorListener(wearSensor, recordAccumulator);
            default:
                return null;
        }
    }
}
