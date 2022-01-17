package es.uji.geotec.tugtest.messaging.handlers;

import android.content.Context;

import java.util.ArrayList;

import es.uji.geotec.tugtest.messaging.MessagingProtocol;
import es.uji.geotec.tugtest.messaging.ResultMessagingProtocol;
import es.uji.geotec.tugtest.sensoring.WearSensor;

public class GyroscopeMessagingHandler extends AbstractMessagingHandler {

    public GyroscopeMessagingHandler(Context context) { super(context); };

    @Override
    protected ArrayList<String> getRequiredPermissions() {
        return new ArrayList<>();
    }

    @Override
    protected MessagingProtocol getProtocol() {
        return new MessagingProtocol(
                "/gyroscope/start",
                "/gyroscope/stop",
                "/gyroscope/new-record",
                new ResultMessagingProtocol("/gyroscope/ready"),
                new ResultMessagingProtocol("/gyroscope/prepare")
        );
    }

    @Override
    protected WearSensor getWearSensorType() {
        return WearSensor.GYROSCOPE;
    }
}
