package sk.tuke.smartlab.lab7_backgroundservice_location;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Handler h;
    Runnable r;
    TextView cislo;
    private int odpocitajCislo(){
        int odpocitaneCislo = 0;
        odpocitaneCislo = Integer.parseInt(cislo.getText().toString());
        odpocitaneCislo--;
        return odpocitaneCislo;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cislo = findViewById(R.id.tv_cislo);
        h = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                int noveCislo = odpocitajCislo();
                if(noveCislo == 0){
                    startActivity(new Intent(MainActivity.this,MapaActivity.class));
                    h.removeCallbacks(this);
                }
                cislo.setText(Integer.toString(noveCislo));
                h.postDelayed(r,1000);
            }
        };
        ((Button)findViewById(R.id.btn_startWatch))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        h.post(r);
                    }
                });
    }
}
