package ro.ase.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowMessagesIdeas extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private List<HolidayMessages> holMessages = new ArrayList<HolidayMessages>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_messages_ideas);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        final ListView lv = findViewById(R.id.lvIdeas);

        ExtractMessagesJSON extractJSON = new ExtractMessagesJSON(){
            @Override
            protected void onPostExecute(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        holMessages.addAll(ExtractMessagesJSON.holMessages);

                        MessagesIdeasAdapter adapter = new MessagesIdeasAdapter(getApplicationContext(), R.layout.elem_lv_ideas, holMessages, getLayoutInflater()){
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);

                                HolidayMessages mss = holMessages.get(position);

                                return view;
                            }
                        };
                        lv.setAdapter(adapter);
                    }

                });

            };
        };
        try {
            extractJSON.execute(new URL("https://pastebin.com/raw/RZFzJ5ig"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }





    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Intent intent = null;

            switch(menuItem.getItemId()){
                case R.id.homepage:
                    intent = new Intent(getApplicationContext(), Homepage.class);
                    startActivity(intent);
                    break;
                case R.id.search:
                    intent = new Intent(getApplicationContext(), Search.class);
                    startActivity(intent);
                    break;
                case R.id.addProfile:


                    break;
                case R.id.profile:
                    intent = new Intent(getApplicationContext(), ShowPersonalProfile.class);
                    startActivity(intent);
                    break;
            }

            return true;
        }
    };
}