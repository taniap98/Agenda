package ro.ase.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PopupEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_edit);
        getSupportActionBar().hide();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.6));

        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 1);
        final String fN = intent.getStringExtra("firstName");
        final String lN = intent.getStringExtra("lastName");

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etFN = findViewById(R.id.etFirstName);
                final String firstName = etFN.getText().toString();
                EditText etLN = findViewById(R.id.etLastName);
                final String lastName = etLN.getText().toString();

                //update in room

                ProfileDB profileDB =  ProfileDB.getInstanta(getApplicationContext());
                profileDB.getProfileDao().updateName(id, firstName, lastName);

                //update in firebase
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef =  database.getReference("agenda-fe205-profile").child("agenda-fe205-profile");




                Query queryDB = myRef.orderByChild("lastName").equalTo(lN);

                queryDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()) {
                            data.getRef().child("firstName").setValue(firstName);
                            data.getRef().child("lastName").setValue(lastName);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                finish();
                Intent intent = new Intent(getApplicationContext(), ShowContactsRoom.class);
                startActivity(intent);

            }
        });
    }
}