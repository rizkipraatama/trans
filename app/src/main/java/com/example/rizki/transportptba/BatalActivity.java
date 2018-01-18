package com.example.rizki.transportptba;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BatalActivity extends AppCompatActivity {
    private Button antar,jemput,layanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_batal);
        antar = (Button) findViewById(R.id.cancel_antar);
        jemput = (Button) findViewById(R.id.cancel_jemput);
        layanan = (Button) findViewById(R.id.cancel_layanan);

        antar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BatalActivity.this,BatalAntarActivity.class));
                finish();
            }
        });

        jemput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(BatalActivity.this,BatalJemputActivity.class));
                    finish();
            }
        });

        layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BatalActivity.this,BatalLayananActivity.class));
                finish();
            }
        });

    }
}
