package sk.tuke.smartlab.lab1_layouts_activities_intents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //FIND VIEW BY ID mi vytiahne button komponentu
        //Nemusim ju pretypovat lebo kasdy view ma moznost kliknutia - on click listener
        //VYtvorim si novy on click listener -t.j. cakam na kliknutia
        ((Button) findViewById(R.id.btn_detaily)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tu dam to co sa ma stat ked sa klikne na tlacidlo
                //Intent mi povie o tom co chcem urobit ... aktualne prejst z konextu aktivity MainActivity + chcem vytvorit novy kontext z aktivity FlightDetails
                Intent i = new Intent(MainActivity.this,FlightDetails.class);
                startActivity(i);
            }
        });
    }
}
