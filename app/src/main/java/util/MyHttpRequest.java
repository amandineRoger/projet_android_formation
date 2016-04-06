package util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

/**
 * Created by excilys on 06/04/16.
 */
public class MyHttpRequest {
    private static final String URL = "https://training.loicortola.com/chat-rest/2.0/";
    private static final String TAG = MyHttpRequest.class.getSimpleName();

    //Singleton
    private static MyHttpRequest instance;
    private MyHttpRequest() {}


    public static MyHttpRequest getInstance(){
        if (instance == null) {
            instance = new MyHttpRequest();
        }
        return instance;
    }

    public Boolean connect(final String username, final String password) {

        Boolean success = false;
        URL url;
        HttpURLConnection urlConnection = null;

        //Define authenticator
        Authenticator.setDefault(new BasicAuthenticator(username, password));

        try {
            //Sending connect request
            url = new URL(URL + "/connect");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            //convert response to string
            InputStreamToString inputStreamToString = new InputStreamToString();
            String inputString = inputStreamToString.convert(in);

            //convert string to JSON
            JSONObject reader = null;
            String status = null;

            try {
                reader = new JSONObject(inputString);
                //get status code from JSON
                status = reader.getString("status");
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }

            //Validation
            String code_ok = "200";
            if (code_ok.equals(status)) {
                success = true;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            urlConnection.disconnect();
        }

        return success;

    }




    static class BasicAuthenticator extends Authenticator {
        String baName;
        String baPassword;
        private BasicAuthenticator(String baName1, String baPassword1) {
            baName = baName1;
            baPassword = baPassword1;
        }
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            System.out.println("Authenticating...");
            return new PasswordAuthentication(baName, baPassword.toCharArray());
        }
    };





}
