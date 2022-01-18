package es.uji.geotec.tugtest.messaging.handlers;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.wearable.MessageEvent;

import es.uji.geotec.tugtest.IntentManager;

public class ResultMessagingHandler {

    private Context context;

    public ResultMessagingHandler(Context context) {
        this.context = context;
    }

    public void handleMessage(MessageEvent event) {
        byte[] data = event.getData();
        int result = Integer.parseInt(new String(data));

        Intent intent = IntentManager.intentForTestResult(context, result);
        context.startActivity(intent);
    }
}
