package com.adurcup.pestcontrolsellerapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kshivang on 21/07/16.
 * This is test activity to check activity in working fine
 */
public class MainActivity extends AppCompatActivity{


    String[] time = { "12:30 PM", "12:45 PM", "1:00 PM" };
    String[] location = { "Some Restaurant", "Some Restaurant", "Some Restaurant" };
    String[] addressLine1 = { "Some location, some other sub location, Location",
            "Some location, some other sub location, Location",
            "Some location, some other sub location, Location" };
    String[] addressLine2 = { "MAIN AREA OF SERVICE", "MAIN AREA OF SERVICE",
            "MAIN AREA OF SERVICE" };
    int mId = 1111;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_activity);

        final List<Commitment> commitments= new ArrayList<>();

        for(int i = 0; i < time.length; i++ ) {
            Commitment commitment = new Commitment(time[i],
                    location[i], addressLine1[i], addressLine2[i]);
            commitments.add(commitment);
        }

        findViewById(R.id.create_notification_bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("My notification")
                                .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(MainActivity.this, newOrderAlertActivity.class).
                        putParcelableArrayListExtra("constant", (ArrayList<Commitment>) commitments);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
// Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(newOrderAlertActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                mNotificationManager.notify(mId, mBuilder.build());

            }
        });

        findViewById(R.id.create_activity_bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, newOrderAlertActivity.class));
            }
        });
    }
}
