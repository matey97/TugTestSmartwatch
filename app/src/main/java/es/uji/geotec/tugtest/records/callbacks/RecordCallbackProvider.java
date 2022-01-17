package es.uji.geotec.tugtest.records.callbacks;

import android.content.Context;

import es.uji.geotec.tugtest.sensoring.WearSensor;

public class RecordCallbackProvider {
    private RecordCallbackProvider() {
    }

    public static AbstractRecordCallback getRecordCallbackFor(
            WearSensor wearSensor,
            Context context,
            String requesterId,
            String sendingPath
    ) {
        switch (wearSensor) {
            case ACCELEROMETER:
            case GYROSCOPE:
                return new TriAxialRecordCallback(context, requesterId, sendingPath);
            default:
                return null;
        }
    }
}
