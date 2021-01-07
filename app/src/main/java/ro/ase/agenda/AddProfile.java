package ro.ase.agenda;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AddProfile extends AppCompatActivity {
    
    private BottomNavigationView bottomNavigationView;
    private FirebaseDatabase database;
    public static ArrayList<Profile> list = new ArrayList<Profile>();
    private DatePickerDialog.OnDateSetListener mDateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);
        getSupportActionBar().hide();
        
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        final Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.add_category, R.layout.support_simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        //preluam campurile din form
        final EditText etFirstName = findViewById(R.id.etFirstName);
        final EditText etLastName = findViewById(R.id.etLastName);
        final EditText etPhone = findViewById(R.id.etPhone);
        final EditText etEmail = findViewById(R.id.etEmail);
        final TextView etBirthday = findViewById(R.id.etBirthday);

        etBirthday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddProfile.this,  mDateListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                dialog.show();
            }
        });

        mDateListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                etBirthday.setText(date);
            }
        };

        //salvam in baza de date
        database = FirebaseDatabase.getInstance();

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(etFirstName.getText().toString().isEmpty()){
                    etFirstName.setError("Insert first name!");
                } else if(etLastName.getText().toString().isEmpty()){
                    etLastName.setError("Insert last name!");
                } else if(etPhone.getText().toString().isEmpty() || (etPhone.getText().toString().length() != 10)){
                    etPhone.setError("Insert a valid phone number!");
                } else if(etBirthday.getText().toString().isEmpty()) {
                    etBirthday.setError("Insert birthday");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("agenda-fe205-profile");
                    myRef.keepSynced(true);

                    ValueEventListener listener = new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean ok = true;
                            if(snapshot.exists()){
                                for(DataSnapshot dn: snapshot.getChildren()) {
                                    if(dn.child("phone").getValue(String.class).equals(etPhone.getText().toString())) {
                                        etPhone.setError("Phone number already registered!");
                                        ok = false;
                                    }
                                 }
                            }
                            if(ok){
                                String firstName = etFirstName.getText().toString();
                                String lastName = etLastName.getText().toString();
                                String phone = etPhone.getText().toString();
                                String email = etEmail.getText().toString();
                                String birthday = etBirthday.getText().toString();

                                Category category = Category.valueOf(spinnerCategory.getSelectedItem().toString().replaceAll(" ", "").toUpperCase());

                                Profile profile = new Profile(firstName, lastName, phone, email, category, birthday);

                                //adaugare in baza de date Firebase
                                writeProfileInFirebase(profile);

                                //adaugare in baza de date room

                                ProfileDB profileDB =  ProfileDB.getInstanta(getApplicationContext());

                                profileDB.getProfileDao().insert(profile);




                                //deschidere homepage
                                Intent intentHomepage = new Intent(getApplicationContext(), Homepage.class);
                                startActivity(intentHomepage);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    };
                    myRef.child("agenda-fe205-profile").addValueEventListener(listener);





                }
            }
        });

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHomepage = new Intent(getApplicationContext(), Homepage.class);
                startActivity(intentHomepage);
            }
        });


    }

    private void writeProfileInFirebase(final Profile profile){
        final DatabaseReference myRef = database.getReference("agenda-fe205-profile");
        myRef.keepSynced(true);

        myRef.child("agenda-fe205-profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile.setUid(myRef.child("agenda-fe205-profile").push().getKey());
                myRef.child("agenda-fe205-profile").child(profile.getUid()).setValue(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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