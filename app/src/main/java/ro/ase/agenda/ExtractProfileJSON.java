package ro.ase.agenda;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExtractProfileJSON extends AsyncTask<URL, Void, String> {
    public static List<Profile> profileList = new ArrayList<>();
    JSONArray profiles = null;

    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)urls[0].openConnection();
            conn.setRequestMethod("GET");
            InputStream ist = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br = new BufferedReader(isr);

            String sbuf = "";
            String linie = "";
            while ((linie = br.readLine()) != null){
                sbuf += linie + "\n";
            }

            loadJSONObject(sbuf);

            return sbuf;

        } catch (Exception ex) {
            Log.e("doInBackground", ex.getMessage());
        }
        finally {
            if (conn != null){
                conn.disconnect();
            }
        }
        return null;
    }

    public void loadJSONObject(String jsonStr){
        if (jsonStr != null){
            try {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jsonStr);
                }  catch (JSONException e) {
                    e.printStackTrace();
                }

                profiles = jsonObject.getJSONArray("profiles");

                for (int i = 0; i < profiles.length(); i++){
                    JSONObject prof = profiles.getJSONObject(i);
                    String firstName = prof.getString("firstName");
                    String lastName = prof.getString("lastName");
                    String phone = prof.getString("phone");
                    String email = prof.getString("email");
                    Category category = Category.valueOf(prof.getString("category"));
                    String date = prof.getString("date");

                    Profile profile = new Profile(firstName, lastName, phone, email, category, date);

                    profileList.add(profile);
                }

            } catch (JSONException e){
                e.printStackTrace();
            }

        } else {
            Log.e("loadJSONObject", "Couldn't get any data from the url");
        }
    }
}
