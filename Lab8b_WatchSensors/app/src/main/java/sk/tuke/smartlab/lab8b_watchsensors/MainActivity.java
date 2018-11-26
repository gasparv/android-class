package sk.tuke.smartlab.lab8b_watchsensors;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private Line mLineX;
    private Line mLineY;
    private Line mLineZ;
    private List<PointValue> valuesX;
    private List<PointValue> valuesY;
    private List<PointValue> valuesZ;
    private SensorManager sm;
    private Sensor sensor;
    private SensorEventListener sensorListener;
    private LineChartView chartView;
    private LineChartData chartData;
    private long i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Enables Always-on
        setAmbientEnabled();

        chartView = findViewById(R.id.v_chartview);


        valuesX = new ArrayList<>();
        valuesY = new ArrayList<>();
        valuesZ = new ArrayList<>();

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                i++;

                if(valuesX.size()>100){
                    valuesX.remove(0);
                }
                valuesX.add(new PointValue(i,sensorEvent.values[0]));
                if(valuesY.size()>100){
                    valuesY.remove(0);
                }
                valuesY.add(new PointValue(i,sensorEvent.values[1]));
                if(valuesZ.size()>100){
                    valuesZ.remove(0);
                }
                valuesZ.add(new PointValue(i,sensorEvent.values[2]));

                mLineX = new Line(valuesX).setColor(Color.RED).setPointRadius(1);
                mLineY = new Line(valuesY).setColor(Color.GREEN).setPointRadius(1);
                mLineZ = new Line(valuesZ).setColor(Color.BLUE).setPointRadius(1);
                List<Line> lineArray = new ArrayList<>();
                lineArray.add(mLineX);
                lineArray.add(mLineY);
                lineArray.add(mLineZ);
                chartData = new LineChartData(lineArray);
                chartView.setLineChartData(chartData);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sensorListener);
    }
}
