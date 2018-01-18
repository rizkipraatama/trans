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
import android.text.InputType;
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
import android.text.format.DateFormat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;


public class JemputActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private DatabaseReference dbTransport_Jemput;
    private DatabaseReference dbUser_Jemput;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private Button btnlanjut,btntanggal;
    private EditText nohpp, tanggalp, jmlp, ketp;
    private TextView emailp, namap, nomorp;
    private Spinner kep,satkerp,waktup,keperluanp;
    private LinearLayout linearLayout;

    int day,month,year,hour,minute,dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView textView = new TextView(this);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jemput);
        //get firebase auth instance

        dbTransport_Jemput = FirebaseDatabase.getInstance().getReference().child("jemput")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        //get current user

        setContentView(R.layout.activity_jemput);
        linearLayout = (LinearLayout) findViewById(R.id.layout_bio_jemput);
        namap = (TextView) findViewById(R.id.nama_jemput);
        nomorp = (TextView) findViewById(R.id.nomor_jemput);
        emailp = (TextView) findViewById(R.id.email_jemput);
        nohpp = (EditText) findViewById(R.id.telepon_jemput);
        satkerp = (Spinner) findViewById(R.id.satker_jemput);
        tanggalp = (EditText) findViewById(R.id.tanggal_jemput);
        waktup = (Spinner) findViewById(R.id.waktu_jemput);
        jmlp = (EditText) findViewById(R.id.jumlah_jemput);
        keperluanp = (Spinner) findViewById(R.id.keperluan_jemput);
        ketp = (EditText) findViewById(R.id.ket_jemput);
        kep = (Spinner) findViewById(R.id.daerah_spinner_jemput);
        btnlanjut = (Button) findViewById(R.id.btn_jemput_lanjut);
        btntanggal = (Button) findViewById(R.id.btn_tanggal_jemput);

        dbUser_Jemput = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dbUser_Jemput.child("Nomor Pegawai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String childValue = String.valueOf(dataSnapshot.getValue());
                nomorp.setText(childValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
         dbUser_Jemput.child("Nama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String childValue = String.valueOf(dataSnapshot.getValue());
                namap.setText(childValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       dbUser_Jemput.child("Email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String childValue = String.valueOf(dataSnapshot.getValue());
                emailp.setText(childValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnlanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = dbTransport_Jemput.push().getKey();
                String jumlah = jmlp.getText().toString().trim();
                textView.setText("Database Berhasil Disimpan!\nKode Pemesanan\n\n\t\t'"+id+("'\n\n(Silahkan salin kode tanpa tanda(') atau tekan tombol dibawah untuk menyalin kode tsb atau screenshot kode diatas untuk melakukan perubahan atau pembatalan pemesanan)"));
                textView.setTextIsSelectable(true);
                builder1.setView(textView);
                builder1.setCancelable(false);
                builder1.setPositiveButton("Salin Kode Pemesanan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ClipboardManager Cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText(" ",id);
                        Cm.setPrimaryClip(clip);

                        Toast.makeText(JemputActivity.this,"Kode Pemesanan sudah tersalin!",Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(JemputActivity.this,MainActivity.class));
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
                if (!TextUtils.isEmpty(jumlah)) {
                    if (jmlp.getText().length() > 0) {
                        try {
                            linearLayout.removeAllViews();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }

                        int length = Integer.parseInt(jmlp.getText().toString().trim());
                        List<EditText> listnama = new ArrayList<EditText>();
                        List<EditText> listnomor = new ArrayList<EditText>();
                        List<EditText> listalamat = new ArrayList<EditText>();

                        for (int i = 0; i < length; i++) {
                            TextView text_nama = new TextView(JemputActivity.this);
                            text_nama.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            text_nama.setText("Nama Penumpang-" + (i + 1));
                            linearLayout.addView(text_nama);

                            EditText edit_nama = new EditText(JemputActivity.this);
                            listnama.add(edit_nama);
                            edit_nama.setId(i + 1);
                            edit_nama.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout.addView(edit_nama);

                            TextView text_nomor = new TextView(JemputActivity.this);
                            text_nomor.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            text_nomor.setText("Nomor Telepon Penumpang-" + (i + 1));
                            linearLayout.addView(text_nomor);

                            EditText edit_nomor = new EditText(JemputActivity.this);
                            listnomor.add(edit_nomor);
                            edit_nomor.setId(i + 100);
                            edit_nomor.setInputType(InputType.TYPE_CLASS_PHONE);
                            edit_nomor.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout.addView(edit_nomor);

                            TextView text_alamat = new TextView(JemputActivity.this);
                            text_alamat.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            text_alamat.setText("Alamat Penumpang-" + (i + 1));
                            linearLayout.addView(text_alamat);

                            EditText edit_alamat = new EditText(JemputActivity.this);
                            listalamat.add(edit_alamat);
                            edit_alamat.setId(i + 1000);
                            edit_alamat.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            linearLayout.addView(edit_alamat);
                        }

                        Button simpan = new Button(JemputActivity.this);
                        simpan.setText("Simpan");
                        simpan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayout.addView(simpan);

                        simpan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String email = emailp.getText().toString().trim();
                                String nohp = nohpp.getText().toString().trim();
                                String satker = satkerp.getSelectedItem().toString();
                                String tanggal = tanggalp.getText().toString().trim();
                                String waktu = waktup.getSelectedItem().toString();
                                String jml = jmlp.getText().toString().trim();
                                String keperluan = keperluanp.getSelectedItem().toString();
                                String ket = ketp.getText().toString().trim();
                                String ke = kep.getSelectedItem().toString();
                                Boolean ambil = false;
                                if (!TextUtils.isEmpty(email)) {
                                    if (!TextUtils.isEmpty(nohp)) {
                                        if (!TextUtils.isEmpty(satker)) {
                                            if (!TextUtils.isEmpty(tanggal)) {
                                                if (!TextUtils.isEmpty(waktu)) {
                                                    if (!TextUtils.isEmpty(jml)) {
                                                        if (!TextUtils.isEmpty(keperluan)) {
                                                            if (!TextUtils.isEmpty(ket)) {
                                                                if (!TextUtils.isEmpty(ke)) {
                                                                    addJemput(id);
                                                                    String[] nama = new String[listnama.size()];
                                                                    String[] nomor = new String[listnomor.size()];
                                                                    String[] alamat = new String[listalamat.size()];
                                                                    for (int i = 0; i < listnama.size(); i++) {
                                                                        nama[i] = listnama.get(i).getText().toString();
                                                                        dbTransport_Jemput.child(id).child("Nama Penumpang-" + (i + 1)).setValue(nama[i]);
                                                                    }

                                                                    for (int i = 0; i < listnomor.size(); i++) {
                                                                        nomor[i] = listnomor.get(i).getText().toString();
                                                                        dbTransport_Jemput.child(id).child("Nomor Telepon Penumpang-" + (i + 1)).setValue(nomor[i]);
                                                                    }

                                                                    for (int i = 0; i < listalamat.size(); i++) {
                                                                        alamat[i] = listalamat.get(i).getText().toString();
                                                                        dbTransport_Jemput.child(id).child("Alamat Penumpang-" + (i + 1)).setValue(alamat[i]);
                                                                    }
                                                                    alert1.show();
                                                                } else {
                                                                    Toast.makeText(JemputActivity.this, "Silahkan isi tujuan", Toast.LENGTH_LONG).show();
                                                                }

                                                            } else {
                                                                Toast.makeText(JemputActivity.this, "Silahkan isi keterangan", Toast.LENGTH_LONG).show();
                                                            }

                                                        } else {
                                                            Toast.makeText(JemputActivity.this, "Silahkan isi keperluan", Toast.LENGTH_LONG).show();
                                                        }

                                                    } else {
                                                        Toast.makeText(JemputActivity.this, "Silahkan isi jumlah penumpang", Toast.LENGTH_LONG).show();
                                                    }

                                                } else {
                                                    Toast.makeText(JemputActivity.this, "Silahkan isi waktu", Toast.LENGTH_LONG).show();
                                                }

                                            } else {
                                                Toast.makeText(JemputActivity.this, "Silahkan isi tanggal", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(JemputActivity.this, "Silahkan isi satuan kerja", Toast.LENGTH_LONG).show();
                                        }

                                    } else {
                                        Toast.makeText(JemputActivity.this, "Silahkan isi no hp", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    //if the value is not given displaying a toast
                                    Toast.makeText(JemputActivity.this, "Please enter a name", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(JemputActivity.this,"Silahkan Isi Jumlah Penumpang",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btntanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(JemputActivity.this,JemputActivity.this, year, month, day);
                datePickerDialog.show();
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


    private void addJemput(String id) {
        String email = emailp.getText().toString().trim();
        String nohp = nohpp.getText().toString().trim();
        String satker = satkerp.getSelectedItem().toString();
        String tanggal = tanggalp.getText().toString().trim();
        String waktu = waktup.getSelectedItem().toString();
        String jml = jmlp.getText().toString().trim();
        String keperluan = keperluanp.getSelectedItem().toString();
        String ket = ketp.getText().toString().trim();
        String ke = kep.getSelectedItem().toString();
        Boolean ambil = false;

        jemput jemputp = new jemput(email, nohp, satker, tanggal, waktu, jml, keperluan, ket, ke, ambil);
        dbTransport_Jemput.child(id).setValue(jemputp);
        Toast.makeText(this, "Tersimpan", Toast.LENGTH_LONG).show();

    }
}