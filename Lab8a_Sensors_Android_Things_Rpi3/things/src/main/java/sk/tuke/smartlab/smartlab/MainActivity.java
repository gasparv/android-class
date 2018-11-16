package sk.tuke.smartlab.smartlab;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.galarzaa.androidthings.Rc522;
import com.google.android.things.contrib.driver.bmx280.Bmx280;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.SpiDevice;

import java.io.IOException;

public class MainActivity extends Activity {
    private Rc522 mRc522;
    private TextView tv_cardId;
    private TextView tv_temp;
    private TextView tv_pressure;
    private Button btn_scan;
    private Bmx280 mBmp280;
    private Handler h;
    private Runnable runnable;
    private PeripheralManager pioService;
    RfidTask rr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        h = new Handler();
        tv_cardId = findViewById(R.id.tv_cardId);
        btn_scan = findViewById(R.id.btn_scan);
        tv_temp = findViewById(R.id.tv_temp);
        tv_pressure = findViewById(R.id.tv_pressure);
        pioService = PeripheralManager.getInstance();
            runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        mBmp280 = new Bmx280("I2C1", 0x76);
                        mBmp280.setTemperatureOversampling(Bmx280.OVERSAMPLING_1X);
                        mBmp280.setMode(Bmx280.MODE_NORMAL);
                        mBmp280.setPressureOversampling(Bmx280.OVERSAMPLING_1X);
                        float temperature = mBmp280.readTemperature();
                        float pressure = mBmp280.readPressure();
                        tv_temp.setText(Float.toString(temperature));
                        tv_pressure.setText(Float.toString(pressure));
                    } catch (IOException e) {
                        // error reading temperature
                    }
                    try {
                        // If nothing else needs to read sensor values, consider calling setMode(Bmx280.MODE_SLEEP)
                        mBmp280.close();
                    } catch (IOException e) {
                        // error closing sensor
                    }

                    h.postDelayed(this, 500);
                }
            };
        h.post(runnable);

        try {
            SpiDevice spiDevice = pioService.openSpiDevice("SPI0.0");
            Gpio resetPin = pioService.openGpio("BCM25");
            mRc522 = new Rc522(spiDevice, resetPin);


        } catch (IOException e) {
            e.printStackTrace();
        }
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rr = new RfidTask(mRc522);
                    rr.execute();
            }
        });
    }

    private class RfidTask extends AsyncTask<Object, Object, String> {
        private static final String TAG = "RfidTask";
        private Rc522 rc522;

        RfidTask(Rc522 rc522) {
            this.rc522 = rc522;
        }

        @Override
        protected String doInBackground(Object... params) {
            rc522.stopCrypto();
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
                //Check if a RFID tag has been found
                if (!rc522.request()) {
                    continue;
                }
                //Check for collision errors
                if (!rc522.antiCollisionDetect()) {
                    continue;
                }
                byte[] uuid = rc522.getUid();

                String result = "";
                for(byte singleByte:uuid){
                    result += Integer.toString(Byte.toUnsignedInt(singleByte));
                }
                return result;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv_cardId.setText(s);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
