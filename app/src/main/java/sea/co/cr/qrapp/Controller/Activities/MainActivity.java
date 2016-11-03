package sea.co.cr.qrapp.Controller.Activities;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.asanchezo.qrapp.R;


/*created by Alejandro Sanchez O
* Date: 31/10/2016*/
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton imageButton = (ImageButton) findViewById(R.id.buttonQR);
        //Se le agregan un efecto similar al "onhover" al imagebutton
        addClickEffect(imageButton);

    }

    public void onClick(View v){
        startScannerActivity();
    }
    /*Efecto para los botones*/
    void addClickEffect(View view)
    {
        Drawable drawableNormal = view.getBackground();

        Drawable drawablePressed = view.getBackground().getConstantState().newDrawable();
        drawablePressed.mutate();
        drawablePressed.setColorFilter(Color.argb(50, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);

        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[] {android.R.attr.state_pressed}, drawablePressed);
        listDrawable.addState(new int[] {}, drawableNormal);
        view.setBackground(listDrawable);
    }

    public void startScannerActivity(){
        Intent k = new Intent(MainActivity.this, QrScanner.class);
        startActivity(k);
    }

}
