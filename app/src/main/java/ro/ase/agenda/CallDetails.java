package ro.ase.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CallDetails extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private List<AgendaDetails> messages = new ArrayList<>();
    public static List<Integer> checkedMessages = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_details);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 1);
        final ProfileDB profileDB = ProfileDB.getInstanta(getApplicationContext());

        messages = profileDB.getDetailsDao().getDetailsProfile(id);

        final ListView lv = findViewById(R.id.lvDetails);
        final DetailsAdapter adapter = new DetailsAdapter(getApplicationContext(), R.layout.elemente_lv, messages, getLayoutInflater()) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                AgendaDetails detail = messages.get(position);

                return view;//
            }
        };
        lv.setAdapter(adapter);

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                for(int id : checkedMessages){
                    profileDB.getDetailsDao().deleteOne(id);

                }
                finish();
                startActivity(getIntent());
            }
        });
        Button btnShow= findViewById(R.id.btnJSON);
        btnShow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ShowMessagesIdeas.class);
                startActivity(intent);
            }
        });

    }



//        ExtractProfileJSON extract = new ExtractProfileJSON(){
//            @Override
//            protected void onPostExecute(String s) {
//                runOnUiThread(() -> {
//                    profileList.addAll(ExtractProfileJSON.profileList);
//
////                                for(Profile profile: profileList)
////                                    profile.setId(100);
//
//                    profileDB.getProfileDao().insert(profileList);
//
//                    ProfileAdapter adapter = new ProfileAdapter(getApplicationContext(), R.layout.elemente_lv, profileList, getLayoutInflater()){
////                                @NonNull
////                                @Override
////                                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
////                                    View view = super.getView(position, convertView, parent);
////
////                                    Vehicle vehicle1 = vehicleList.get(position);
////
////                                    TextView tvPrice = view.findViewById(R.id.price);
////                                    if (vehicle1.getPrice() < 10000)
////                                        tvPrice.setTextColor(Color.GREEN);
////                                    else
////                                        tvPrice.setTextColor(Color.RED);
////
////                                    return view;//                               }
//                    };
//
//                    listView.setAdapter(adapter);
//                });
//            }
//        };
  //  }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Intent intent = null;

            switch(menuItem.getItemId()){
                case R.id.homepage:
                    intent = new Intent(getApplicationContext(), Homepage.class);

                    break;
                case R.id.search:
                    intent = new Intent(getApplicationContext(), Search.class);

                    break;
                case R.id.addProfile:
                    intent = new Intent(getApplicationContext(), AddProfile.class);

                    break;
                case R.id.profile:
                    intent = new Intent(getApplicationContext(), ShowPersonalProfile.class);

                    break;
            }
            startActivity(intent);
            return true;
        }
    };
}