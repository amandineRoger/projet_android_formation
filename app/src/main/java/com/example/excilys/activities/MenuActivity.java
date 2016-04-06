package com.example.excilys.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import tasks.GetListTask;

/**
 * Created by excilys on 05/04/16.
 */
public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    /**
     * Lance l'activité "Envoyer un message"
     * @param view
     */
    public void listenerEnvoyerMessage(View view) {
        Intent intent = new Intent(this, EnvoiActivity.class);
        startActivity(intent);
    }

    /**
     * Lance l'activité "Liste des messages"
     * @param view
     */
    public void launchListeActivity(View view){
        Intent intent = new Intent(this, ListeActivity.class);
        startActivity(intent);
    }


}
