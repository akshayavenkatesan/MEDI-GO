package com.example.ed_care;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class NotificationReceiver extends BroadcastReceiver {
    int count = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toastMessage");


        FirebaseDatabase.getInstance().getReference(message).child("Alert").setValue("0");

        Toast.makeText(context, "Acknowledged", Toast.LENGTH_SHORT).show();
        count=1;
    }
}