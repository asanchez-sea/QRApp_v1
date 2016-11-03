package sea.co.cr.qrapp.Controller.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.asanchezo.qrapp.R;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static sea.co.cr.qrapp.Model.Asistente.getAsistenteSingleton;

public class QrScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private static final int REQUEST_CAMERA_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);
        requestCameraPermissionsAndStartScanner();
    }
    public void requestCameraPermissionsAndStartScanner(){
        /*Solicitando permisos para utilizar la camara  - se requiere para dispositivos con android v6*/
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED){
                    openScanner();
                }
                else {
                    if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
                        Toast.makeText(this,"No Permission to use the Camera services", Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[] {android.Manifest.permission.CAMERA},REQUEST_CAMERA_RESULT);
                    ImageButton buttonQR = (ImageButton) findViewById(R.id.buttonQR);
                    buttonQR.performClick();
                }
            }
            else{

                openScanner();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void openScanner(){
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }
    @Override
    protected void onPause () {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
    /*El resultado de la lectura se obtiene de este metodo*/
        Log.v("handleResult",result.getText());
        mScannerView.resumeCameraPreview(this);
        getAsistenteSingleton().setAsistente(result.getText());
        getAsistenteSingleton().setEstado("");
        getAsistenteSingleton().setEmpresa("");
        getAsistenteSingleton().setNombre("");
        getAsistenteSingleton().setCorreoElectronico("");
        finish();
        Intent k = new Intent(QrScanner.this, DatosAsistente.class);
        startActivity(k);
    }
}

