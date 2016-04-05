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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import util.InputStreamToString;

/**
 * Created by excilys on 05/04/16.
 */
public class ConnectTask extends AsyncTask{
    private static final String TAG = ConnectTask.class.getSimpleName();

    Activity activity;
    Boolean response;

    public ConnectTask(Activity activity){
        this.activity = activity;
        response = false;
    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG, "entré dans onPreExecute");
        super.onPreExecute();
        ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    protected Object doInBackground(Object[] params) {

        Log.i(TAG, "entré dans doInBackground");

        MainActivity mainActivity = (MainActivity) activity;
        String user = mainActivity.getUsername();
        String pwd = mainActivity.getPassword();

        URL url ;
        HttpURLConnection urlConnection = null;

        String urlText = "http://formation-android-esaip.herokuapp.com/connect/";

        try {
            url = new URL(urlText+user+"/"+pwd);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamToString inputStreamToString = new InputStreamToString();
            String inputString = inputStreamToString.convert(in);
            if (inputString.equals("true")) response = true;
            Log.d(TAG,"response = "+ inputString);
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
        super.onPostExecute(o);

        MainActivity mainActivity = (MainActivity) activity;

        ProgressBar progressBar = (ProgressBar) mainActivity.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Log.d(TAG, "launch Intent !");
        if (response) mainActivity.launchMenuActivity();
        Log.d(TAG, "Intent launched !");
    }






}
