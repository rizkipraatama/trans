package com.example.rizki.transportptba;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class UbahJemputActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private DatabaseReference dbJemput;
    private Button cari;
    private LinearLayout linearLayout,linearLayout_data,linearLayouttgl,linearLayoutwaktu,linearLayoutbtn;
    private TextView tanggallama,waktulama;
    private EditText tanggalp,kode;
    private Spinner waktup;

    int year,month,day,hour,minute,yearFinal,monthFinal,dayFinal,hourFinal,minuteFinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);

        dbJemput = FirebaseDatabase.getInstance().getReference().child("jemput")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        setContentView(R.layout.activity_ubah_jemput);
        cari = (Button) findViewById(R.id.btn_cari_jemput);
        linearLayout = (LinearLayout) findViewById(R.id.layout_ubah_jemput);
        linearLayouttgl = (LinearLayout) findViewById(R.id.layout_text_tanggal_jemput);
        linearLayoutwaktu = (LinearLayout) findViewById(R.id.layout_text_waktu_jemput);
        linearLayout_data = (LinearLayout) findViewById(R.id.layout_data_ubah_jemput);
        linearLayoutbtn = (LinearLayout) findViewById(R.id.layout_btn_ubah_jemput);
        tanggalp = (EditText) findViewById(R.id.tanggal_p_jemput);
        waktup = (Spinner) findViewById(R.id.waktu_p_jemput);
        kode = (EditText) findViewById(R.id.ubah_kode_jemput);
        tanggallama = (TextView) findViewById(R.id.tanggal_jemput_lama);
        waktulama = (TextView) findViewById(R.id.waktu_jemput_lama);


        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kodep = kode.getText().toString().trim();
                if(!TextUtils.isEmpty(kodep)) {
                dbJemput.child(kodep).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            builder1.setMessage("Database Berhasil Diupdate");
                            builder1.setCancelable(false);
                            builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                    startActivity(new Intent(UbahJemputActivity.this,MainActivity.class));
                                    dialogInterface.cancel();
                                }
                            });
                            AlertDialog alert1 =builder1.create();
                            alert1.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialogInterface) {
                                    alert1.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.bg_register);
                                }
                            });
                            builder2.setMessage("Anda Yakin?");
                            builder2.setCancelable(true);
                            builder2.setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            builder2.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String tanggal = tanggalp.getText().toString().trim();
                                    String waktu = waktup.getSelectedItem().toString();
                                    dbJemput.child(kodep).child("tanggal").setValue(tanggal);
                                    dbJemput.child(kodep).child("waktu").setValue(waktu);
                                    alert1.show();
                                }
                            });
                            AlertDialog alert2 = builder2.create();
                            alert2.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialogInterface) {
                                    alert2.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.bg_register);
                                    alert2.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.bg_register);
                                }
                            });
                            linearLayouttgl.removeAllViews();
                            TextView tanggal_text = new TextView(UbahJemputActivity.this);
                            tanggal_text.setText("Tanggal");
                            tanggal_text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayouttgl.addView(tanggal_text);
                            dbJemput.child(kodep).child("tanggal").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String childValue = String.valueOf(dataSnapshot.getValue());
                                    tanggallama.setText(childValue);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            linearLayoutwaktu.removeAllViews();
                            TextView waktu_text = new TextView(UbahJemputActivity.this);
                            waktu_text.setText("Waktu");
                            waktu_text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayoutwaktu.addView(waktu_text);
                            dbJemput.child(kodep).child("waktu").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String childValue = String.valueOf(dataSnapshot.getValue());
                                    waktulama.setText(childValue);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            linearLayout_data.removeAllViews();
                            TextView jumlah = new TextView(UbahJemputActivity.this);
                            jumlah.setText("Jumlah Penumpang");
                            jumlah.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout_data.addView(jumlah);
                            TextView text_jumlah = new TextView(UbahJemputActivity.this);
                            dbJemput.child(kodep).child("jmlpenumpang").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String ChildValue = String.valueOf(dataSnapshot.getValue());
                                    text_jumlah.setText(ChildValue);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            text_jumlah.setTextSize(20);
                            text_jumlah.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout_data.addView(text_jumlah);

                            TextView ke = new TextView(UbahJemputActivity.this);
                            ke.setText("Tujuan");
                            ke.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout_data.addView(ke);
                            TextView text_ke = new TextView(UbahJemputActivity.this);
                            dbJemput.child(kodep).child("ke").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String ChildValue = String.valueOf(dataSnapshot.getValue());
                                    text_ke.setText(ChildValue);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            text_ke.setTextSize(20);
                            text_ke.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout_data.addView(text_ke);

                            TextView ket = new TextView(UbahJemputActivity.this);
                            ket.setText("Keterangan");
                            ket.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout_data.addView(ket);
                            TextView text_ket = new TextView(UbahJemputActivity.this);
                            dbJemput.child(kodep).child("ket").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String ChildValue = String.valueOf(dataSnapshot.getValue());
                                    text_ket.setText(ChildValue);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            text_ket.setTextSize(20);
                            text_ket.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout_data.addView(text_ket);

                            linearLayout.removeAllViews();
                            Button pilih = new Button(UbahJemputActivity.this);
                            pilih.setText("Pilih Jadwal Baru");
                            pilih.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout.addView(pilih);

                            pilih.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Calendar c = Calendar.getInstance();
                                    year = c.get(Calendar.YEAR);
                                    month = c.get(Calendar.MONTH);
                                    day = c.get(Calendar.DAY_OF_MONTH);

                                    DatePickerDialog datePickerDialog = new DatePickerDialog(UbahJemputActivity.this,UbahJemputActivity.this, year, month, day);
                                    datePickerDialog.show();
                                }
                            });

                            linearLayoutbtn.removeAllViews();
                            Button ubah = new Button(UbahJemputActivity.this);
                            ubah.setText("Update Jadwal");
                            ubah.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayoutbtn.addView(ubah);

                            ubah.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        alert2.show();
                                    }
                                    catch (Exception e){
                                    }
                                }
                            });
                        }
                        else {
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
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2){
        yearFinal = i;
        monthFinal = i1+1;
        dayFinal = i2;

        tanggalp.setText(dayFinal+"/"+monthFinal+"/"+yearFinal);
    }
}
