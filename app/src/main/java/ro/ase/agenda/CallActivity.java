package ro.ase.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");

        TextView tv = findViewById(R.id.nrTel);
        tv.setText(phone);

        ImageButton btn = findViewById(R.id.hangUp);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}