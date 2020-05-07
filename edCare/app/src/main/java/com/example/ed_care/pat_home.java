package com.example.ed_care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class pat_home extends AppCompatActivity {

    Button b;
    DatabaseReference reff;
    private SensorManager sm;

    private float acelVal;
    private float acelLast;
    private float shake;
    int count =0;
    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pat_home);

        mobile = getIntent().getStringExtra("aide mob");
        //b = (Button)findViewById(R.id.help);

        reff = FirebaseDatabase.getInstance().getReference().child("Member");

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelLast = SensorManager.GRAVITY_EARTH;
        acelVal = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
    }
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z ));

            float delta = acelVal - acelLast;
            shake = shake* 0.9f + delta;

            if(shake > 12 && count == 0)
            {
                Toast.makeText(pat_home.this, "ALERT SENT !!!" , Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference(""+mobile).child("Alert").setValue("1");

                count++;
            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };



        /*b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reff.push().setValue("ALERT!!!");
                Toast.makeText(MainActivity.this, "data inserted", Toast.LENGTH_SHORT).show();
            }
        });*/


}
