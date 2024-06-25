package es.uji.geotec.tugtest;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import java.text.DecimalFormat;

import es.uji.geotec.tugtest.intent.IntentManager;
import es.uji.geotec.tugtest.vibration.VibratorManager;

public class ResultActivity extends ComponentActivity {

    private TextView info, time, unit, extendedInfo;
    private ImageView warningIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int resultMs = getIntent().getIntExtra(IntentManager.INTENT_TEST_RESULT, -1);
        setupUIComponents();
        updateUIStatus(resultMs);
        doVibrate();
    }

    private void setupUIComponents() {
        info = findViewById(R.id.result_info);
        time = findViewById(R.id.result_time);
        unit = findViewById(R.id.result_unit);
        extendedInfo = findViewById(R.id.result_extended_info);
        warningIcon = findViewById(R.id.warning_icon);
    }

    private void updateUIStatus(int result) {
        boolean success = result > 0;

        if (success) {
            DecimalFormat df = new DecimalFormat("#.##");
            time.setText(df.format(result / 1000.0));
        } else {
            int infoTextId;
            switch (result) {
                case -1:
                    infoTextId = R.string.result_info_failure;
                    break;
                case -2:
                    infoTextId = R.string.result_info_proc_breach;
                    break;
                default:
                    infoTextId = R.string.result_info_unknown;
            }
            info.setText(infoTextId);
            time.setVisibility(View.GONE);
            unit.setVisibility(View.GONE);
            extendedInfo.setVisibility(View.GONE);
            warningIcon.setVisibility(View.VISIBLE);
        }
    }

    private void doVibrate() {
        VibratorManager vibratorManager = new VibratorManager(this);
        vibratorManager.oneShotVibration(500);
    }
}