package util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLEncoder;

import tasks.ConnectTask;

/**
 * Created by excilys on 06/04/16.
 */
public class MyHttpRequest {
    private static final String URL_CHAT = "https://training.loicortola.com/chat-rest/2.0";
    private static final String TAG = MyHttpRequest.class.getSimpleName();

    //Singleton
    private static MyHttpRequest instance;
    private String username;
    private String password;

    private MyHttpRequest() {
    }


    public static MyHttpRequest getInstance() {
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
            url = new URL(URL_CHAT + "/connect");
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
                instance.username = username;
                instance.password = password;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            urlConnection.disconnect();
        }

        return success;

    }

    /**
     * Send the messagesList request
     *
     * @param limit  maximum number of results
     * @param offset result offset
     * @return JSON Array response in String
     */
    public String getMessagesList(int limit, int offset) {
        URL url;
        HttpURLConnection urlConnection = null;
        String inputString = "";

        try {
            //Envoi de la requête pour récupérer la liste des messages
            url = new URL(URL_CHAT + "/messages?limit=" + limit + "&offset=" + offset);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            //Conversion de la réponse en String
            InputStreamToString inputStreamToString = new InputStreamToString();
            inputString = inputStreamToString.convert(in);
            Log.d("getMessagesList", "response = " + inputString);

        } catch (MalformedURLException e) {
            Log.e(TAG, "malformed URL exception _ e = " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException _ e = " + e.getMessage());
        } finally {
            urlConnection.disconnect();
        }

        return inputString;
    }


    public Boolean sendMessage(String jsonMessage) {
        Boolean success = false;

        URL url;
        HttpURLConnection urlConnection = null;

        DataOutputStream outputStream;
        DataInputStream inputStream;

        String inputString = "";

        try {
            url = new URL(URL_CHAT + "/messages");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            //Request building
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
            writer.write(jsonMessage);
            writer.flush();
            writer.close();

            //response
            InputStream in = new BufferedInputStream(urlConnection.getErrorStream());

            //Conversion de la réponse en String
            InputStreamToString inputStreamToString = new InputStreamToString();
            inputString = inputStreamToString.convert(in);

            Log.d(TAG, "sendMessage : response = " + inputString);

        } catch (MalformedURLException e) {
            Log.e(TAG, "sendMessage :" + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "sendMessage :" + e.getMessage());
        }

        return success;
    }


    public Boolean register(String jsonRequestBody) {
        Boolean success = false;

        URL url;
        HttpURLConnection urlConnection = null;

        DataOutputStream outputStream;
        DataInputStream inputStream;

        String inputString = "";

        try {
            url = new URL(URL_CHAT + "/register");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            //Request building
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
            writer.write(jsonRequestBody);
            writer.flush();
            writer.close();

            //response
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            //Conversion de la réponse en String
            InputStreamToString inputStreamToString = new InputStreamToString();
            inputString = inputStreamToString.convert(in);
            Log.d("register", "response = " + inputString);

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


        } catch (MalformedURLException e) {
            Log.e(TAG, "register :" + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "register :" + e.getMessage());
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
    }

    ;


}
