package vn.edu.uit.jrfsit.alarms;

/**
 * Created by KhánhDuy on 12/29/2015.
 */

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import vn.edu.uit.jrfsit.R;
import vn.edu.uit.jrfsit.activity.LoginActivity;
import vn.edu.uit.jrfsit.service.LocationService;
import vn.edu.uit.jrfsit.service.RecService;
import vn.edu.uit.jrfsit.utils.BadgeUtils;
import vn.edu.uit.jrfsit.utils.Utils;

public class AlarmReceiver extends BroadcastReceiver {
    private int numMessagesOne = 0;
    private int notificationIdOne = 111;
    private NotificationManager myNotificationManager;
    @Override
    public void onReceive(final Context context, Intent intent) {
        final RecService recService = new RecService();
        final String userId =  intent.getStringExtra("UserId");
        new Thread(new Runnable() {
            public void run() {
                int numberjob= 0;
                numberjob = recService.getRecNotification(userId);

                if(numberjob>0){
                    displayNotificationOne(context,numberjob);
                }
            }
        }).start();
        /////////////////////////////////
        LocationService gps = new LocationService(context.getApplicationContext());
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Geocoder geocoder;
            List<Address> addresses=null;
            geocoder = new Geocoder(context, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                final String address = addresses.get(0).getLocality();
                final RecService service = new RecService();
                new Thread(new Runnable() {
                    public void run() {
                        boolean check =  service.updateLocationRec(userId,address);
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //gps.showSettingsAlert();
        }
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
    }

    protected void displayNotificationOne(Context context , int numberJob) {

        // Invoking the default notification service
        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(context);

        mBuilder.setContentTitle("Công việc giới thiệu từ JRIT");
        mBuilder.setContentText("Có " + numberJob + " công việc mới giới thiệu cho bạn");
        mBuilder.setTicker("Explicit: Công việc giới thiệu cho bạn!");
        mBuilder.setSmallIcon(R.drawable.logo1, 1);
        BadgeUtils.setBadge(context,numberJob);

        // Increase notification number every time a new notification arrives
        mBuilder.setNumber(++numMessagesOne);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, LoginActivity.class);
        resultIntent.putExtra("notificationId", notificationIdOne);

        //This ensures that navigating backward from the Activity leads out of the app to Home page
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Adds the back stack for the Intent
        stackBuilder.addParentStack(LoginActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_ONE_SHOT //can only be used once
                );
        // start the activity when the user clicks the notification text
        mBuilder.setContentIntent(resultPendingIntent);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
        long[] vibrate = { 0, 100, 200, 300 };
        mBuilder.setVibrate(vibrate);

        myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        // pass the Notification object to the system
        myNotificationManager.notify(notificationIdOne, mBuilder.build());
    }
}