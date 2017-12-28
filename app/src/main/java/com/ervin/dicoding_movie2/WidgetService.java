package com.ervin.dicoding_movie2;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ervin on 12/28/2017.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
