package es.uji.geotec.tugtest.records.accumulator;

import es.uji.geotec.tugtest.records.TriAxialRecord;
import es.uji.geotec.tugtest.records.callbacks.AbstractRecordCallback;
import es.uji.geotec.tugtest.sensoring.WearSensor;

public class RecordAccumulatorProvider {
    private RecordAccumulatorProvider() {}

    public static RecordAccumulator getRecordAccumulatorProviderFor(WearSensor wearSensor, AbstractRecordCallback callback, int limit) {
        switch (wearSensor) {
            case ACCELEROMETER:
            case GYROSCOPE:
                return new RecordAccumulator<TriAxialRecord>(callback, limit);
            default:
                return null;
        }
    }
}
