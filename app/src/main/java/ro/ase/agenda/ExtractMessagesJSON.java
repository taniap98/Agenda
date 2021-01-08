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

public class ExtractMessagesJSON extends AsyncTask<URL, Void, String> {
    public static List<HolidayMessages> holMessages = new ArrayList<>();



    JSONArray holidayMessages = null;
    JSONArray messageIdea = null;

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

                holidayMessages = jsonObject.getJSONArray("ideas");

                for (int i = 0; i < holidayMessages.length(); i++){
                    JSONObject message = holidayMessages.getJSONObject(i);
                    String holiday = message.getString("holiday");
                    String category = message.getString("category");



                    messageIdea = message.getJSONArray("messages");
                    List<String> messages = new ArrayList<>();
                    for(int j = 0; j < messageIdea.length(); j++){
                        JSONObject idea = messageIdea.getJSONObject(j);
                        String mess = idea.getString("message");

                        messages.add(mess);
                    }


                    HolidayMessages hol = new HolidayMessages(holiday, category, messages);

                    holMessages.add(hol);
                }

            } catch (JSONException e){
                e.printStackTrace();
            }

        } else {
            Log.e("loadJSONObject", "Couldn't get any data from the url");
        }
    }
}
