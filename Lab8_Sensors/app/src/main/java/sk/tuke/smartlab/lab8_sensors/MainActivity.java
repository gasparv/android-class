package sk.tuke.smartlab.lab8_sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private SensorManager sm;
private TextView tv_sensor_value;
private SensorEventListener se;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_sensor_value = findViewById(R.id.tv_sensor_value);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        se = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                tv_sensor_value.setText(Float.toString(sensorEvent.values[0]));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sm.registerListener(se,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(se);
    }
}
