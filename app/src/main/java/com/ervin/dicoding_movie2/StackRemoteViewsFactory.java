package com.ervin.dicoding_movie2;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.ervin.dicoding_movie2.DatabaseContarct.CONTENT_URI;

/**
 * Created by ervin on 12/28/2017.
 */

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    //private List<Bitmap> mWidgetItems = new ArrayList<>();
    private List<Movie> mWidgetItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    Cursor list;
    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    @Override
    public void onCreate() {
        list=mContext.getContentResolver().query(CONTENT_URI,null,null,null,null);
        for (int i = 0;i<list.getCount();i++){
            Movie mv = getItem(i);
            Log.d("asdf", "onCreate: coba "+mv.getPath_image());
            mWidgetItems.add(mv);
        }
    }

    @Override
    public void onDataSetChanged() {
        mWidgetItems.clear();
        final long identityToken = Binder.clearCallingIdentity();
        list=mContext.getContentResolver().query(CONTENT_URI,null,null,null,null);
        for (int i = 0;i<list.getCount();i++){
            Movie mv = getItem(i);
            Log.d("asdf", "onCreate: coba "+mv.getPath_image());
            mWidgetItems.add(mv);
        }

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bmp = null;

        try {
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w342"+mWidgetItems.get(i).getPath_image())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
        }catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error","error");
        }
        rv.setImageViewBitmap(R.id.iv_stack,bmp);
        Bundle extras = new Bundle();
        extras.putString(StackWidget.EXTRA_ITEM, mWidgetItems.get(i).getRelease());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.iv_stack, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Movie getItem(int position){
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(list);
    }

}
