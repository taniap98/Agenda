package ro.ase.agenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShowPersonalProfile extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        SharedPreferences settingsFile=
                getSharedPreferences("PersonalProfilePreferences", 0);
        SharedPreferences.Editor myEditor = settingsFile.edit();
        myEditor.putString("name", "Popescu Ion");
        myEditor.putString("phone", "0754444555");
        myEditor.putString("email", "popescuion@gmail.com");
        myEditor.putString("date", "12/12/1997");
        myEditor.apply();

        SharedPreferences mySettings=
                getSharedPreferences("PersonalProfilePreferences", 0);

        String name = mySettings.getString("name", null);
        String phone = mySettings.getString("phone", null);
        String email = mySettings.getString("email", null);
        String date = mySettings.getString("date", null);

        TextView tv1 = findViewById(R.id.tvName);
        tv1.setText(name);
        TextView tv2 = findViewById(R.id.tvPhone);
        tv2.setText(phone);
        TextView tv3 = findViewById(R.id.tvEmailValue);
        tv3.setText(email);
        TextView tv4 = findViewById(R.id.tvBirthdayValue);
        tv4.setText(date);





        ImageButton btnMaps = findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
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


                    break;
            }

            return true;
        }
    };
}