package com.example.excilys.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import tasks.GetListTask;
import tasks.SendMessageTask;
import util.MessagesMapper;

/**
 * Created by excilys on 08/04/16.
 */
public class ChatActivity extends AppCompatActivity {


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
        updateMessagesList();
    }

    /*************************** AppBar **************************************/
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
            case R.id.action_refresh:
                refreshMessagesList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*************************** Messages list ***************************/

    public static final int LIMIT = 8;
    private static boolean lastPage;

    private String username;
    private String password;
    private ListView listView;
    private int currentPage;


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
        ListAdapter adapter = new MyListAdapter(MessagesMapper.messagesListToArrayList(liste));
        listView.setAdapter(adapter);
    }

    public void refreshMessagesList() {
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


    /**************************** Message sending ****************************/

    private String message;

    public String getMessage() {
        return message;
    }

    /**
     * Récupère le contenu de la zone de texte editText_message et lance la tâche asynchrone chargée d'envoyer le message
     *
     * @param view
     */
    public void sendMessage(View view) {

        EditText editText = (EditText) findViewById(R.id.fragment_textEdit);
        message = editText.getText().toString();

        SharedPreferences settings = getSharedPreferences(MainActivity.SHARED_PREF_NAME, 0);
        String username = settings.getString(MainActivity.USERNAME, null);
        String password = settings.getString(MainActivity.PWD, null);

        SendMessageTask messageTask = new SendMessageTask(username, password, this);
        messageTask.execute();
        eraseCurrentMessage();
        refreshMessagesList();
    }

    public void eraseCurrentMessage(){
        EditText editText = (EditText) findViewById(R.id.fragment_textEdit);
        editText.setText("");
    }

    /*************************** List adapter ***************************/

    class MyListAdapter extends BaseAdapter {

        private ArrayList<HashMap<String, String>> messages;

        public MyListAdapter(ArrayList<HashMap<String, String>> list){
            this.messages = list;
            Log.d("MyListAdapter", "constructeur !");
        }


        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int ressource;

            String login = messages.get(position).get("nom");

            if (username.equals(login)){
                ressource = R.layout.list_element_owner;
                login = "Vous";
            } else {
                ressource = R.layout.list_element;
            }

            LayoutInflater inflater = getLayoutInflater();

            View row = inflater.inflate(ressource, parent, false);

            TextView loginTextView = (TextView) row.findViewById(R.id.pseudo);
            TextView messageTextView = (TextView) row.findViewById(R.id.textMessage);

            loginTextView.setText(login);
            messageTextView.setText(messages.get(position).get("message"));

            return (row);
        }
    }








}
