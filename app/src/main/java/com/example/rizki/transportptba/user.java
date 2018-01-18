package com.example.rizki.transportptba;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by rizki on 12/26/2017.
 */
@IgnoreExtraProperties
public class user {
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public static String FB_REFERENCE_USER = "user";
    private String inputEmail;
    private String inputNama;
    private String inputNomor;
    private String inputPassword;

        String uid;

    public user(){};

    public user (String uid, String inputEmail, String inputNama, String inputNomor, String inputPassword){
        this.inputEmail = inputEmail;
        this.inputNama = inputNama;
        this.inputNomor = inputNomor;
        this.inputPassword = inputPassword;

    }

    String getInputEmail(){
        return inputEmail;
    }
    String getInputNama(){
        return inputNama;
    }
    String getInputNomor(){
        return inputNomor;
    }
    String getInputPassword(){
        return inputPassword;
    }
    public void writeNewUser(String uid, String inputEmail, String inputNama, String inputNomor,String inputPassword){
        database.child("user").child(uid).child("Username").setValue(inputEmail);
        database.child("user").child(uid).child("Password").setValue(inputPassword);
        database.child("user").child(uid).child("Name").setValue(inputNama);
        database.child("user").child(uid).child("Number").setValue(inputNomor);
    }

}

