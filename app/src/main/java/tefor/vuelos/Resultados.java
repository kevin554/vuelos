package tefor.vuelos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Resultados extends AppCompatActivity {

    private TextView vueloMasBaratoTextView, vueloMasRapidoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        vueloMasBaratoTextView = (TextView) findViewById(R.id.resultados_vueloMasBaratoTextView);
        vueloMasRapidoTextView = (TextView) findViewById(R.id.resultados_vueloMasRapidoTextView);
    }

}
