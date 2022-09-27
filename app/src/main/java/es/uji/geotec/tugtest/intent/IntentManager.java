package es.uji.geotec.tugtest.intent;

import android.content.Context;
import android.content.Intent;

import es.uji.geotec.tugtest.ResultActivity;

public class IntentManager {

    private static final int PENDING_INTENT_RC = 50;

    private static final String PERMISSIONS_EXTRAS = "PERMISSIONS";
    private static final String NODE = "NODE";
    private static final String PROTOCOL = "PROTOCOL";

    public static final String INTENT_REQUEST = "es.uji.geotec.tugtest.intent.IntentManager.INTENT_REQUEST";
    public static final String INTENT_MESSAGE = "es.uji.geotec.tugtest.intent.IntentManager.INTENT_MESSAGE";
    public static final String INTENT_MESSAGE_STARTED = "es.uji.geotec.tugtest.intent.IntentManager.INTENT_MESSAGE_STARTED";
    public static final String INTENT_MESSAGE_ENDED = "es.uji.geotec.tugtest.intent.IntentManager.INTENT_MESSAGE_ENDED";

    public static final String INTENT_TEST_RESULT = "es.uji.geotec.tugtest.intent.IntentManager.INTENT_TEST_RESULT";

    private IntentManager() {
    }

    public static Intent intentForUIUpdate(String message) {
        Intent intent = new Intent(INTENT_REQUEST);
        intent.putExtra(INTENT_MESSAGE, message);
        return intent;
    }

    public static Intent intentForTestResult(Context context, int result) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(INTENT_TEST_RESULT, result);
        return intent;
    }

}
