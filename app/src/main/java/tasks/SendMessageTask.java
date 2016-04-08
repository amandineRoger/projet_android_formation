package tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.excilys.activities.ChatActivity;

import util.MessagesMapper;
import util.MyHttpRequest;

/**
 * Created by excilys on 05/04/16.
 */
public class SendMessageTask extends AsyncTask {

    private static final String TAG = SendMessageTask.class.getSimpleName();

    String username;
    String password;
    ChatActivity activity;
    Boolean response;

    /**
     * Constructeur de la tache
     *
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @param activity ChatActivity
     */
    public SendMessageTask(String username, String password, ChatActivity activity) {
        this.username = username;
        this.password = password;
        this.activity = activity;
        response = false;
    }


    @Override
    protected Object doInBackground(Object[] params) {

        //Récupération du message à envoyer
        String message = activity.getMessage();

        if (message.length() > 0) {
            MyHttpRequest httpRequest = MyHttpRequest.getInstance();
            httpRequest.sendMessage(MessagesMapper.messageToJSON(username, message));
        }
        return null;
    }


    @Override
    protected void onPostExecute(Object o) {
        if (response) {
            Toast.makeText(activity, "Message envoyé", Toast.LENGTH_SHORT).show();

        }
    }

}
