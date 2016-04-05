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

/**
 * Created by excilys on 05/04/16.
 */
public class ListeActivity extends AppCompatActivity{


    String username;
    String password;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);


        SharedPreferences settings = getSharedPreferences(MainActivity.SHARED_PREF_NAME, 0);
        username = settings.getString(MainActivity.USERNAME, null);
        password = settings.getString(MainActivity.PWD, null);

        listView = (ListView) findViewById(R.id.listView_message);
        listenerListeMessage(null);
    }

    public void listenerListeMessage(View view) {
        GetListTask listTask = new GetListTask(username, password, this);
        listTask.execute();
        String liste = "";
        try {
            liste = (String) listTask.get();

        } catch (Exception e) {
            Log.e("listenerListeMessage", e.getMessage());
        }
        Log.d("listenerListeMessage", "response = " + liste);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();


        String[] elements;
        HashMap<String, String> tmp;

        for (String str : liste.split(";")){
            elements = str.split(":");
            if (elements.length == 2) {
                tmp = new HashMap<>();
                tmp.put("nom", elements[0]);
                tmp.put("message", elements[1]);
                list.add(tmp);
            }
        }

        ListAdapter adapter = new SimpleAdapter(this, list, R.layout.list_element, new String[]{"nom", "message"}, new int[] {R.id.pseudo, R.id.textMessage});
        listView.setAdapter(adapter);
    }

}
