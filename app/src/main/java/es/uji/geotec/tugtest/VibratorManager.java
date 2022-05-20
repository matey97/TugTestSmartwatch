package es.uji.geotec.tugtest;

import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibratorManager {

    private Vibrator vibrator;

    public VibratorManager(Context context) {
        this.vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
    }

    public void oneShotVibration(long ms) {
        this.vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
