package com.clevertap.bmstest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.NotificationInfo;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;

import java.net.URL;
import java.util.Map;
import java.util.Random;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {
//    String imgUrl="";
//    String appImgUrl="";
//    String notifTitle="";
//    String notifText="";
//    RemoteViews contentViewBig,contentViewSmall;


   // @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);   //Set Log level to DEBUG log warnings or other important messages

        setContentView(R.layout.activity_main);

//        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
//        NotificationChannel notificationChannel = new NotificationChannel("test_cid", "test_name",1 );
//        notificationChannel.setDescription("desc");
//        notificationManager.createNotificationChannel(notificationChannel);

//        NotificationChannel androidChannel = new NotificationChannel("test_cid", "test_name", NotificationManager.IMPORTANCE_DEFAULT);
//        NotificationManager notificationManager = getSystemService(NotificationManager.class);
//        notificationManager.createNotificationChannel(androidChannel);

//
//

    }

    public void addtocart(View view) {
        try {
            CleverTapAPI ct;

            ct = CleverTapAPI.getInstance(getApplicationContext());

            ct.event.push("Add to Cart");



        } catch (CleverTapMetaDataNotFoundException e) {
            // handle appropriately

        } catch (CleverTapPermissionsNotSatisfied e) {
            // handle appropriately

        }

    }
}
