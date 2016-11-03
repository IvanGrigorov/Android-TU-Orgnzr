package com.androidprojects.tudevs.tu_orgnzr.Events;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by Ivan Grigorov on 23/04/2016.AbstractEvent
 * Abstraction of all events in the app
 * General Event Construction
 */
public abstract class AbstractEvent implements View.OnClickListener {

    protected Context context;
    protected Activity activity;

    public AbstractEvent(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {

    }
}
