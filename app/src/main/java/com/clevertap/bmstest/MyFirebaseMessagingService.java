package com.clevertap.bmstest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.NotificationInfo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.URL;
import java.util.Map;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by divyekant on 31/05/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String imgUrl = "";
    String appImgUrl = "";
    String notifTitle = "";
    String notifText = "";
    RemoteViews contentViewBig, contentViewSmall;
    private static final String TAG = "MyFirebaseMsgService";

    String countDownTimerCheck = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        try {
            if (remoteMessage.getData().size() > 0) {
                Bundle extras = new Bundle();
                for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                    extras.putString(entry.getKey(), entry.getValue());

                    countDownTimerCheck = remoteMessage.getData().get("count");


                }

                NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);

                if (info.fromCleverTap) {
                    if (!countDownTimerCheck.equals(null) || !countDownTimerCheck.isEmpty()) {
                    //Comment the code where CleverTap renders the notification for you
                    //CleverTapAPI.createNotification(getApplicationContext(), extras);
                    long when = System.currentTimeMillis();
                    final NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    contentViewBig = new RemoteViews(getPackageName(), R.layout.custom_notification);
                    contentViewSmall = new RemoteViews(getPackageName(), R.layout.custom_notification_small);
                    //Null and Empty checks for your Key Value Pairs
                    if (imgUrl != null && !imgUrl.isEmpty()) {
                        URL imgUrlLink = new URL(imgUrl);
                        contentViewBig.setImageViewBitmap(R.id.image_pic, BitmapFactory.decodeStream(imgUrlLink.openConnection().getInputStream()));
                    }
                    if (appImgUrl != null && !appImgUrl.isEmpty()) {
                        URL appImgUrlLink = new URL(appImgUrl);
                        contentViewBig.setImageViewBitmap(R.id.image_app, BitmapFactory.decodeStream(appImgUrlLink.openConnection().getInputStream()));
                        contentViewSmall.setImageViewBitmap(R.id.image_app, BitmapFactory.decodeStream(appImgUrlLink.openConnection().getInputStream()));
                    }
                    if (notifTitle != null && !notifTitle.isEmpty()) {
                        contentViewBig.setTextViewText(R.id.title, notifTitle);
                        contentViewSmall.setTextViewText(R.id.title, notifTitle);
                    }

                    if (notifText != null && !notifText.isEmpty()) {
                        contentViewBig.setTextViewText(R.id.text, notifText);
                        contentViewSmall.setTextViewText(R.id.text, notifText);
                    }

                    Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                    notificationIntent.putExtra("extras",extras);
                    PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

                    final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setCustomContentView(contentViewSmall)
                            .setCustomBigContentView(contentViewBig)
                            .setContentTitle("Custom Notification")
                            .setContentIntent(contentIntent)
                            .setAutoCancel(true)
                            .setWhen(when)
                            .setChannel("test_cid");
                    mNotificationManager.notify(1, notificationBuilder.build());


                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            CountDownTimer cdt5 = new CountDownTimer(Integer.parseInt(countDownTimerCheck), 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    // = millisUntilFinished/1000;
                                    contentViewBig.setTextViewText(R.id.title, "Seconds remaining: " + millisUntilFinished / 1000);
                                    contentViewSmall.setTextViewText(R.id.title, "Seconds remaining: " + millisUntilFinished / 1000);
                                    mNotificationManager.notify(1, notificationBuilder.build());
                                }

                                @Override
                                public void onFinish() {
                                    Log.d("DONE", "DONE");
                                    mNotificationManager.cancel(1);
                                }
                            }.start();
                        }
                    });
                        mNotificationManager.notify(1, notificationBuilder.build());

                    }
                    else    {
                        CleverTapAPI.createNotification(this,extras);
                    }


                } else {
                    // not from CleverTap handle yourself or pass to another provider
                }
            }
        } catch (Throwable t) {
            Log.d("MYFCMLIST", "Error parsing FCM message", t);
        }
        //startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


    }
}
