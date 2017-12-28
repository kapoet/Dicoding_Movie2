package com.ervin.dicoding_movie2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ervin on 12/27/2017.
 */

public class AppPreference {
    SharedPreferences prefs;
    Context context;

    public AppPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(long input){
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.app_first_run);
        editor.putLong(key,input);
        editor.apply();
    }

    public long getFirstRun(){
        String key = context.getResources().getString(R.string.app_first_run);
        return prefs.getLong(key, 10);
    }
    public void setFirstRun2(long input){
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.app_first_run2);
        editor.putLong(key,input);
        editor.apply();
    }

    public long getFirstRun2(){
        String key = context.getResources().getString(R.string.app_first_run2);
        return prefs.getLong(key, -2);
    }
}
