package com.androidprojects.tudevs.tu_orgnzr.Events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.androidprojects.tudevs.tu_orgnzr.Edit_Program_Mode;

/**
 * Created by Ivan Grigorov on 17/04/2016.
 * Creates event for clicking the edit programm button
 */
public class EditProgrammOnClickEvent extends AbstractEvent implements View.OnClickListener {

    public EditProgrammOnClickEvent(Context context, Activity activity) {
        super(context, activity);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this.context, Edit_Program_Mode.class);
        this.context.startActivity(intent);
        this.activity.finish();
    }
}
