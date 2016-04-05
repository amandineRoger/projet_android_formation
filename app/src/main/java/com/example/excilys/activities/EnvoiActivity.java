package com.example.excilys.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import tasks.SendMessageTask;

/**
 * Created by excilys on 05/04/16.
 */
public class EnvoiActivity extends AppCompatActivity {

    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoyer);


    }


    public String getMessage(){
        return message;
    }

    public void sendMessage(View view){

        EditText editText = (EditText) findViewById(R.id.editText_message);
        message = editText.getText().toString();



        SharedPreferences settings = getSharedPreferences(MainActivity.SHARED_PREF_NAME,0);
        String username = settings.getString(MainActivity.USERNAME, null);
        String password = settings.getString(MainActivity.PWD, null);

        SendMessageTask messageTask = new SendMessageTask(username, password, this);
        messageTask.execute();
    }
}
