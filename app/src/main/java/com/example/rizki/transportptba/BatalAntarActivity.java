package com.example.rizki.transportptba;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BatalAntarActivity extends AppCompatActivity {
    private DatabaseReference dbAntar;
    private EditText kode;
    private Button check;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        dbAntar = FirebaseDatabase.getInstance().getReference().child("antar")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        setContentView(R.layout.activity_batal_antar);
        linearLayout = (LinearLayout) findViewById(R.id.layout_data_batal_antar);
        kode = (EditText) findViewById(R.id.batal_kode_antar);
        check = (Button) findViewById(R.id.btn_check_antar);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kodep = kode.getText().toString().trim();
                if(!TextUtils.isEmpty(kodep)) {
                    dbAntar.child(kodep).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                linearLayout.removeAllViews();
                                builder2.setMessage("Database Berhasil Dihapus");
                                builder2.setCancelable(false);
                                builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                        startActivity(new Intent(BatalAntarActivity.this, MainActivity.class));
                                    }
                                });
                                AlertDialog alert2 = builder2.create();
                                alert2.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialogInterface) {
                                        alert2.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.bg_register);
                                        alert2.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.bg_register);
                                    }
                                });
                                builder1.setMessage("Apakah Anda Yakin? Seluruh Data yang Terhapus Tidak Bisa Dikembalikan Lagi");
                                builder1.setCancelable(true);
                                builder1.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dbAntar.child(kodep).removeValue();
                                        alert2.show();
                                    }
                                });
                                builder1.setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                AlertDialog alert1 = builder1.create();
                                alert1.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialogInterface) {
                                        alert1.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.bg_register);
                                        alert1.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.btn_logut_bg);
                                    }
                                });
                                TextView jumlah = new TextView(BatalAntarActivity.this);
                                jumlah.setText("Jumlah Penumpang");
                                jumlah.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(jumlah);
                                TextView jumlah_text = new TextView(BatalAntarActivity.this);
                                dbAntar.child(kodep).child("jmlpenumpang").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String childValue = String.valueOf(dataSnapshot.getValue());
                                        jumlah_text.setText(childValue);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                jumlah_text.setTextSize(20);
                                linearLayout.addView(jumlah_text);

                                TextView satker = new TextView(BatalAntarActivity.this);
                                satker.setText("Satuan Kerja");
                                satker.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(satker);
                                TextView satker_text = new TextView(BatalAntarActivity.this);
                                dbAntar.child(kodep).child("satker").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String childValue = String.valueOf(dataSnapshot.getValue());
                                        satker_text.setText(childValue);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                satker_text.setTextSize(20);
                                linearLayout.addView(satker_text);

                                TextView ke = new TextView(BatalAntarActivity.this);
                                ke.setText("Tujuan");
                                ke.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(ke);
                                TextView ke_text = new TextView(BatalAntarActivity.this);
                                dbAntar.child(kodep).child("ke").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String childValue = String.valueOf(dataSnapshot.getValue());
                                        ke_text.setText(childValue);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                ke_text.setTextSize(20);
                                linearLayout.addView(ke_text);

                                TextView tanggal = new TextView(BatalAntarActivity.this);
                                tanggal.setText("Tanggal Pengantaran");
                                tanggal.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(tanggal);
                                TextView tanggal_text = new TextView(BatalAntarActivity.this);
                                dbAntar.child(kodep).child("tanggal").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String childValue = String.valueOf(dataSnapshot.getValue());
                                        tanggal_text.setText(childValue);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                tanggal_text.setTextSize(20);
                                linearLayout.addView(tanggal_text);

                                TextView waktu = new TextView(BatalAntarActivity.this);
                                waktu.setText("Waktu Pengantaran");
                                waktu.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(waktu);
                                TextView waktu_text = new TextView(BatalAntarActivity.this);
                                dbAntar.child(kodep).child("waktu").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String childValue = String.valueOf(dataSnapshot.getValue());
                                        waktu_text.setText(childValue);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                waktu_text.setTextSize(20);
                                linearLayout.addView(waktu_text);

                                Button hapus = new Button(BatalAntarActivity.this);
                                hapus.setText("Hapus");
                                hapus.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(hapus);

                                hapus.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        alert1.show();
                                    }
                                });
                            } else {
                                kode.setError("Data Tidak Ditemukan");
                                kode.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    kode.setError("Silahkan Isi Kode Pemesanan");
                }
            }
        });
    }
}
