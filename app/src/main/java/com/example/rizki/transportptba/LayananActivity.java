package com.example.rizki.transportptba;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LayananActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private DatabaseReference dbTransport_Layanan;
    private DatabaseReference dbUser_Layanan;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private Button btnlanjut,btntanggal_mulai,btntanggal_selesai;
    private EditText nohpp, tanggal_mulaip, tanggal_selesaip, waktup, jmlp, ketp;
    private TextView emailp, namap, nomorp;
    private Spinner kep , darip,satkerp, keperluanp;
    private LinearLayout linearLayout;

    int day,month,year,hour,minute,dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal,monthFinal2,dayFinal2,yearFinal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView textView = new TextView(this);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        dbTransport_Layanan = FirebaseDatabase.getInstance().getReference().child("layanan")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        setContentView(R.layout.activity_layanan);
        linearLayout = (LinearLayout) findViewById(R.id.layout_bio_layanan) ;
        namap = (TextView) findViewById(R.id.nama_layanan);
        nomorp = (TextView) findViewById(R.id.nomor_layanan);
        emailp = (TextView) findViewById(R.id.email_layanan);
        nohpp = (EditText) findViewById(R.id.telpon_layanan);
        satkerp = (Spinner) findViewById(R.id.satker_layanan);
        tanggal_mulaip = (EditText) findViewById(R.id.mulai_layanan);
        tanggal_selesaip = (EditText) findViewById(R.id.selesai_layanan);
        waktup = (EditText) findViewById(R.id.waktu_layanan);
        jmlp = (EditText) findViewById(R.id.jumlah_layanan);
        keperluanp = (Spinner) findViewById(R.id.keperluan_layanan);
        ketp = (EditText) findViewById(R.id.ket_layanan);
        darip = (Spinner) findViewById(R.id.daerah_spinner_dari);
        kep = (Spinner) findViewById(R.id.daerah_spinner_ke);
        btnlanjut = (Button) findViewById(R.id.btn_lanjut_layanan);
        btntanggal_mulai = (Button) findViewById(R.id.btn_tanggal_mulai_layan);
        btntanggal_selesai = (Button) findViewById(R.id.btn_tanggal_selesai_layan);

        dbUser_Layanan = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dbUser_Layanan.child("Nama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String childValue = String.valueOf(dataSnapshot.getValue());
                namap.setText(childValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbUser_Layanan.child("Nomor Pegawai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String childValue = String.valueOf(dataSnapshot.getValue());
                nomorp.setText(childValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
         dbUser_Layanan.child("Email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String childValue = String.valueOf(dataSnapshot.getValue());
                emailp.setText(childValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btntanggal_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(LayananActivity.this,LayananActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        btntanggal_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar d = Calendar.getInstance();

                 year = d.get(Calendar.YEAR);
                 month= d.get(Calendar.MONTH);
                day = d.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(LayananActivity.this,new innerFirstDate(), year, month, day);
                datePickerDialog2.show();

            }

        });

        btnlanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = dbTransport_Layanan.push().getKey();
                String jumlah = jmlp.getText().toString().trim();
                textView.setText("Database Berhasil Disimpan!\nKode Pemesanan \n\n\t\t '"+id+("'\n\n(Silahkan salin kode tanpa tanda(') atau tekan tombol dibawah untuk menyalin kode tsb atau screenshot kode diatas untuk melakukan perubahan atau pembatalan pemesanan)"));
                textView.setTextIsSelectable(true);
                builder1.setView(textView);
                builder1.setCancelable(false);
                builder1.setPositiveButton("Salin Kode Pemesanan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ClipboardManager Cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText(" ",id);
                        Cm.setPrimaryClip(clip);

                        Toast.makeText(LayananActivity.this,"Kode Pemesanan sudah tersalin!",Toast.LENGTH_LONG).show();
                        finish();

                        startActivity(new Intent(LayananActivity.this,MainActivity.class));

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
                if (!TextUtils.isEmpty(jumlah)){
                if(jmlp.getText().length()>0){
                    try {
                        linearLayout.removeAllViews();
                    } catch (Throwable e){
                        e.printStackTrace();
                    }

                    int length = Integer.parseInt(jmlp.getText().toString().trim());
                    List<EditText> listnama = new ArrayList<EditText>();
                    List<EditText> listnomor = new ArrayList<EditText>();
                    List<EditText> listalamat = new ArrayList<EditText>();

                    for (int i=0;i<length;i++){
                        TextView text_nama = new TextView(LayananActivity.this);
                        text_nama.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                        text_nama.setText("Nama Penumpang-"+(i+1));
                        linearLayout.addView(text_nama);

                        EditText edit_nama = new EditText(LayananActivity.this);
                        listnama.add(edit_nama);
                        edit_nama.setId(i+1);
                        edit_nama.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayout.addView(edit_nama);

                        TextView text_nomor = new TextView(LayananActivity.this);
                        text_nomor.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                        text_nomor.setText("Nomor Telepon Penumpang-"+(i+1));
                        linearLayout.addView(text_nomor);

                        EditText edit_nomor = new EditText(LayananActivity.this);
                        listnomor.add(edit_nomor);
                        edit_nomor.setId(i+100);
                        edit_nomor.setInputType(InputType.TYPE_CLASS_PHONE);
                        edit_nomor.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayout.addView(edit_nomor);

                        TextView text_alamat = new TextView(LayananActivity.this);
                        text_alamat.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                        text_alamat.setText("Alamat Penumpang-"+(i+1));
                        linearLayout.addView(text_alamat);

                        EditText edit_alamat = new EditText(LayananActivity.this);
                        listalamat.add(edit_alamat);
                        edit_alamat.setId(i+1000);
                        edit_alamat.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayout.addView(edit_alamat);
                    }

                    Button simpan = new Button(LayananActivity.this);
                    simpan.setText("Simpan");
                    simpan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                    linearLayout.addView(simpan);

                    simpan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String email = emailp.getText().toString().trim();
                            String nohp = nohpp.getText().toString().trim();
                            String satker = satkerp.getSelectedItem().toString();
                            String tanggal_mulai = tanggal_mulaip.getText().toString().trim();
                            String tanggal_selesai = tanggal_selesaip.getText().toString().trim();
                            String waktu = waktup.getText().toString().trim();
                            String jml = jmlp.getText().toString().trim();
                            String keperluan = keperluanp.getSelectedItem().toString();
                            String ket = ketp.getText().toString().trim();
                            String dari = darip.getSelectedItem().toString();
                            String ke = kep.getSelectedItem().toString();
                            Boolean ambil = false;
                            if (!TextUtils.isEmpty(email)) {
                                if (!TextUtils.isEmpty(nohp)){
                                    if (!TextUtils.isEmpty(satker)){
                                        if (!TextUtils.isEmpty(tanggal_mulai)){
                                            if (!TextUtils.isEmpty(tanggal_selesai)) {
                                                if (!TextUtils.isEmpty(waktu)) {
                                                    if (!TextUtils.isEmpty(jml)) {
                                                        if (!TextUtils.isEmpty(keperluan)) {
                                                            if (!TextUtils.isEmpty(ket)) {
                                                                if (!TextUtils.isEmpty(dari)) {
                                                                    if (!TextUtils.isEmpty(ke)) {
                                                                        addLayanan(id);
                                                                        String[] nama = new String[listnama.size()];
                                                                        String[] nomor = new String[listnomor.size()];
                                                                        String[] alamat = new String[listalamat.size()];
                                                                        for (int i=0; i< listnama.size();i++){
                                                                            nama[i]=listnama.get(i).getText().toString();
                                                                            dbTransport_Layanan.child(id).child("Nama Penumpang-"+(i+1)).setValue(nama[i]);
                                                                        }

                                                                        for (int i=0; i< listnomor.size();i++){
                                                                            nomor[i]=listnomor.get(i).getText().toString();
                                                                            dbTransport_Layanan.child(id).child("Nomor Telepon Penumpang-"+(i+1)).setValue(nomor[i]);
                                                                        }

                                                                        for (int i=0; i< listalamat.size();i++){
                                                                            alamat[i]=listalamat.get(i).getText().toString();
                                                                            dbTransport_Layanan.child(id).child("Alamat Penumpang-"+(i+1)).setValue(alamat[i]);
                                                                        }
                                                                        alert1.show();
                                                                    } else {Toast.makeText(LayananActivity.this, "Silahkan isi tujuan", Toast.LENGTH_LONG).show();}

                                                                } else {Toast.makeText(LayananActivity.this, "Silahkan isi tujuan jemput", Toast.LENGTH_LONG).show();}

                                                            } else {Toast.makeText(LayananActivity.this, "Silahkan isi keterangan", Toast.LENGTH_LONG).show();}

                                                        } else {Toast.makeText(LayananActivity.this, "Silahkan isi keperluan", Toast.LENGTH_LONG).show();}

                                                    } else {Toast.makeText(LayananActivity.this, "Silahkan isi jumlah penumpang", Toast.LENGTH_LONG).show();}

                                                } else {Toast.makeText(LayananActivity.this, "Silahkan isi waktu", Toast.LENGTH_LONG).show();}

                                            } else{Toast.makeText(LayananActivity.this, "Silahkan isi tanggal selesai", Toast.LENGTH_LONG).show();}

                                        } else{Toast.makeText(LayananActivity.this, "Silahkan isi tanggal mulai", Toast.LENGTH_LONG).show();}

                                    } else{Toast.makeText(LayananActivity.this, "Silahkan isi satuan kerja", Toast.LENGTH_LONG).show();}

                                } else{Toast.makeText(LayananActivity.this, "Silahkan isi no hp", Toast.LENGTH_LONG).show();}

                            } else {Toast.makeText(LayananActivity.this, "Please enter a name", Toast.LENGTH_LONG).show();}
                        }
                    });
                }
                }
                else {
                    Toast.makeText(LayananActivity.this,"Silahkan Isi Jumlah Penumpang",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    class innerFirstDate implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            yearFinal2 = i;
            monthFinal2 = i1 + 1;
            dayFinal2 = i2;

            tanggal_selesaip.setText(dayFinal2 + "/" + monthFinal2 + "/" + yearFinal2);

        }
    }
    @Override
    public void onDateSet (DatePicker datePicker,int i, int i1, int i2){
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(LayananActivity.this,LayananActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1){
        hourFinal = i;
        minuteFinal = i1;

        tanggal_mulaip.setText(dayFinal+"/"+monthFinal+"/"+yearFinal);
        waktup.setText(hourFinal+":"+minuteFinal);
    }


    private void addLayanan(String id){
        String email = emailp.getText().toString().trim();
        String nohp = nohpp.getText().toString().trim();
        String satker = satkerp.getSelectedItem().toString();
        String tanggal_mulai = tanggal_mulaip.getText().toString().trim();
        String tanggal_selesai = tanggal_selesaip.getText().toString().trim();
        String waktu = waktup.getText().toString().trim();
        String jml = jmlp.getText().toString().trim();
        String keperluan = keperluanp.getSelectedItem().toString();
        String ket = ketp.getText().toString().trim();
        String dari = darip.getSelectedItem().toString();
        String ke = kep.getSelectedItem().toString();
        Boolean ambil = false;

        layanan layanp = new layanan(email, nohp, satker, tanggal_mulai, tanggal_selesai, waktu, jml, keperluan, ket, dari, ke, ambil);
        dbTransport_Layanan.child(id).setValue(layanp);
        Toast.makeText(this, "Tersimpan", Toast.LENGTH_LONG).show();
    }



}
