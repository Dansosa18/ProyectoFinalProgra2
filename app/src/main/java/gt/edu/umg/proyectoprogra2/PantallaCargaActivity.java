package gt.edu.umg.proyectoprogra2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class PantallaCargaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_carga_activity);

        TimerTask tarea = new TimerTask(){
            @Override
            public void run() {
                Intent intent = new Intent(PantallaCargaActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 3000);


    }
}
