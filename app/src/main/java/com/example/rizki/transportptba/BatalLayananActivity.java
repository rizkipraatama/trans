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

import org.w3c.dom.Text;

public class BatalLayananActivity extends AppCompatActivity {
    private DatabaseReference dbLayanan;
    private EditText kode;
    private Button check;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        dbLayanan = FirebaseDatabase.getInstance().getReference().child("layanan")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        setContentView(R.layout.activity_batal_layanan);
        linearLayout = (LinearLayout) findViewById(R.id.layout_data_batal_layanan);
        kode = (EditText) findViewById(R.id.batal_kode_layanan);
        check = (Button) findViewById(R.id.btn_check_layanan);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kodep = kode.getText().toString().trim();
                if (!TextUtils.isEmpty(kodep)) {
                    dbLayanan.child(kodep).addValueEventListener(new ValueEventListener() {
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
                                        startActivity(new Intent(BatalLayananActivity.this, MainActivity.class));
                                    }
                                });
                                AlertDialog alert2 = builder2.create();
                                alert2.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialogInterface) {
                                        alert2.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.bg_register);
                                    }
                                });
                                builder1.setMessage("Apakah Anda Yakin? Seluruh Data yang Terhapus Tidak Bisa Dikembalikan Lagi");
                                builder1.setCancelable(true);
                                builder1.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dbLayanan.child(kodep).removeValue();
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
                                TextView jumlah = new TextView(BatalLayananActivity.this);
                                jumlah.setText("Jumlah Penumpang");
                                jumlah.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(jumlah);
                                TextView jumlah_text = new TextView(BatalLayananActivity.this);
                                dbLayanan.child(kodep).child("jmlpenumpang").addValueEventListener(new ValueEventListener() {
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

                                TextView satker = new TextView(BatalLayananActivity.this);
                                satker.setText("Satuan Kerja");
                                satker.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(satker);
                                TextView satker_text = new TextView(BatalLayananActivity.this);
                                dbLayanan.child(kodep).child("satker").addValueEventListener(new ValueEventListener() {
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

                                TextView dari = new TextView(BatalLayananActivity.this);
                                dari.setText("Tempat Penjemputan");
                                dari.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(dari);
                                TextView dari_text = new TextView(BatalLayananActivity.this);
                                dbLayanan.child(kodep).child("dari").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String childValue = String.valueOf(dataSnapshot.getValue());
                                        dari_text.setText(childValue);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                dari_text.setTextSize(20);
                                linearLayout.addView(dari_text);

                                TextView ke = new TextView(BatalLayananActivity.this);
                                ke.setText("Tujuan Berangkat");
                                ke.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(ke);
                                TextView ke_text = new TextView(BatalLayananActivity.this);
                                dbLayanan.child(kodep).child("ke").addValueEventListener(new ValueEventListener() {
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

                                TextView tanggal_antar = new TextView(BatalLayananActivity.this);
                                tanggal_antar.setText("Tanggal Mulai Layanan");
                                tanggal_antar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(tanggal_antar);
                                TextView tanggal_antar_text = new TextView(BatalLayananActivity.this);
                                dbLayanan.child(kodep).child("tanggal_mulai").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String childValue = String.valueOf(dataSnapshot.getValue());
                                        tanggal_antar_text.setText(childValue);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                tanggal_antar_text.setTextSize(20);
                                linearLayout.addView(tanggal_antar_text);

                                TextView tanggal_jemput = new TextView(BatalLayananActivity.this);
                                tanggal_jemput.setText("Tanggal Selesai Layanan");
                                tanggal_jemput.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(tanggal_jemput);
                                TextView tanggal_jemput_text = new TextView(BatalLayananActivity.this);
                                dbLayanan.child(kodep).child("tanggal_selesai").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String childValue = String.valueOf(dataSnapshot.getValue());
                                        tanggal_jemput_text.setText(childValue);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                tanggal_jemput_text.setTextSize(20);
                                linearLayout.addView(tanggal_jemput_text);

                                TextView waktu = new TextView(BatalLayananActivity.this);
                                waktu.setText("Waktu Pengantaran");
                                waktu.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearLayout.addView(waktu);
                                TextView waktu_text = new TextView(BatalLayananActivity.this);
                                dbLayanan.child(kodep).child("waktu").addValueEventListener(new ValueEventListener() {
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

                                Button hapus = new Button(BatalLayananActivity.this);
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
