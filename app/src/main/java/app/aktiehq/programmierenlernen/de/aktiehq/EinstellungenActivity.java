package app.aktiehq.programmierenlernen.de.aktiehq;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by ATN_97 on 08.02.2016.
 */
public class EinstellungenActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Toast.makeText(this, "Einstellungen-Activity gestartet.", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Zurück mit Back-Button.", Toast.LENGTH_SHORT).show();

        // noinspection deprecation
        addPreferencesFromResource(R.xml.preferences);

        Preference aktienlistePref = findPreference(getString(R.string.preference_aktienliste_key));
        aktienlistePref.setOnPreferenceChangeListener(this);

        // onPreferenceChange sofort aufrufen mit der in SharedPreferences gespeicherten Aktienliste
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String gespeicherteAktienliste = sharedPrefs.getString(aktienlistePref.getKey(), "");
        onPreferenceChange(aktienlistePref, gespeicherteAktienliste);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        preference.setSummary(newValue.toString());
        return true;
    }
}
