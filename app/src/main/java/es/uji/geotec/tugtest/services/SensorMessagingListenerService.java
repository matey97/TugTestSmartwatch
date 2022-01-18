package es.uji.geotec.tugtest.services;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import es.uji.geotec.tugtest.capabilities.CapabilityAdvertisementHandler;
import es.uji.geotec.tugtest.messaging.handlers.AccelerometerMessagingHandler;
import es.uji.geotec.tugtest.messaging.handlers.GyroscopeMessagingHandler;
import es.uji.geotec.tugtest.messaging.handlers.ResultMessagingHandler;


public class SensorMessagingListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent event) {
        String path = event.getPath();

        if (path.contains("advertise-capabilities")) {
            new CapabilityAdvertisementHandler(this).handleRequest(event);
        } else if (path.contains("accelerometer")) {
            new AccelerometerMessagingHandler(this).handleMessage(event);
        } else if (path.contains("gyroscope")) {
            new GyroscopeMessagingHandler(this).handleMessage(event);
        } else if (path.contains("result")) {
            new ResultMessagingHandler(this).handleMessage(event);
        }
    }
}
