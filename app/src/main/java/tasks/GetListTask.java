package tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;

import com.example.excilys.activities.MenuActivity;

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
public class GetListTask extends AsyncTask {

    private static final String TAG = GetListTask.class.getSimpleName();

    String username;
    String password;
    Activity activity;
    Boolean response;


    public GetListTask(String username, String password, Activity activity) {
        this.username = username;
        this.password = password;
        this.activity = activity;
        response = false;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        URL url ;
        HttpURLConnection urlConnection = null;
        String inputString = "";

        try {
            url = new URL("http://formation-android-esaip.herokuapp.com/messages/"+username+"/"+password);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamToString inputStreamToString = new InputStreamToString();
            inputString = inputStreamToString.convert(in);
            Log.d(TAG, "response = " + inputString);
            if (inputString.length() > 0) response=true;

        } catch (MalformedURLException e) {
            Log.e(TAG, "malformed URL exception _ e = "+ e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, "IOException _ e = " + e.getMessage()+ "\n "+e.getCause().toString());
        } finally {
            urlConnection.disconnect();
        }

        //return null;
        return inputString;

    }




}
