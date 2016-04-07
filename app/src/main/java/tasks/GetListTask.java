package tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;

import com.example.excilys.activities.ListeActivity;
import com.example.excilys.activities.MenuActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import util.InputStreamToString;
import util.MyHttpRequest;

/**
 * Created by excilys on 05/04/16.
 */
public class GetListTask extends AsyncTask {

    private static final String TAG = GetListTask.class.getSimpleName();


    String username;
    String password;
    Boolean response;

    /**
     * Constructeur de la tache
     *
     * @param username nom d'utilisateur
     * @param password mot de passe
     */
    public GetListTask(String username, String password) {
        this.username = username;
        this.password = password;
        response = false;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        MyHttpRequest request = MyHttpRequest.getInstance();
        return request.getMessagesList(ListeActivity.LIMIT, ((int) params[0] - 1) * ListeActivity.LIMIT);
    }
}
