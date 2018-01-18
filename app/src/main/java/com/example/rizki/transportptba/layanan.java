package com.example.rizki.transportptba;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by rizki on 12/30/2017.
 */
@IgnoreExtraProperties
public class layanan {
    private String emailp;
    private String nohp;
    private String satker;
    private String tanggal_mulai;
    private String tanggal_selesai;
    private String waktu;
    private String jmlpenumpang;
    private String keperluan;
    private String ket;
    private String dari;
    private String ke;
    private Boolean ambil;

    public layanan() {

    }
    public layanan(String emailp, String nohp, String satker, String tanggal_mulai,String tanggal_selesai, String waktu,String jmlpenumpang, String keperluan, String ket,String dari, String ke, Boolean ambil) {
        this.emailp = emailp;
        this.nohp = nohp;
        this.satker = satker;
        this.tanggal_mulai = tanggal_mulai;
        this.tanggal_selesai = tanggal_selesai;
        this.waktu = waktu;
        this.jmlpenumpang = jmlpenumpang;
        this.keperluan = keperluan;
        this.ket = ket;
        this.dari = dari;
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

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public String getTanggal_selesai(){ return tanggal_selesai;}

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

    public String getDari(){ return dari;}

    public String getKe() {
        return ke;
    }

    public Boolean getAmbil() { return ambil; }

}