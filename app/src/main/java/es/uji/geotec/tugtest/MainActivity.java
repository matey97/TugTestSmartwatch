package es.uji.geotec.tugtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.ComponentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import es.uji.geotec.tugtest.intent.IntentManager;
import es.uji.geotec.tugtest.tug.ApplicationMode;
import es.uji.geotec.tugtest.tug.PreferencesManager;
import es.uji.geotec.tugtest.tug.TugTestCommandClient;
import es.uji.geotec.tugtest.tug.TugTestSensorRecordingService;
import es.uji.geotec.wearossensors.permissions.PermissionsManager;
import es.uji.geotec.wearossensors.plainmessage.PlainMessageClient;
import es.uji.geotec.wearossensors.services.RecordingServiceManager;

public class MainActivity extends ComponentActivity {

    private LinearLayout linearLayout;
    private TextView infoText;
    private Button startBtn, stopBtn;
    private ProgressBar waitBar;
    private ToggleButton modeToggle;
    private ApplicationMode mode;

    private BroadcastReceiver receiver;

    private TugTestCommandClient commandClient;
    private PlainMessageClient plainMessageClient;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commandClient = new TugTestCommandClient(this);
        plainMessageClient = new PlainMessageClient(this);
        preferencesManager = new PreferencesManager(this);
        PermissionsManager.setPermissionsActivity(this, RequestPermissionsActivity.class);
        RecordingServiceManager.setService(this, TugTestSensorRecordingService.class);

        setupLayout();
        setupUIComponents();
        setupReceiver();
        setupMessageClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                new IntentFilter(IntentManager.INTENT_REQUEST)
        );
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    public void onStartCommandTap(View view) {
        waitingToStart();
        commandClient.sendCommand(mode.start, SensorManager.SENSOR_DELAY_FASTEST, 50);
    }

    public void onStopCommandTap(View view) {
        commandClient.sendCommand(mode.stop, 0, 0);
    }

    private void setupLayout() {
        linearLayout = findViewById(R.id.linear_layout);
        if (this.getResources().getConfiguration().isScreenRound()) {
            int padding = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.12f);
            linearLayout.setPadding(padding, padding, padding, padding);
        }
    }

    private void setupUIComponents() {
        infoText = findViewById(R.id.info_text);
        startBtn = findViewById(R.id.start_command);
        stopBtn = findViewById(R.id.stop_command);
        waitBar = findViewById(R.id.waitBar);

        mode = preferencesManager.getApplicationMode();
        modeToggle = findViewById(R.id.toggleMode);
        modeToggle.setChecked(mode == ApplicationMode.TUG);
        modeToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mode = isChecked ? ApplicationMode.TUG : ApplicationMode.COLLECTION;
                preferencesManager.setApplicationMode(mode);
                toggleMode();
            }
        });
        toggleMode();
    }


    private void setupReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getStringExtra(IntentManager.INTENT_MESSAGE)) {
                    case IntentManager.INTENT_MESSAGE_STARTED:
                        executionStarted();
                        break;
                    case IntentManager.INTENT_MESSAGE_ENDED:
                        executionEnded();
                        break;
                }
            }
        };
    }

    private void setupMessageClient() {
        plainMessageClient.registerListener(message -> {
            int result = Integer.parseInt(message.getPlainMessage().getMessage());
            IntentManager.intentForTestResult(getApplicationContext(), result);
        });
    }

    private void waitingToStart() {
        startBtn.setVisibility(View.GONE);
        waitBar.setVisibility(View.VISIBLE);
        infoText.setText(R.string.wait_info);
        modeToggle.setEnabled(false);
    }

    private void executionStarted() {
        waitBar.setVisibility(View.GONE);
        stopBtn.setVisibility(View.VISIBLE);
        infoText.setText(mode == ApplicationMode.TUG ? R.string.execution_info : R.string.collection_info);
    }

    private void executionEnded() {
        stopBtn.setVisibility(View.GONE);
        startBtn.setVisibility(View.VISIBLE);
        infoText.setText(mode == ApplicationMode.TUG ? R.string.start_info : R.string.start_collection_info);
        modeToggle.setEnabled(true);
    }

    private void toggleMode() {
        int start = R.string.start_test , stop = R.string.end_test, info = R.string.start_info;
        if (mode == ApplicationMode.COLLECTION) {
            start = R.string.start_collection;
            stop = R.string.end_collection;
            info = R.string.start_collection_info;
        }

        startBtn.setText(start);
        stopBtn.setText(stop);
        infoText.setText(info);
    }
}