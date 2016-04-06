package util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by excilys on 06/04/16.
 */
public class MessagesMapper {

    public static ArrayList<HashMap<String, String>> messagesListToArrayList (String messages) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        String[] elements;
        HashMap<String, String> tmp;

        JSONArray jsonArray;
        JSONObject jsonObject;

        try{
            jsonArray = new JSONArray(messages);
            int arrayLength = jsonArray.length();

            //boucle d'extraction des messages depuis la réponse JSON du serveur
            for (int i=0; i<arrayLength; i++){
                jsonObject = jsonArray.getJSONObject(i);
                tmp = new HashMap<>();
                tmp.put("nom", jsonObject.getString("login"));
                tmp.put("message", jsonObject.getString("message"));
                list.add(tmp);
            }

        } catch (JSONException e){
            Log.e("MessageMapper", e.getMessage());
        }

        return list;
    }

}
