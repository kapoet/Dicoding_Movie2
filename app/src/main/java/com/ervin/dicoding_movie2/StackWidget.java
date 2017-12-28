package com.ervin.dicoding_movie2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class StackWidget extends AppWidgetProvider {
    public static final String TOAST_ACTION = "com.example.dicoding.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.dicoding.EXTRA_ITEM";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, WidgetService.class);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stack_widget);

        views.setRemoteAdapter( R.id.stack_view, intent);

        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastIntent = new Intent(context, StackWidget.class);

        toastIntent.setAction(StackWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(TOAST_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            String viewIndex = intent.getStringExtra(EXTRA_ITEM);
            Toast.makeText(context, context.getResources().getString(R.string.click_widget) + viewIndex, Toast.LENGTH_SHORT).show();
        }
        if (intent.getAction().equals(
                AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            ComponentName name = new ComponentName(context, StackWidget.class);
            int [] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);

            AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(ids,R.id.stack_view);

        }
        super.onReceive(context, intent);
    }
}

