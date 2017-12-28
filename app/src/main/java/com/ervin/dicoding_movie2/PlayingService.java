package com.ervin.dicoding_movie2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

public class PlayingService extends Service {
    private final static String TAG = "CheckRecentPlay";
    private static Long MILLISECS_PER_DAY = 86400000L;
    private static Long MILLISECS_PER_MIN = 60000L;
    //private static long delay = MILLISECS_PER_MIN * 2;   // 2 minutes (for testing)
    private static long delay = MILLISECS_PER_DAY * 15;   // 15 days
    AppPreference ap;
 ArrayList<Movie> dataa;
    int NOTIFICAITION_ID = 1;
    public PlayingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ap = new AppPreference(this);
        if(ap.getFirstRun2()!=-2){
            sendNotification();
        }
        Log.d(TAG, "onCreate first run 2: "+ap.getFirstRun2());
        // Set an alarm for the next time this service should run:
        ap.setFirstRun2(0);
        setAlarm();
        Log.v(TAG, "Service stopped");
        stopSelf();
    }
    public void setAlarm() {

        Intent serviceIntent = new Intent(this, PlayingService.class);
        PendingIntent pi = PendingIntent.getService(this, 131313, serviceIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pi);
        Log.v(TAG, "Alarm set");
    }

    public void sendNotification() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+BuildConfig.TMDB_API+"&language=en-US";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String hasil = new String(responseBody);
                ArrayList<Movie> dataMovie = new ArrayList<>();
                try {
                    JSONObject a =  new JSONObject(hasil);
                    JSONArray b = a.getJSONArray("results");
                    for (int i=0;i<b.length();i++){
                        JSONObject c = b.getJSONObject(i);
                        Movie movie = new Movie(c.getString("title"),c.getString("release_date"),c.getString("overview"),c.getString("poster_path"),c.getString("id"));
                        dataMovie.add(movie);
                    }
                    dataa = new ArrayList<>();
                    dataa = dataMovie;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                makeNotif();
            }
        });

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void makeNotif() {
        for (int i = 0; i < dataa.size(); i++) {
            Intent mainIntent = new Intent(this, DetailActivity.class);
            Movie movie = dataa.get(i);
            mainIntent.putExtra(DetailActivity.DATA_MOVE, movie);

            Notification.Builder noti = (Notification.Builder) new Notification
                    .Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentIntent(PendingIntent.getActivity(this, i, mainIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT))
                    .setContentTitle(movie.getTitle())
                    .setContentText(movie.getSynopsis())
                    .setSubText(getResources()
                            .getString(R.string.subtext))
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplication());
            notificationManagerCompat.notify(NOTIFICAITION_ID, noti.build());
            NOTIFICAITION_ID+=1;

            Log.v(TAG, "Notification sent");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
