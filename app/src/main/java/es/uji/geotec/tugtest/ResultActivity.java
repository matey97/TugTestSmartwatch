package es.uji.geotec.tugtest;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import java.text.DecimalFormat;

public class ResultActivity extends ComponentActivity {

    private TextView info, time, unit, extendedInfo;

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
    }

    private void updateUIStatus(int result) {
        boolean success = result != -1;

        if (success) {
            DecimalFormat df = new DecimalFormat("#.##");
            time.setText(df.format(result / 1000.0));
        } else {
            info.setText(R.string.result_info_failure);
            time.setVisibility(View.GONE);
            unit.setVisibility(View.GONE);
            extendedInfo.setVisibility(View.GONE);
        }
    }

    private void doVibrate() {
        VibratorManager vibratorManager = new VibratorManager(this);
        vibratorManager.oneShotVibration(500);
    }
}