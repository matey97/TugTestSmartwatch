package es.uji.geotec.tugtest.listeners;

import android.hardware.SensorEventListener;

import es.uji.geotec.tugtest.records.accumulator.RecordAccumulator;

public interface WearSensorListener extends SensorEventListener {
    void setRecordAccumulator(RecordAccumulator callback);
}
