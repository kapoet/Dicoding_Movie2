package com.ervin.dicoding_movie2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class DailyService extends Service {
    private final static String TAG = "CheckRecentPlay";
    private static Long MILLISECS_PER_DAY = 86400000L;
    private static Long MILLISECS_PER_MIN = 60000L;
    private static long delay = MILLISECS_PER_MIN * 1;   // 1 minutes (for testing)
    //private static long delay = MILLISECS_PER_DAY * 1;   // 1 days
    AppPreference ap;
    public DailyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ap = new AppPreference(getApplicationContext());
        if(ap.getFirstRun()!=10){
            if (ap.getFirstRun() < System.currentTimeMillis() - delay){
                sendNotification();
            }
        }
        Log.d(TAG, "Service started"+ap.getFirstRun());
        // Set an alarm for the next time this service should run:
        setAlarm();
        //sendNotification();
        Log.v(TAG, "Service stopped");
        stopSelf();
    }
    public void setAlarm() {

        Intent serviceIntent = new Intent(this, DailyService.class);
        PendingIntent pi = PendingIntent.getService(this, 111313, serviceIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pi);
        Log.v(TAG, "Alarm set");
    }

    public void sendNotification() {

        Intent mainIntent = new Intent(this, MainActivity.class);
        Notification.Builder noti = (Notification.Builder) new Notification
                .Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(PendingIntent.getActivity(this, 111314, mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle("We Miss you. . ")
                .setContentText("Lets open this app. . .")
                .setSubText("Daily Remainder")
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplication());
        notificationManagerCompat.notify(111315, noti.build());
//        NotificationManager notificationManager
//                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(131315, noti);

        Log.v(TAG, "Notification sent");
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }
}
