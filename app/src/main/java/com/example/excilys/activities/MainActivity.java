package com.example.excilys.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import tasks.ConnectTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String SHARED_PREF_NAME = "credentials";
    public static final String USERNAME = "username";
    public static final String PWD = "password";

    private EditText usernameField;
    private EditText passwordField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuperation des zones de texte
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        //recuperation des identifiants
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME,0);
        String username = settings.getString(USERNAME, null);
        String password = settings.getString(PWD, null);

        //préremplissage du formulaire de connexion
        if (username != null) {
            usernameField.setText(username);
            if (password != null) passwordField.setText(password);
        }
    }

    /* Accesseurs */
    public String getUsername(){
        return usernameField.getText().toString();
    }

    public String getPassword(){
        return passwordField.getText().toString();
    }

    /* Listeners */

    /**
     * Vide les champs d'identifiants
     * @param view
     */
    public void listenerVider(View view) {
        usernameField.setText("");
        passwordField.setText("");
    }

    /**
     * Lance la tache asynchrone chargée de la connexion
     * @param view
     */
    public void listenerValider(View view){
        ConnectTask connectTask = new ConnectTask(this);
        connectTask.execute();
    }

    /**
     * Lance l'activité menu
     */
    public void launchMenuActivity(){
        Intent intent = new Intent(this, MenuActivity.class);

        //Enregistrement des identifiants
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USERNAME, getUsername());
        editor.putString(PWD, getPassword());
        editor.commit();

        //Lancement de l'activité
        startActivity(intent);
    }


}
