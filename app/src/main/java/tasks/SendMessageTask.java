package tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.excilys.activities.EnvoiActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import util.InputStreamToString;

/**
 * Created by excilys on 05/04/16.
 */
public class SendMessageTask extends AsyncTask {

    private static final String TAG = SendMessageTask.class.getSimpleName();

    String username;
    String password;
    EnvoiActivity activity;
    Boolean response;

    /**
     * Constructeur de la tache
     * @param username  nom d'utilisateur
     * @param password  mot de passe
     * @param activity EnvoiActivity
     */
    public SendMessageTask(String username, String password, EnvoiActivity activity) {
        this.username = username;
        this.password = password;
        this.activity = activity;
        response = false;
    }


    @Override
    protected Object doInBackground(Object[] params) {
        URL url ;
        HttpURLConnection urlConnection = null;

        //Récupération du message à envoyer
        String message = activity.getMessage();

        if (message.length() > 0) {
            try {
                //Encodage de l'URL
                message = URLEncoder.encode(message, "UTF-8");
                message = message.replace("+", "%20");

                //Envoi de la requête
                url = new URL("http://formation-android-esaip.herokuapp.com/message/"+username+"/"+password+"/"+message);
                urlConnection = (HttpURLConnection) url.openConnection();

                //Récupération de la réponse
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                response = true;

            } catch (MalformedURLException e) {
                Log.e(TAG, "malformed URL exception _ e = "+ e.getMessage() );
            } catch (IOException e) {
                Log.e(TAG, "IOException _ e = " + e.getMessage());
            } finally {
                urlConnection.disconnect();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(Object o) {
       if (response) {
           Toast.makeText(activity, "Message envoyé", Toast.LENGTH_SHORT).show();
       }
    }

}
