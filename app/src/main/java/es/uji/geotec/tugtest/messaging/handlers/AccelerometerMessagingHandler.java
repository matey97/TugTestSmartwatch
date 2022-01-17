package es.uji.geotec.tugtest.messaging.handlers;

import android.content.Context;

import java.util.ArrayList;

import es.uji.geotec.tugtest.messaging.MessagingProtocol;
import es.uji.geotec.tugtest.messaging.ResultMessagingProtocol;
import es.uji.geotec.tugtest.sensoring.WearSensor;

public class AccelerometerMessagingHandler extends AbstractMessagingHandler {

    public AccelerometerMessagingHandler(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<String> getRequiredPermissions() {
        return new ArrayList<>();
    }

    @Override
    protected MessagingProtocol getProtocol() {
        return new MessagingProtocol(
                "/accelerometer/start",
                "/accelerometer/stop",
                "/accelerometer/new-record",
                new ResultMessagingProtocol("/accelerometer/ready"),
                new ResultMessagingProtocol("/accelerometer/prepare")
        );
    }

    @Override
    protected WearSensor getWearSensorType() {
        return WearSensor.ACCELEROMETER;
    }
}
