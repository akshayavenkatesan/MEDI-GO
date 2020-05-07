package com.example.ed_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.ed_care.App.CHANNEL_1_ID;

public class home extends AppCompatActivity {

    DatabaseReference reff;
    private NotificationManagerCompat notificationManager;
    String mob, alert;
    int c=0;
    Main2Activity m = new Main2Activity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getPackageManager().getLaunchIntentForPackage("com.example.priyanka.mapsdemo");
        startActivity(i);



        notificationManager = NotificationManagerCompat.from(this);
        //Toast.makeText(this, m.mobile, Toast.LENGTH_SHORT).show();
        reff = FirebaseDatabase.getInstance().getReference().child("" + m.mobile);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alert = dataSnapshot.child("Alert").getValue().toString();

                if (alert.equals("1")) {

                    Toast.makeText(home.this, "ALERT", Toast.LENGTH_SHORT).show();

                    notif();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void notif()
    {
        //Toast.makeText(home.this, "INSIDE", Toast.LENGTH_SHORT).show();

        Intent activityIntent = new Intent(this, home.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", m.mobile);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.one)
                .setContentTitle("ALERT")
                .setContentText("PLEASE ATTEND YOUR PATIENT!!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();

        notificationManager.notify(1, notification);


    }





}


