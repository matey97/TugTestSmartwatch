package es.uji.geotec.tugtest.capabilities;

import android.content.Context;

import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

import es.uji.geotec.tugtest.sensoring.WearSensor;
import es.uji.geotec.tugtest.sensoring.WearSensorManager;

public class CapabilityAdvertisementHandler {

    private MessageClient messageClient;
    private WearSensorManager sensorManager;

    public CapabilityAdvertisementHandler(Context context) {
        this.messageClient = Wearable.getMessageClient(context);
        this.sensorManager = new WearSensorManager(context);
    }

    public void handleRequest(MessageEvent event) {
        List<WearSensor> availableSensors = this.sensorManager.availableSensors();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < availableSensors.size(); i++) {
            sb.append(availableSensors.get(i).toString());

            if (i != availableSensors.size() - 1)
                sb.append("#");
        }

        this.messageClient.sendMessage(event.getSourceNodeId(), event.getPath(), sb.toString().getBytes());
    }
}
