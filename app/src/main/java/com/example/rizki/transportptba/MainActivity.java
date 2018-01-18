
package com.example.rizki.transportptba;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button  antar, jemput, layanan, ubah, cancel, signOut;

    private EditText oldEmail, newEmail, password, newPassword;
    private ProgressBar progressBar;
    private DatabaseReference dbUser;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    public static final int SIGN_OUT = 3;
    private final int RC_SETTING = 989;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        if (auth.getCurrentUser() == null) {
            // user auth state is changed - user is null
            // launch login activity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        dbUser = FirebaseDatabase.getInstance().getReference().child("user")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.main_user);

        dbUser.child("Nama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ChildValue = String.valueOf(dataSnapshot.getValue());
                textView.setText("Welcome " + ChildValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
       public void onClickAntar(View view){
        Intent intent = new Intent(this, AntarActivity.class);
        startActivity(intent);
            }

    public void onClickJemput(View view){
        Intent intent = new Intent(this, JemputActivity.class);
        startActivity(intent);}

    public void onClickLayanan(View view){
        Intent intent = new Intent(this, LayananActivity.class);
        startActivity(intent);}

    public void onClickUbah(View view){
        Intent intent = new Intent(this, UbahActivity.class);
        startActivity(intent);}

    public void onClickBatal(View view){
        Intent intent = new Intent(this,BatalActivity.class);
        startActivity(intent);}
    public void onClickLogout(View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Anda Yakin Ingin Keluar?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder1.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                auth.signOut();
                setResult(SIGN_OUT);
                Toast.makeText(MainActivity.this, "Berhasil keluar!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        AlertDialog alert1 = builder1.create();
        alert1.show();}
        }

