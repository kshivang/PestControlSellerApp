package com.adurcup.pestcontrolsellerapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kshivang on 23/07/16.
 * This service for receive
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService{
    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        final List<Commitment> commitments= new ArrayList<>();
        Map data = remoteMessage.getData();

        try{
            JSONObject service = new JSONObject((String)data.
                    get("current_service"));
            JSONArray outstandServices = new JSONArray((String)data.
                    get("outstanding_services"));

            Commitment currentService = new Commitment(
                    service.getString("date"), service.getString("time"),
                    service.getString("area"), service.getString("location"),
                    service.getString("address_line_one"), service.getString("address_line_two")
            );
            commitments.add(currentService);

            for (int i = 0; i < outstandServices.length(); i++) {

                JSONObject osService = outstandServices.getJSONObject(i);

                Commitment outstandingServices = new Commitment(
                        osService.getString("date"), osService.getString("time"),
                        osService.getString("area"), osService.getString("location"),
                        osService.getString("address_line_one"),
                        osService.getString("address_line_two"));

                commitments.add(outstandingServices);
            }

            sendNotification(remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody(),
                    commitments);

        }catch (JSONException e) {
            e.printStackTrace();
        }



        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String title, String messageBody, List<Commitment> commitments) {
        Intent resultIntent = new Intent(this, newOrderAlertActivity.class).
                putParcelableArrayListExtra(Constant.KEY_COMMITMENTS,
                        (ArrayList<Commitment>) commitments);

        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */,
                resultIntent, PendingIntent.FLAG_ONE_SHOT);


        if(title == null || title.length() == 0 ){
            title = "Adurcup";
        }


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(Constant.ID_CLOUD_NOTIFICATION, notificationBuilder.build());
    }

}
