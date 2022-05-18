package es.uji.geotec.tugtest;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static final String PREFERENCES_NAME = "TUG_TEST_PREFERENCES";
    private static final String APP_MODE_KEY = "APP_MODE_KEY";

    private SharedPreferences preferences;

    public PreferencesManager(Context context) {
        this.preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void setApplicationMode(ApplicationMode mode) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(APP_MODE_KEY, mode.name());
        editor.apply();
    }

    public ApplicationMode getApplicationMode() {
        String mode = preferences.getString(APP_MODE_KEY, "TUG");
        return ApplicationMode.valueOf(mode);
    }
}
