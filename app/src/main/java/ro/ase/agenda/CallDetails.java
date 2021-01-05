package ro.ase.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CallDetails extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

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

//        ProfileDB profileDB =  ProfileDB.getInstanta(getApplicationContext());


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
    }

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