package tefor.vuelos;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import tefor.vuelos.clienteHTTP.HttpConnection;
import tefor.vuelos.clienteHTTP.MethodType;
import tefor.vuelos.clienteHTTP.RequestConfiguration;
import tefor.vuelos.clienteHTTP.StandarRequestConfiguration;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton botonFlotante;
    private Spinner origenSpinner, destinoSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();

        botonFlotante.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new ObtenerPaisesTask().execute();
    }

    private void inicializarComponentes() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        botonFlotante = (FloatingActionButton) findViewById(R.id.botonFlotante);
        origenSpinner = (Spinner) findViewById(R.id.contentMain_origenSpinner);
        destinoSpinner = (Spinner) findViewById(R.id.contentMain_destinoSpinner);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.botonFlotante:
                verResultadosVuelos();
        }
    }

    private void verResultadosVuelos() {
        Intent intent = new Intent(this, Resultados.class);
        startActivity(intent);
    }

    private class ObtenerPaisesTask extends AsyncTask<Void, String, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            //String URL = "http://169.254.111.56:8080/Vuelos/Processor";
            //String URL = "http://192.168.43.32:4848/Vuelos/Processor";
            String url = "http://192.168.43.32:8080/Vuelos/Processor";

            Hashtable<String, String> parametros = new Hashtable<>();
            parametros.put("evento", "obtenerPaises");

            RequestConfiguration configuracion =
                    new StandarRequestConfiguration(url, MethodType.GET, parametros);

            String respuesta = HttpConnection.sendRequest(configuracion);

            List<String> listaPaises = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(respuesta);

                for (int i = 0; i < jsonArray.length(); i++) {
                    String pais = jsonArray.getString(i);
                    listaPaises.add(pais);
                }
            } catch (JSONException e) {
                Log.e("Vuelos App", "Hubo un error al obtener los paises de un JSON Array");
            }

            return listaPaises;
        }

        @Override
        protected void onPostExecute(List<String> s) {
            super.onPostExecute(s);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_spinner_item, s);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            origenSpinner.setAdapter(adapter);
            destinoSpinner.setAdapter(adapter);
        }

    }

}
