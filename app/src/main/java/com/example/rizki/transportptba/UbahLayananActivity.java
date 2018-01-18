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
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class UbahLayananActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private DatabaseReference dbLayanan;
    private Button cari;
    private LinearLayout linearLayout,linearLayouttgl,linearLayouttgl2,linearLayoutwaktu,linearLayout_data,linearLayoutbtn;
    private EditText tanggalp,tanggalp2,waktup,kode;
    private TextView tanggalmulailama,tanggalselesailama,waktulama;

    int year,month,day,hour,minute,yearFinal,monthFinal,dayFinal,hourFinal,minuteFinal;
    int year2,month2,day2,yearFinal2,monthFinal2,dayFinal2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_layanan);

        dbLayanan = FirebaseDatabase.getInstance().getReference().child("layanan")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        setContentView(R.layout.activity_ubah_layanan);
        cari = (Button) findViewById(R.id.btn_cari_layanan);
        linearLayout = (LinearLayout) findViewById(R.id.layout_ubah_layanan);
        linearLayouttgl = (LinearLayout) findViewById(R.id.layout_text_tanggal_layanan);
        linearLayouttgl2 = (LinearLayout) findViewById(R.id.layout_text_tanggal_layanan2);
        linearLayoutwaktu = (LinearLayout) findViewById(R.id.layout_text_waktu_layanan);
        linearLayout_data = (LinearLayout) findViewById(R.id.layout_data_ubah_layanan);
        linearLayoutbtn = (LinearLayout) findViewById(R.id.layout_btn_ubah_layananan);
        kode = (EditText) findViewById(R.id.ubah_kode_layanan);
        tanggalp = (EditText) findViewById(R.id.tanggal_p_layanan);
        tanggalp2 = (EditText) findViewById(R.id.tanggal_p_layanan2);
        waktup = (EditText) findViewById(R.id.waktu_p_layanan);
        tanggalmulailama = (TextView) findViewById(R.id.tanggal_mulai_layanan_lama);
        tanggalselesailama = (TextView) findViewById(R.id.tanggal_selesai_layanan_lama);
        waktulama = (TextView) findViewById(R.id.waktu_layanan_lama);

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kodep = kode.getText().toString().trim();
                if(!TextUtils.isEmpty(kodep)) {
                dbLayanan.child(kodep).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            builder1.setMessage("Database Berhasil Diupdate");
                            builder1.setCancelable(false);
                            builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                    startActivity(new Intent(UbahLayananActivity.this,MainActivity.class));
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
                                    String tanggal2 = tanggalp2.getText().toString().trim();
                                    String waktu = waktup.getText().toString().trim();
                                    dbLayanan.child(kodep).child("tanggal_mulai").setValue(tanggal);
                                    dbLayanan.child(kodep).child("tanggal_selesai").setValue(tanggal2);
                                    dbLayanan.child(kodep).child("waktu").setValue(waktu);
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
                            TextView tanggal_text = new TextView(UbahLayananActivity.this);
                            tanggal_text.setText("Tanggal Mulai");
                            tanggal_text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayouttgl.addView(tanggal_text);
                            dbLayanan.child(kodep).child("tanggal_mulai").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String childValue = String.valueOf(dataSnapshot.getValue());
                                    tanggalmulailama.setText(childValue);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            linearLayouttgl2.removeAllViews();
                            TextView tanggal_text2 = new TextView(UbahLayananActivity.this);
                            tanggal_text2.setText("Tanggal Selesai");
                            tanggal_text2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayouttgl2.addView(tanggal_text2);
                            dbLayanan.child(kodep).child("tanggal_selesai").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String childValue = String.valueOf(dataSnapshot.getValue());
                                    tanggalselesailama.setText(childValue);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            linearLayoutwaktu.removeAllViews();
                            TextView waktu_text= new TextView(UbahLayananActivity.this);
                            waktu_text.setText("Waktu");
                            waktu_text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayoutwaktu.addView(waktu_text);
                            dbLayanan.child(kodep).child("waktu").addValueEventListener(new ValueEventListener() {
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
                            TextView jumlah = new TextView(UbahLayananActivity.this);
                            jumlah.setText("Jumlah Penumpang");
                            jumlah.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout_data.addView(jumlah);
                            TextView text_jumlah = new TextView(UbahLayananActivity.this);
                            dbLayanan.child(kodep).child("jmlpenumpang").addValueEventListener(new ValueEventListener() {
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

                            TextView dari = new TextView(UbahLayananActivity.this);
                            dari.setText("Tujuan Jemput");
                            dari.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout_data.addView(dari);
                            TextView text_dari = new TextView(UbahLayananActivity.this);
                            dbLayanan.child(kodep).child("dari").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String ChildValue = String.valueOf(dataSnapshot.getValue());
                                    text_dari.setText(ChildValue);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            text_dari.setTextSize(20);
                            text_dari.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout_data.addView(text_dari);

                            TextView ke = new TextView(UbahLayananActivity.this);
                            ke.setText("Tujuan Pergi");
                            ke.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout_data.addView(ke);
                            TextView text_ke = new TextView(UbahLayananActivity.this);
                            dbLayanan.child(kodep).child("ke").addValueEventListener(new ValueEventListener() {
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

                            TextView ket = new TextView(UbahLayananActivity.this);
                            ket.setText("Keterangan");
                            ket.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout_data.addView(ket);
                            TextView text_ket = new TextView(UbahLayananActivity.this);
                            dbLayanan.child(kodep).child("ket").addValueEventListener(new ValueEventListener() {
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
                            Button pilih = new Button(UbahLayananActivity.this);
                            pilih.setText("Pilih Jadwal Tanggal Mulai & Waktu Berangkat Baru");
                            pilih.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout.addView(pilih);

                            pilih.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Calendar c = Calendar.getInstance();
                                    year = c.get(Calendar.YEAR);
                                    month = c.get(Calendar.MONTH);
                                    day = c.get(Calendar.DAY_OF_MONTH);

                                    DatePickerDialog datePickerDialog = new DatePickerDialog(UbahLayananActivity.this,UbahLayananActivity.this, year, month, day);
                                    datePickerDialog.show();
                                }
                            });

                            Button pilih2 = new Button(UbahLayananActivity.this);
                            pilih2.setText("Pilih Jadwal Tanggal Selesai");
                            pilih2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout.addView(pilih2);

                            pilih2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Calendar d = Calendar.getInstance();
                                    year2 = d.get(Calendar.YEAR);
                                    month2 = d.get(Calendar.MONTH);
                                    day2 = d.get(Calendar.DAY_OF_MONTH);

                                    DatePickerDialog datePickerDialog2 = new DatePickerDialog(UbahLayananActivity.this,new innerFirstDate2(), year2, month2, day2);
                                    datePickerDialog2.show();
                                }
                            });

                            linearLayoutbtn.removeAllViews();
                            Button ubah = new Button(UbahLayananActivity.this);
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

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(UbahLayananActivity.this,UbahLayananActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1){
        hourFinal = i;
        minuteFinal = i1;

        tanggalp.setText(dayFinal+"/"+monthFinal+"/"+yearFinal);
        waktup.setText(hourFinal+":"+minuteFinal);
    }

    class innerFirstDate2 implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            yearFinal2 = i;
            monthFinal2 = i1 + 1;
            dayFinal2 = i2;

            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
            tanggalp2.setText(dayFinal2 + "/" + monthFinal2 + "/" + yearFinal2);
        }
    }
}
