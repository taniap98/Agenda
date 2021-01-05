package ro.ase.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private List<Profile> profileList = new ArrayList<Profile>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("agenda-fe205-profile");
        myRef.keepSynced(true);

        ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    profileList.clear();
                    for(DataSnapshot dn: snapshot.getChildren()) {
                        Profile profile = dn.getValue(Profile.class);
                        profileList.add(profile);
                    }
                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                for(Profile profile : profileList){

                    CardProfileFragment card = new CardProfileFragment(profile);
                    ft.add(R.id.fragment_placeholder, card);

                }

                ft.commitAllowingStateLoss();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        myRef.child("agenda-fe205-profile").addValueEventListener(listener);

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Intent intent = null;

            switch(menuItem.getItemId()){
                case R.id.homepage:


                    break;
                case R.id.search:
                    intent = new Intent(getApplicationContext(), Search.class);
                    startActivity(intent);
                    break;
                case R.id.addProfile:
                    intent = new Intent(getApplicationContext(), AddProfile.class);
                    startActivity(intent);
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