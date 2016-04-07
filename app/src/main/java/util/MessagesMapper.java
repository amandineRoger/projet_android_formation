package util;

import android.util.Log;

import com.example.excilys.activities.ListeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by excilys on 06/04/16.
 */
public class MessagesMapper {

    public static ArrayList<HashMap<String, String>> messagesListToArrayList(String messages) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        String[] elements;
        HashMap<String, String> tmp;

        JSONArray jsonArray;
        JSONObject jsonObject;

        try {
            jsonArray = new JSONArray(messages);
            int arrayLength = jsonArray.length();
            if (arrayLength < ListeActivity.LIMIT) {
                ListeActivity.setLastPage(true);
            } else {
                ListeActivity.setLastPage(false);
            }

            //boucle d'extraction des messages depuis la réponse JSON du serveur
            for (int i = 0; i < arrayLength; i++) {
                jsonObject = jsonArray.getJSONObject(i);
                tmp = new HashMap<>();
                tmp.put("nom", jsonObject.getString("login"));
                tmp.put("message", jsonObject.getString("message"));
                list.add(0, tmp);
            }

        } catch (JSONException e) {
            Log.e("MessageMapper", e.getMessage());
        }

        return list;
    }

    public static String messageToJSON(String username, String message) {
        JSONObject jsonObject = new JSONObject();

        try {
            //UUID field
            UUID uuid = UUID.randomUUID();
            jsonObject.put("uuid", uuid);

            //login field
            jsonObject.put("login", username);

            //message field
            jsonObject.put("message", message);
        } catch (JSONException e) {
            Log.e("messageToJSON", e.getMessage());
        }

        Log.d("messageToJSON", "object = " + jsonObject.toString());

        return jsonObject.toString();

    }

}
