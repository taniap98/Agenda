package ro.ase.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class Popup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        getSupportActionBar().hide();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.6));

        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 1);

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
                Log.d("id", String.valueOf(id));
                EditText etMessage = findViewById(R.id.etMessage);
                String message = etMessage.getText().toString();

                Date date = new Date(System.currentTimeMillis());

                AgendaDetails detail = new AgendaDetails(date, message, id);

                //adaugare in baza de date room

                ProfileDB profileDB =  ProfileDB.getInstanta(getApplicationContext());

                profileDB.getDetailsDao().insert(detail);

            }
        });


    }
}