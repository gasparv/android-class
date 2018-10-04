package example.chi.tuke.com.lab01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //region #1 - Hello World
        // This is comment
        /* This is
        also commnet*/
        // System.out.println("Hello World!!"); // prints Hello World
        // endregion

        //region #2 - Variables
        long cisloVelke = 1024;    // 2^64
        int cislo = 1024; // 2^32
        short cisloMale = 111; //+-32768
        byte cisloVelmiMale = 127;  //-128 az 127
        boolean isTrue = true;

        cisloVelke = 26;
        cislo = 0x1a;
        cisloMale = 0b11010;

        String a = "Hello";
        String b = "W";
        b = b.concat("orld");
        String c = a+b;
        System.out.println(c);

        String d = String.format("aaa %d .2f", cislo, 2.9876);
        System.out.println(d);
        //endregion

        //region #3 - vetvenie
        cislo = 2;
        if (cislo % 2 == 0) {
            System.out.println("Parne");
        } else {
            System.out.println("Neparne");
        }

        for (int i = 0; i < 90; i++) {
            System.out.println(String.format("%d", i));
        }

        int i = 0;
        while (i < 100) {
            System.out.println(String.format("%d", i));
            i++;
        }
        //endregion

        //region $4 - triedy
        Car car = new Car();
        Seat seat = new Seat();
        seat.speedUp();
        System.out.println(String.format("%d",seat.speed));
        //endregion

        // #5 debuging

        // #6 -> static variables

        // #7 -> class Skoda oddedena od Car, speed up iba o 2

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
