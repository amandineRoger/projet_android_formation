package tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import util.MyHttpRequest;

/**
 * Created by excilys on 07/04/16.
 */
public class RegisterTask extends AsyncTask {
    String username;
    String password;
    Boolean response;

    public RegisterTask(String username, String password){
        this.username = username;
        this.password = password;
        this.response = false;

    }



    @Override
    protected Object doInBackground(Object[] params) {

        MyHttpRequest request = MyHttpRequest.getInstance();
        return request.register(getJSONToRegister(username, password));

    }



    private String getJSONToRegister(final String username, final String password){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("login", username);
            jsonObject.put("password", password);

        } catch (JSONException e) {
            Log.e("RegisterTask", "getJSONToRegister : "+e.getMessage());
        }

        return jsonObject.toString();
    }





}
