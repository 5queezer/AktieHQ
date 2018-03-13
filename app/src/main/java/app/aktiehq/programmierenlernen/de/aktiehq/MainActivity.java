package app.aktiehq.programmierenlernen.de.aktiehq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

/**
 * Created by ATN_97 on 02.02.2016.
 */
public class MainActivity extends AppCompatActivity{
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(LOG_TAG, "In Callback-Methode: onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "In Callback-Methode: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "In Callback-Methode: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(LOG_TAG, "In Callback-Methode: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(LOG_TAG, "In Callback-Methode: onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(LOG_TAG, "In Callback-Methode: onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "In Callback-Methode: onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            startActivity(new Intent(this, EinstellungenActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
