package com.example.excilys.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import tasks.GetListTask;

/**
 * Created by excilys on 05/04/16.
 */
public class MenuActivity extends AppCompatActivity {

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        SharedPreferences settings = getSharedPreferences(MainActivity.SHARED_PREF_NAME, 0);
        username = settings.getString(MainActivity.USERNAME, null);
        password = settings.getString(MainActivity.PWD, null);


    }

    public void listenerListeMessage(View view) {
        GetListTask listTask = new GetListTask(username, password, this);
        listTask.execute();

    }

    public void listenerEnvoyerMessage(View view) {

        Intent intent = new Intent(this, EnvoiActivity.class);
        startActivity(intent);
    }

    public void launchListeActivity(){
        Intent intent = new Intent(this, ListeActivity.class);
        startActivity(intent);
    }


}
