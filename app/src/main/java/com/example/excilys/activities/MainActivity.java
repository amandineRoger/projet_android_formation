package com.example.excilys.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tasks.ConnectTask;
import tasks.RegisterTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String SHARED_PREF_NAME = "credentials";
    public static final String USERNAME = "username";
    public static final String PWD = "password";

    private EditText usernameField;
    private EditText passwordField;

    private static Boolean displayError = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuperation des zones de texte
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        //recuperation des identifiants
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, 0);
        String username = settings.getString(USERNAME, null);
        String password = settings.getString(PWD, null);

        //préremplissage du formulaire de connexion
        if (username != null) {
            usernameField.setText(username);
            if (password != null) passwordField.setText(password);
        }

        Log.d(TAG, "onCreate : display = " + displayError);


        //Gestion message d'erreur (champs vides)
        if (displayError) {
            TextView error = (TextView) findViewById(R.id.textView_error);
            error.setVisibility(View.VISIBLE);
        }

    }

    /* Accesseurs */
    public String getUsername() {
        return usernameField.getText().toString();
    }

    public String getPassword() {
        return passwordField.getText().toString();
    }

    /* Listeners */

    /**
     * Vide les champs d'identifiants
     *
     * @param view
     */
    public void listenerVider(View view) {
        usernameField.setText("");
        passwordField.setText("");
    }

    /**
     * Lance la tache asynchrone chargée de la connexion
     *
     * @param view
     */
    public void listenerValider(View view) {

        String username = getUsername();
        String password = getPassword();

        TextView error = (TextView) findViewById(R.id.textView_error);

        if ((username.length() > 0) && (password.length() > 0)) {
            error.setVisibility(View.INVISIBLE);
            displayError = false;
            ConnectTask connectTask = new ConnectTask(this);
            connectTask.execute();
            try {
                Boolean response = (Boolean) connectTask.get();
                if (!response) {
                    Toast.makeText(this, "Echec de la connexion ", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e(TAG, "listenerValider : " + e.getMessage());
            }
        } else {
            displayError = true;
            error.setVisibility(View.VISIBLE);
        }
    }


    public void listenerRegister(View view) {
        String username = getUsername();
        String password = getPassword();

        TextView error = (TextView) findViewById(R.id.textView_error);

        if ((username.length() > 0) && (password.length() > 0)) {
            error.setVisibility(View.INVISIBLE);
            displayError = false;
            RegisterTask registerTask = new RegisterTask(username, password);
            registerTask.execute();

            Boolean response = false;
            try {
                response = (Boolean) registerTask.get();
            } catch (Exception e) {
                Log.e(TAG, "listenerRegister : " + e.getMessage());
            }

            if (response) {
                listenerValider(null);
            } else {
                Toast.makeText(this, "Echec de l'inscription", Toast.LENGTH_SHORT).show();
            }

        } else {
            displayError = true;
            error.setVisibility(View.VISIBLE);
        }
    }



    /**
     * Lance l'activité "Liste des messages"
     */
    public void launchListeActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }


}
