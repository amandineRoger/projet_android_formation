package com.example.excilys.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Lance l'activité "Envoyer un message"
     *
     * @param view
     */
    public void listenerEnvoyerMessage(View view) {
        Intent intent = new Intent(this, EnvoiActivity.class);
        startActivity(intent);
    }

    /**
     * Lance l'activité "Liste des messages"
     *
     * @param view
     */
    public void launchListeActivity(View view) {
        Intent intent = new Intent(this, ListeActivity.class);
        startActivity(intent);
    }




}
