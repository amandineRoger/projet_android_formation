package com.example.excilys.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by excilys on 05/04/16.
 */
public class EnvoiActivity extends AppCompatActivity {

    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoyer);

        EditText editText = (EditText) findViewById(R.id.editText_message);
        message = editText.getText().toString();
    }


    public String getMessage(){
        return message;
    }

    public void sendMessage(View view){
        //TODO
    }
}
