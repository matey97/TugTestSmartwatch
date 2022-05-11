package es.uji.geotec.tugtest.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import es.uji.geotec.tugtest.NTPTime;
import es.uji.geotec.tugtest.records.TriAxialRecord;
import es.uji.geotec.tugtest.records.accumulator.RecordAccumulator;
import es.uji.geotec.tugtest.sensoring.WearSensor;

public class TriAxialSensorListener implements SensorEventListener {

    private WearSensor sensor;
    private RecordAccumulator accumulator;
    private NTPTime ntpTime;

    public TriAxialSensorListener(WearSensor sensor, RecordAccumulator recordAccumulator) {
        this.sensor = sensor;
        this.accumulator = recordAccumulator;
        this.ntpTime = NTPTime.getInstance();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != sensor.getSensorType())
            return;

        float xValue = event.values[0];
        float yValue = event.values[1];
        float zValue = event.values[2];

        TriAxialRecord record = new TriAxialRecord(ntpTime.currentTime(), xValue, yValue, zValue);
        accumulator.accumulateRecord(record);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
