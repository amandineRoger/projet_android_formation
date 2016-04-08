package tasks;


import android.os.AsyncTask;
import com.example.excilys.activities.ChatActivity;
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
        return request.getMessagesList(ChatActivity.LIMIT, ((int) params[0] - 1) * ChatActivity.LIMIT);
    }
}
