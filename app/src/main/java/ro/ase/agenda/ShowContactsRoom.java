package ro.ase.agenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowContactsRoom extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private List<Profile> profiles = new ArrayList<Profile>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_contacts_room);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        final ProfileDB profileDB =  ProfileDB.getInstanta(getApplicationContext());

        profiles = profileDB.getProfileDao().getAll();

        ListView lv = findViewById(R.id.lvContacts);
        List<String> names = new ArrayList<String>();
        for(Profile p : profiles){
            names.add(p.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                R.layout.lv_one_message, names);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(ShowContactsRoom.this)
                        .setTitle("Doriti sa stergeti sau sa modificati?")
                        .setMessage("Delete or Edit?")
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                TextView textView = (TextView) view.findViewById(R.id.oneMess);
                                String fullName = textView.getText().toString();

                                String[] name = fullName.split(" ");


                                //stergem din room
                                profileDB.getProfileDao().deleteOne(name[0], name[1]);

                                //stergem din firebase
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef =  database.getReference("agenda-fe205-profile").child("agenda-fe205-profile");




                                Query queryDB = myRef.orderByChild("lastName").equalTo(name[1]);

                                queryDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot data: dataSnapshot.getChildren()) {
                                            data.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                TextView textView = (TextView) view.findViewById(R.id.oneMess);
                                String fullName = textView.getText().toString();

                                String[] name = fullName.split(" ");

                                ProfileDB profileDB =  ProfileDB.getInstanta(getApplicationContext());

                                Profile p = profileDB.getProfileDao().findByProfileId(name[0], name[1]);

                                Intent intent = new Intent(getApplicationContext(), PopupEdit.class);

                                intent.putExtra("id", p.getId());
                                intent.putExtra("firstName", name[0]);
                                intent.putExtra("lastName", name[1]);
                                startActivity(intent);

                                dialogInterface.cancel();

                            }
                        })
                        .create();

                dialog.show();


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