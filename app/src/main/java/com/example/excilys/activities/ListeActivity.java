package com.example.excilys.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import tasks.GetListTask;
import util.MessagesMapper;

/**
 * Created by excilys on 05/04/16.
 */
public class ListeActivity extends AppCompatActivity {


    String username;
    String password;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        //Recupération des identifiants
        SharedPreferences settings = getSharedPreferences(MainActivity.SHARED_PREF_NAME, 0);
        username = settings.getString(MainActivity.USERNAME, null);
        password = settings.getString(MainActivity.PWD, null);

        listView = (ListView) findViewById(R.id.listView_message);
        listenerListeMessage(null);
    }

    /**
     * Lance la tache asynchrone chargée de récupérer la liste des messages et remplit la listView
     *
     * @param view
     */
    public void listenerListeMessage(View view) {
        //lancement de la tâche
        GetListTask listTask = new GetListTask(username, password);
        listTask.execute();

        String liste = "";
        try {
            //récupération des messages
            liste = (String) listTask.get();
            Log.d("listenerListeMessage", "response = "+liste);

        } catch (Exception e) {
            Log.e("listenerListeMessage", e.getMessage());
        }


        //Remplissage du listView
        ListAdapter adapter = new SimpleAdapter(this, MessagesMapper.messagesListToArrayList(liste), R.layout.list_element, new String[]{"nom", "message"}, new int[]{R.id.pseudo, R.id.textMessage});
        listView.setAdapter(adapter);
    }

}
