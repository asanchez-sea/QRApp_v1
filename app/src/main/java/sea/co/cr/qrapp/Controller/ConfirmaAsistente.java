package sea.co.cr.qrapp.Controller;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static sea.co.cr.qrapp.Model.Asistente.getAsistenteSingleton;

/**
 * Created by asanchezo on 2/11/2016.
 */

public class ConfirmaAsistente extends AsyncTask<Void, Void, String> {

    public String api_Asistentes = "http://13.85.65.247/WS_WorkShop360/Service/AsistenteService.svc/ConfirmarAsistencia/"+getAsistenteSingleton().getAsistente();
    public Exception exception;
    protected void onPreExecute() {
        //  progressBar.setVisibility(View.VISIBLE);
        //responseView.setText("Cargando");
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
