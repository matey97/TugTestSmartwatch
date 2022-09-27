package es.uji.geotec.tugtest.tug;

import android.content.Context;

import es.uji.geotec.wearossensors.command.CommandClient;

public class TugTestCommandClient extends CommandClient {

    public TugTestCommandClient(Context context) {
        super(context);
    }

    public void sendCommand(String commandName, int sensorDelay, int batchSize) {
        super.sendCommand(commandName, sensorDelay, batchSize);
    }
}
