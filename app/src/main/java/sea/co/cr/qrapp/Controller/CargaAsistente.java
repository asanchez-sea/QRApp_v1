package sea.co.cr.qrapp.Controller;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static sea.co.cr.qrapp.Model.Asistente.getAsistenteSingleton;

/**
 * Created by asanchezo on 2/11/2016.
 */

public class CargaAsistente extends AsyncTask<Void, Void, String> {
    public String api_Asistentes = "http://13.85.65.247/WS_WorkShop360/Service/AsistenteService.svc/consultarAsistentePorId/"+getAsistenteSingleton().getAsistente();
    public Exception exception;
    protected void onPreExecute() {
        //  progressBar.setVisibility(View.VISIBLE);
        //responseView.setText("Cargando");
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
                        line = "["+line+"]";
                        JSONArray jsonArray =new JSONArray(line);

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
