package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Classe fournie dans le sujet du projet
 * Created by excilys on 05/04/16.
 */
public class InputStreamToString {

    /**
     * Convertit un inputStream en String
     *
     * @param is inputStream (réponse de requête)
     * @return la réponse convertie en String
     */
    public String convert(InputStream is) {
        String line = "";
        StringBuilder builder = new StringBuilder();

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while ((line = rd.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

}
