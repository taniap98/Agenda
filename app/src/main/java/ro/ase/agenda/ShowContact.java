package ro.ase.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShowContact extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        getSupportActionBar().hide();


        //contact details
        Intent intent = getIntent();
        String name = intent.getStringExtra("fullName");
        TextView tv1 = findViewById(R.id.tvNameC);
        tv1.setText(name);
        final String phone = intent.getStringExtra("phone");
        TextView tv2 = findViewById(R.id.tvPhoneC);
        tv2.setText(phone);
        String email = intent.getStringExtra("email");
        TextView tv3 = findViewById(R.id.tvEmailValueC);
        tv3.setText(email);
        String birthday = intent.getStringExtra("birthday");
        TextView tv4 = findViewById(R.id.tvBirthdayValueC);
        tv4.setText(birthday);


        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);



        Button btnDetailsMessage = findViewById(R.id.btnDetailsMsg);
        btnDetailsMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDB profileDB =  ProfileDB.getInstanta(getApplicationContext());

                Profile p = profileDB.getProfileDao().findByProfileId(phone);

                Intent intent = new Intent(getApplicationContext(), CallDetails.class);
                intent.putExtra("id", p.getId());
                startActivity(intent);
            }
        });
        ImageButton btnMessage = findViewById(R.id.btnMessage);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileDB profileDB =  ProfileDB.getInstanta(getApplicationContext());

                Profile p = profileDB.getProfileDao().findByProfileId(phone);

                Intent intent = new Intent(getApplicationContext(), Popup.class);

                intent.putExtra("id", p.getId());
                startActivity(intent);
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