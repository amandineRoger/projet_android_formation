package tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.excilys.activities.MainActivity;
import com.example.excilys.activities.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

import util.InputStreamToString;
import util.MyHttpRequest;

/**
 * Created by excilys on 05/04/16.
 */
public class ConnectTask extends AsyncTask {
    private static final String TAG = ConnectTask.class.getSimpleName();

    Activity activity;
    Boolean response;

    /**
     * Constructeur de la tache
     *
     * @param activity : Main Activity contenant la progressBar
     */
    public ConnectTask(Activity activity) {
        this.activity = activity;
        response = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Affichage de la progressBar
        ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    protected Object doInBackground(Object[] params) {
        //Récupération des identifiants
        MainActivity mainActivity = (MainActivity) activity;
        final String user = mainActivity.getUsername();
        final String pwd = mainActivity.getPassword();

        MyHttpRequest request = MyHttpRequest.getInstance();
        response = request.connect(user, pwd);

        Log.d(TAG, "response = " + response);

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        //Masquage de la progressBar
        MainActivity mainActivity = (MainActivity) activity;
        ProgressBar progressBar = (ProgressBar) mainActivity.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        //Lancement de l'activité "Menu"
        if (response) {
            mainActivity.launchMenuActivity();
        } else {

        }
    }


}
