package com.androidprojects.tudevs.tu_orgnzr.Events;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.androidprojects.tudevs.tu_orgnzr.Create_New_Note_Activity;
import com.androidprojects.tudevs.tu_orgnzr.Edit_Program_Mode;

/**
 * Created by Ivan Grigorov on 28/04/2016.
 * Redirects to new event activity
 */
public class NewNoteOnClickEvent extends AbstractEvent implements View.OnClickListener {

        public NewNoteOnClickEvent(Context context, Activity activity) {
            super(context, activity);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(this.context, Create_New_Note_Activity.class);
            this.context.startActivity(intent);
            this.activity.finish();
        }
}
