package com.androidprojects.tudevs.tu_orgnzr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidprojects.tudevs.tu_orgnzr.Config.Config;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button log_In_Button = (Button) findViewById(R.id.Log_In_Button);
        // Final EditText username_Input_Text = (EditText) findViewById(R.id.Username_Input);

        log_In_Button.setOnClickListener(new SaveUserListener());

        // Route to Profile Activity if the user has already made the Log In Task

        final String value = getSharedPreferences(Config.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Config.key, Config.defaultValue);

        if (value != Config.defaultValue) {
            Intent intent = new Intent(this, Profile_Activity.class);
            startActivity(intent);
            finish();
        }


    }


    // Saves user preferences on Log_In_Button Click
    private class SaveUserListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            final EditText username_Input_Text = (EditText) findViewById(R.id.Username_Input);
            final EditText password_Input_Text = (EditText) findViewById(R.id.Password_Input);

            // Check if all inputs are filled
            if (username_Input_Text.getText().toString().isEmpty() || password_Input_Text.getText().toString().isEmpty()) {

                Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;

            }

            getSharedPreferences(Config.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().putString("username", username_Input_Text.getText()
                    .toString()).commit();
            getSharedPreferences(Config.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().putString("password", password_Input_Text.getText()
                    .toString()).commit();

            Toast.makeText(getApplicationContext(), "Saved user " + username_Input_Text.getText().toString(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Profile_Activity.class);
            startActivity(intent);
            finish();
        }
    }


}