package app.aktiehq.programmierenlernen.de.aktiehq;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.StringReader;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by ATN_97 on 02.02.2016.
 */
public class AktienlisteFragment extends Fragment{
    // Der ArrayAdapter ist jetzt eine Membervariable der Klasse AktienlisteFragment
    ArrayAdapter<String> mAktienlisteAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private final String LOG_TAG = AktienlisteFragment.class.getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v(LOG_TAG, "In Callback-Methode: onAttach()");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(LOG_TAG, "In Callback-Methode: onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "In Callback-Methode: onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "In Callback-Methode: onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(LOG_TAG, "In Callback-Methode: onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(LOG_TAG, "In Callback-Methode: onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(LOG_TAG, "In Callback-Methode: onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "In Callback-Methode: onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(LOG_TAG, "In Callback-Methode: onDetach()");
    }

    public AktienlisteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "In Callback-Methode: onCreate()");
        // menü bekannt geben, dadurch kann unser Fragment Menü-Events verarbeiten
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_aktienlistefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Wir prüfen, ob Menü-Element mit der ID "action_daten_aktualisieren"
        // ausgewählt wurde und geben eine Meldung aus
        int id = item.getItemId();
        if(id == R.id.action_daten_aktualisieren) {
            aktualisiereDaten();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(LOG_TAG, "In Callback-Methode: onCreate()");

        /*
        Log.v(LOG_TAG, "verbose     - Meldung");
        Log.d(LOG_TAG, "debug       - Meldung");
        Log.i(LOG_TAG, "information - Meldung");
        Log.w(LOG_TAG, "warning     - Meldung");
        Log.e(LOG_TAG, "error       - Meldung");
        */

        String [] aktienlisteArray = {
                "Adidas - Kurs: 73,45 €",
                "Allianz - Kurs: 145,12 €",
                "BASF - Kurs: 84,27 €",
                "Bayer - Kurs: 128,60 €",
                "Beiersdorf - Kurs: 80,55 €",
                "BMW St. - Kurs: 104,11 €",
                "Commerzbank - Kurs: 12,47 €",
                "Continental - Kurs: 209,94 €",
                "Daimler - Kurs: 84,33 €"
        };

        List<String> aktienListe = new ArrayList<>(Arrays.asList(aktienlisteArray));

        mAktienlisteAdapter =
                new ArrayAdapter<>(
                        getActivity(), // Die aktuelle Umgebung (diese Activity)
                        R.layout.list_item_aktienliste, // ID der XML-Layout Datei
                        R.id.list_item_aktienliste_textview, // ID des TextViews
                        aktienListe); // Beispieldaten in einer ArrayList


        View rootView = inflater.inflate(R.layout.fragment_aktienliste, container, false);

        // Eine Referenz zu unserem ListView, und Verbinden des ArrayAdapters mit dem ListView
        // Anschließend Registrieren eines OnItemClickListener für den ListView
        ListView aktienlisteListView = (ListView) rootView.findViewById(R.id.listview_aktienliste);
        aktienlisteListView.setAdapter(mAktienlisteAdapter);

        aktienlisteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String aktienInfo = (String) adapterView.getItemAtPosition(position);
                //Toast.makeText(getActivity(), aktienInfo, Toast.LENGTH_SHORT).show();

                // Intent erzeugen und Starten der AktiendetailActivity mit explizitem Intent
                Intent aktiendetailIntent = new Intent(getActivity(), AktiendetailActivity.class);
                aktiendetailIntent.putExtra(Intent.EXTRA_TEXT, aktienInfo);
                startActivity(aktiendetailIntent);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout_aktienliste);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                aktualisiereDaten();
            }
        });
        return rootView;
    }

    public void aktualisiereDaten() {
        // Erzeugen einer Instanz von HoleDatenTask
        HoleDatenTask holeDatenTask = new HoleDatenTask();

        // Auslesen der ausgewählten Aktienliste aus den SharedPreferences
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String prefAktienlisteKey = getString(R.string.preference_aktienliste_key);
        String prefAktienlisteDefault = getString(R.string.preference_aktienliste_default);
        String aktienliste = sPrefs.getString(prefAktienlisteKey, prefAktienlisteDefault);

        // Auslesen des Anzeige-Modus aus den SharedPreferences
        String prefIndizemodusKey = getString(R.string.preference_indizemodus_key);
        Boolean indizemodus = sPrefs.getBoolean(prefIndizemodusKey, false);

        // Starten des asynchronen Tasks und Übergabe der Aktienliste
        if (indizemodus) {
            String indizeliste = "^GDAXI,^TECDAX,^MDAXI,^SDAXI,^GSPC,^N225,^HSI,XAGUSD=X,XAUUSD=X";
            holeDatenTask.execute(indizeliste);
        } else {
            holeDatenTask.execute(aktienliste);
        }

        // Den Benutzer informieren, dass neue Aktiendaten im Hintergrund abgefragt werden
        Toast.makeText(getActivity(), "Aktiendaten werden abgefragt!", Toast.LENGTH_SHORT).show();
    }

    public class HoleDatenTask extends AsyncTask<String, Integer, String[]> {
        private final String LOG_TAG = HoleDatenTask.class.getSimpleName();

        @Override
        protected String[] doInBackground(String... strings) {
            if (strings.length == 0) { // Keine Eingangsparameter erhalten, daher Abbruch
                return null;
            }

            // Wir konstruieren die Anfrage-URL für die YQL Platform
            final String URL_PARAMETER = "https://query.yahooapis.com/v1/public/yql";
            final String SELECTOR = "select%20*%20from%20csv%20where%20";
            final String DOWNLOAD_URL = "http://download.finance.yahoo.com/d/quotes.csv";
            final String DIAGNOSTICS = "'&diagnostics=true";

            String symbols = strings[0];
            symbols = symbols.replace("^", "%255E");
            String parameters = "snc4xl1d1t1c1p2ohgv";
            String columns = "symbol,name,currency,exchange,price,date,time," +
                    "change,percent,open,high,low,volume";

            String anfrageString = URL_PARAMETER;
            anfrageString += "?q=" + SELECTOR;
            anfrageString += "url='" + DOWNLOAD_URL;
            anfrageString += "?s=" + symbols;
            anfrageString += "%26f=" + parameters;
            anfrageString += "%26e=.csv'%20and%20columns='" + columns;
            anfrageString += DIAGNOSTICS;

            Log.v(LOG_TAG, "Zusammengesetzter Anfrage-String: " + anfrageString);

            // Die URL-Verbindung und der BufferedReader, werden im finally-Block geschlossen
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            // In diesen String speichern wir die Aktiendaten im XML-Format
            String aktiendatenXmlString = "";

            try {
                URL url = new URL(anfrageString);

                // Aufbau der Verbindung zur YQL Platform
                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                if (inputStream == null) { // Keinen Aktiendaten-Stream erhalten, daher Abbruch
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    aktiendatenXmlString += line + "\n";
                }
                if (aktiendatenXmlString.length() == 0) {   // Keine Aktiendaten ausgelesen, Abbruch
                    return null;
                }
                Log.v(LOG_TAG, "Aktiendaten XML-String: " + aktiendatenXmlString);
                publishProgress(1,1);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG + "Error closing stream", e.toString());
                    }
                }
            }

            // Hier parsen wir später die XML-Aktiendaten
            return leseXmlAktiendatenAus(aktiendatenXmlString);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Auf dem Bildschirm geben wir eine Statusmeldung aus, immer wenn
            // publishProgress(int...) in doInBackground(String...) aufgerufen wird
            Toast.makeText(getActivity(), values[0] + " von " + values[1] + " geladen", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String[] strings) {

            // Wir löschen den Inhalt des ArrayAdapters und fügen den neuen Inhalt ein
            // Der neue Inhalt ist der Rückgabewert von doInBackground(String...) also
            // der StringArray gefüllt mit Beispieldaten
            if (strings != null) {
                mAktienlisteAdapter.clear();
                for (String aktienString : strings) {
                    mAktienlisteAdapter.add(aktienString);
                }
            }

            // Hintergrundberechnungen sind jetzt beendet, darüber informieren wir den Benutzer
            Toast.makeText(getActivity(), "Aktiendaten vollständig geladen!",
                    Toast.LENGTH_SHORT).show();
        }

        private String[] leseXmlAktiendatenAus(String xmlString) {

            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xmlString));
                doc = db.parse(is);
            } catch (ParserConfigurationException e) {
                Log.e(LOG_TAG, "Error: " + e.getMessage());
                return null;
            } catch (SAXException e) {
                Log.e(LOG_TAG, "Error: " + e.getMessage());
                return null;
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error: " + e.getMessage());
                return null;
            }

            Element xmlAktiendaten = doc.getDocumentElement();
            NodeList aktienListe = xmlAktiendaten.getElementsByTagName("row");

            int anzahlAktien = aktienListe.getLength();
            int anzahlAktienParameter = aktienListe.item(0).getChildNodes().getLength();

            String[] ausgabeArray = new String[anzahlAktien];
            String[][] alleAktienDatenArray = new String[anzahlAktien][anzahlAktienParameter];

            Node aktienParameter;
            String aktienParameterWert;
            for( int i = 0; i < anzahlAktien; i++ ) {
                NodeList aktienParameterListe = aktienListe.item(i).getChildNodes();

                for ( int j = 0; j < anzahlAktienParameter; j++) {
                    aktienParameter = aktienParameterListe.item(j);
                    aktienParameterWert = aktienParameter.getFirstChild().getNodeValue();
                    alleAktienDatenArray[i][j] = aktienParameterWert;
                }

                ausgabeArray[i] = alleAktienDatenArray[i][0];   // symbol
                ausgabeArray[i] += ": " + alleAktienDatenArray[i][4];   // price
                ausgabeArray[i] += " " + alleAktienDatenArray[i][2];    // currency
                ausgabeArray[i] += " (" + alleAktienDatenArray[i][8] + ")"; // percent
                ausgabeArray[i] += " - [" + alleAktienDatenArray[i][1] + "]"; // name

                Log.v(LOG_TAG, "XML Output:" + ausgabeArray[i]);
            }

            return ausgabeArray;
        }
    }
}
