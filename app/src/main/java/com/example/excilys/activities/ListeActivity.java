package com.example.excilys.activities;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import tasks.GetListTask;
import util.MessagesMapper;

/**
 * Created by excilys on 05/04/16.
 */
public class ListeActivity extends AppCompatActivity {

    public static final int LIMIT = 10;


    String username;
    String password;
    ListView listView;
    int currentPage;
    private static boolean lastPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        //Recupération des identifiants
        SharedPreferences settings = getSharedPreferences(MainActivity.SHARED_PREF_NAME, 0);
        username = settings.getString(MainActivity.USERNAME, null);
        password = settings.getString(MainActivity.PWD, null);
        currentPage = 1;
        lastPage = false;

        listView = (ListView) findViewById(R.id.listView_message);
        //listenerListeMessage(null);
        updateMessagesList();
    }

    private void updateMessagesList() {
        GetListTask listTask = new GetListTask(username, password);
        listTask.execute(currentPage);

        String liste = "";
        try {
            //récupération des messages
            liste = (String) listTask.get();
            Log.d("listenerListeMessage", "response = " + liste);

        } catch (Exception e) {
            Log.e("listenerListeMessage", e.getMessage());
        }


        //Remplissage du listView
        ListAdapter adapter = new SimpleAdapter(this, MessagesMapper.messagesListToArrayList(liste), R.layout.list_element, new String[]{"nom", "message"}, new int[]{R.id.pseudo, R.id.textMessage});
        listView.setAdapter(adapter);
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
            Log.d("listenerListeMessage", "response = " + liste);

        } catch (Exception e) {
            Log.e("listenerListeMessage", e.getMessage());
        }


        //Remplissage du listView
        ListAdapter adapter = new SimpleAdapter(this, MessagesMapper.messagesListToArrayList(liste), R.layout.list_element, new String[]{"nom", "message"}, new int[]{R.id.pseudo, R.id.textMessage});
        listView.setAdapter(adapter);
    }

    public void refreshMessagesList(View view) {
        currentPage = 1;
        setCurrentPage(currentPage);
        updateMessagesList();
    }


    public void listenerNextPage(View view) {
        setCurrentPage(currentPage + 1);
    }

    public void listenerPreviousPage(View view) {
        setCurrentPage(currentPage - 1);
    }


    private void setCurrentPage(int newPageNumber) {
        TextView pageNumberTextView = (TextView) findViewById(R.id.textview_current_page);
        pageNumberTextView.setText(String.valueOf(newPageNumber));
        currentPage = newPageNumber;
        updateMessagesList();
        paginationButtonsManagement();
    }

    private void paginationButtonsManagement() {

        ImageButton previous = (ImageButton) findViewById(R.id.button_prev);
        ImageButton next = (ImageButton) findViewById(R.id.button_next);

        if (currentPage == 1) {
            previous.setVisibility(View.INVISIBLE);
        } else {
            previous.setVisibility(View.VISIBLE);
        }

        if (lastPage) {
            next.setVisibility(View.INVISIBLE);
        } else {
            next.setVisibility(View.VISIBLE);
        }

    }

    public static void setLastPage(boolean isLastPage) {
        lastPage = isLastPage;
    }


}
