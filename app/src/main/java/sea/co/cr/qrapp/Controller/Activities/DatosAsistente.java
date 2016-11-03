package sea.co.cr.qrapp.Controller.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asanchezo.qrapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static sea.co.cr.qrapp.Model.Asistente.getAsistenteSingleton;

/*Creado por: Alejandro Sanchez 01-09-2016*/
/*Desc: En esta clase se manipulan los datos de la persona asistente. Contiene dos sub clases asincronas, una para obtener los datos
* del asistente y otra para modificarlos en el servidor*/
public class DatosAsistente extends AppCompatActivity {
    ProgressBar progressBar;
    TextView txtNombre;
    TextView txtCorreo;
    TextView txtEmpresa;
    TextView txtEstado;
    ImageButton btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_datos_asistente);
        /*Prueba de WS con progress bar*/
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        new CargaAsistente().execute();
        txtNombre = (TextView) findViewById(R.id.tvNombreDato);
        txtCorreo = (TextView) findViewById(R.id.tvCorreoDato);
        txtEmpresa = (TextView) findViewById(R.id.tvEmpresaDato);
        txtEstado = (TextView) findViewById(R.id.tvEstadoDato);
        btnConfirmar = (ImageButton) findViewById(R.id.btnConfirmar);


    }

    public void onClickRegresar(View v) {
        finish();
        getAsistenteSingleton().limpiaAsistente();
        Intent k = new Intent(DatosAsistente.this, MainActivity.class);
        startActivity(k);
    }

    public void onClickConfirmar(View v) {

        progressBar.setVisibility(View.VISIBLE);
        ConfirmaAsistente confirmaAsistente = new ConfirmaAsistente();
        confirmaAsistente.execute();

    }

    public void muestraMensajeConfirmacion() {
        int confirmacion = -2; // inicializando
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Estado de la confirmación");
        confirmacion = getAsistenteSingleton().getConfirmacion();
        switch (confirmacion) {
            case -1:
                builder.setMessage("Ocurrió un error al actualizar su estado en el servidor!");
                break;
            case 0:
                builder.setMessage("Registro actualizado correctamente!");
                break;
            case 1:
                builder.setMessage("El usuario indicado ya confirmó su asistencia previamente!");
                break;
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /*Esta subclase contiene métodos asincronos para obtener los datos del asistente*/
    public class CargaAsistente extends AsyncTask<Void, Void, String> {
        public String api_Asistentes = "http://13.85.65.247/WS_WorkShop360/Service/AsistenteService.svc/consultarAsistentePorId/" + getAsistenteSingleton().getAsistente();
        public Exception exception;

        protected void onPreExecute() {
            //responseView.setText("Cargando");
        }

        @Override
        protected void onPostExecute(String s) {
            txtNombre.setText(getAsistenteSingleton().getNombre());
            txtCorreo.setText(getAsistenteSingleton().getCorreoElectronico());
            txtEmpresa.setText(getAsistenteSingleton().getEmpresa());
            txtEstado.setText(getAsistenteSingleton().getEstado());
            String t = getAsistenteSingleton().getEstado();
            if (getAsistenteSingleton().getEstado().equals("Confirmado")) {
                btnConfirmar.setEnabled(false);
            }
            progressBar.setVisibility(View.GONE);
        }

        protected String doInBackground(Void... urls) {
            // Do some validation here

            try {
                URL url = new URL(api_Asistentes);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        try {
                            // line="\"Asistente\":"+line;
                            //System.out.println(line);
                            //JSONObject jsonObject = new JSONObject(line);
                            line = "[" + line + "]";
                            JSONArray jsonArray = new JSONArray(line);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject valorJSON = jsonArray.getJSONObject(i);
                                getAsistenteSingleton().setNombre(valorJSON.getString("nombre"));
                                getAsistenteSingleton().setCorreoElectronico(valorJSON.getString("correoElectronico"));
                                getAsistenteSingleton().setEmpresa(valorJSON.getString("empresa"));
                                getAsistenteSingleton().setEstado(valorJSON.getString("estado"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    bufferedReader.close();

                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
    }

    /*Esta subclase contiene métodos asincronos para confirmar los datos del asistente*/
    public class ConfirmaAsistente extends AsyncTask<Void, Void, String> {

        public String api_Asistentes = "http://13.85.65.247/WS_WorkShop360/Service/AsistenteService.svc/ConfirmarAsistencia/" + getAsistenteSingleton().getAsistente();
        public Exception exception;

        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            muestraMensajeConfirmacion();
        }

        protected String doInBackground(Void... urls) {
            // String email = emailText.getText().toString();
            // Do some validation here

            try {
                URL url = new URL(api_Asistentes);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        try {
                            getAsistenteSingleton().setConfirmacion(Integer.parseInt(line));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    bufferedReader.close();

                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
    }

}


