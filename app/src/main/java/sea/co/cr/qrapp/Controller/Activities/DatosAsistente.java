package sea.co.cr.qrapp.Controller.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.asanchezo.qrapp.R;
import sea.co.cr.qrapp.Controller.ConfirmaAsistente;

import static sea.co.cr.qrapp.Model.Asistente.getAsistenteSingleton;

/*Creado por: Alejandro Sanchez 01-09-2016*/
public class DatosAsistente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_datos_asistente);
        TextView txtNombre = (TextView) findViewById(R.id.tvNombreDato);
        TextView txtCorreo = (TextView) findViewById(R.id.tvCorreoDato);
        TextView txtEmpresa = (
                TextView) findViewById(R.id.tvEmpresaDato);
        TextView txtEstado = (TextView) findViewById(R.id.tvEstadoDato);
        ImageButton btnConfirmar = (ImageButton) findViewById(R.id.btnConfirmar);
        txtNombre.setText(getAsistenteSingleton().getNombre());
        txtCorreo.setText(getAsistenteSingleton().getCorreoElectronico());
        txtEmpresa.setText(getAsistenteSingleton().getEmpresa());
        txtEstado.setText(getAsistenteSingleton().getEstado());
        String t = getAsistenteSingleton().getEstado();
        if(getAsistenteSingleton().getEstado().equals("Confirmado")){
            btnConfirmar.setEnabled(false);
        }




    }

    public void onClickRegresar(View v) {
        finish();
        getAsistenteSingleton().limpiaAsistente();
        Intent k = new Intent(DatosAsistente.this, MainActivity.class);
        startActivity(k);
    }
    public void onClickConfirmar(View v) throws InterruptedException {
        int confirmacion=-2; // inicializando
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Estado de la confirmación");
        ConfirmaAsistente confirmaAsistente = new ConfirmaAsistente();
        confirmaAsistente.execute();
        Thread.sleep(2000);
        confirmacion = getAsistenteSingleton().getConfirmacion();
        switch (confirmacion){
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

}


