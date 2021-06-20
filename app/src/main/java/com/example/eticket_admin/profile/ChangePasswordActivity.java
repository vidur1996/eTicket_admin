package com.example.eticket_admin.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eticket_admin.MainActivity;
import com.example.eticket_admin.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ChangePasswordActivity extends AppCompatActivity {
    Button btn_updatePassword;
   EditText et_currentPassword,et_newPassword,et_confirmPassword;
    String uname,currentPassword;
    DatabaseReference reff,password_reffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        btn_updatePassword = (Button)findViewById(R.id.btn_update_password);
        et_currentPassword = (EditText)findViewById(R.id.et_password_current);
        et_newPassword = (EditText)findViewById(R.id.et_password_new);
        et_confirmPassword = (EditText)findViewById(R.id.et_password_confirm);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            uname = extras.getString("uname");
        }
    getCurrentPassword();
        btn_updatePassword.setOnClickListener(v -> {
            updatePassword();
        });



    }

    public void getCurrentPassword(){
        reff = FirebaseDatabase.getInstance().getReference().child("admin").child(uname);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                currentPassword = snapshot.child("password").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    public Boolean verifyPassword(){

        if (et_confirmPassword.getText().toString().equals(et_newPassword.getText().toString())){
            return true;

        }else {
            Toast.makeText(this, "Password don't match", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public Boolean checkEmptyPassword(){
        return (!et_currentPassword.getText().toString().equals("")||
                !et_confirmPassword.getText().toString().equals("")||
                !et_newPassword.getText().toString().equals(""));
    }

    public void updatePassword(){
        if(checkEmptyPassword()){
            if(currentPassword.equals(et_currentPassword.getText().toString())) {
                if(verifyPassword()){
                    savePassword(et_newPassword.getText().toString());
                }

            }else {
                Toast.makeText(this, "current password is wrong", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Password missing", Toast.LENGTH_SHORT).show();
        }
    }

    public void savePassword(String newPassword){
       password_reffer = FirebaseDatabase.getInstance().getReference().child("admin").child(uname);
        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                showAlert();
            }
        };
        password_reffer.child("password").setValue(newPassword, completionListener);
    }

    public void moveToProfile(){
        Intent it = new Intent(getApplicationContext(), ProfileActivity.class);
        it.putExtra("uname",uname);
        startActivity(it);
        finish();
    }

    public void showAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Success")
                .setMessage("Pasword changed Successful")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveToProfile();

                    }
                }).show();

    }
}