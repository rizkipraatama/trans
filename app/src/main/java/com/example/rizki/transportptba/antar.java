package com.example.rizki.transportptba;
import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by rizki on 12/25/2017.
 */
@IgnoreExtraProperties
public class antar {
    private String emailp;
    private String nohp;
    private String satker;
    private String tanggal;
    private String waktu;
    private String jmlpenumpang;
    private String keperluan;
    private String ket;
    private String ke;
    Boolean ambil;
    public antar() {
    }

    public antar(String emailp, String nohp, String satker, String tanggal, String waktu, String jmlpenumpang, String keperluan, String ket,String ke, Boolean ambil) {
        this.emailp = emailp;
        this.nohp = nohp;
        this.satker = satker;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.jmlpenumpang = jmlpenumpang;
        this.keperluan = keperluan;
        this.ket = ket;
        this.ke=ke;
        this.ambil=ambil;
    }

    public String getEmailp() {
        return emailp;
    }

    public String getNohp() {
        return nohp;
    }

    public String getSatker() {
        return satker;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getJmlpenumpang() {
        return jmlpenumpang;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public String getKet() {
        return ket;
    }

    public String getKe() {
        return ke;
    }

    public Boolean getAmbil() {
        return ambil;
    }
}