package ro.ase.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private List<Profile> profileList = new ArrayList<Profile>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

//        SearchView sv = findViewById(R.id.sv);
//        sv.setOnQueryTextListener(new OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String qry)
//            {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText)
//            {
//
//                return true;
//            }
//        });
        SearchView sv = findViewById(R.id.sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("agenda-fe205-profile").child("agenda-fe205-profile");

                Query queryDB = myRef.orderByChild("firstName").equalTo(query);

                myRef.keepSynced(true);

                queryDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                      //  LinearLayout ln = findViewById(R.id.fragment_placeholder2);
                      //  ln.removeAllViewsInLayout();

                        List<Fragment> fragmentList =  getSupportFragmentManager().getFragments();
                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                        for(Fragment f : fragmentList){
                            ft2.remove(f);
                        }
                        ft2.commit();
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

                            ft.add(R.id.fragment_placeholder2, card);



                        }
                        ft.commitAllowingStateLoss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                return false;
            }
        });





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