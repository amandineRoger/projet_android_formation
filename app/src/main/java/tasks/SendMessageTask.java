package tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.excilys.activities.EnvoiActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

        try {
            url = new URL("http://formation-android-esaip.herokuapp.com/message/"+username+"/"+password+"/"+activity.getMessage());
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

        } catch (MalformedURLException e) {
            Log.e(TAG, "malformed URL exception _ e = "+ e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, "IOException _ e = " + e.getMessage()+ "\n "+e.getCause().toString());
        } finally {
            urlConnection.disconnect();
        }

        return null;

    }


    @Override
    protected void onPostExecute(Object o) {


    }

}
