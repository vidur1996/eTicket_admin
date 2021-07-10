package com.example.eticket_admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket_admin.data.Admin;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    EditText password_edit, editText_email, editText_name, editText_phone, editText_username;
    EditText confirm_password_edit,editText_busId;
    TextView password_error, email_error,tv_bus_label;
    Button signup;
    String password;
    Spinner spinner_userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        spinner_userType = findViewById(R.id.spinner_userType);
        password_edit = findViewById(R.id.password_edit);
        confirm_password_edit = findViewById(R.id.password_confirm_edit);
        password_error = findViewById(R.id.text_password_invalid);
        signup = findViewById(R.id.button_signup);
        editText_email = findViewById(R.id.editText_email);
        email_error = findViewById(R.id.text_email_error);
        editText_name = findViewById(R.id.editText_name);
        editText_busId  = findViewById(R.id.editText_bus_id);
        editText_phone = findViewById(R.id.editText_phone);
        editText_username = findViewById(R.id.editText_username);
        tv_bus_label = findViewById(R.id.tv_bus_label);
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(signup.this
                , android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.user_type));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_userType.setAdapter(myadapter);


        spinner_userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (String.valueOf(spinner_userType.getSelectedItemId())=="1"){
                    editText_busId.setVisibility(View.VISIBLE);
                    tv_bus_label.setVisibility(View.VISIBLE);
                }else {
                    editText_busId.setVisibility(View.GONE);
                    tv_bus_label.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    email_error.setText("");
                } else {

                    email_error.setText("invalid email");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String utype = spinner_userType.getSelectedItem().toString();
                String pass1 = password_edit.getText().toString().trim();
                String pass2 = confirm_password_edit.getText().toString().trim();
                if (comparePassword(pass1, pass2) && verifyData(utype)) {
                    if (saveData(utype, password)) {
                        Toast.makeText(signup.this, "registration successful", Toast.LENGTH_LONG).show();
                        showAlert();
                    }

                }
            }
        });
    }


    public void showAlert() {
        new MaterialAlertDialogBuilder(signup.this)
                .setTitle("Registration success")
                .setMessage("You will recieve a mail after verification")
                .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(it);
                        finish();

                    }
                }).show();

    }


    public boolean comparePassword(String pass1, String pass2) {
        if (pass1.equals(pass2)) {
            if (pass1 != "" && pass2 != "") {
                this.password = pass1;
                password_error.setText("");
                return true;
            } else {
                return false;
            }
        } else {
            password_error.setText("Both password dont match");
            return false;
        }
    }

    public Boolean saveData(String utype, String password) {
        Admin admin = new Admin();
        admin.setName(editText_name.getText().toString().trim());
        admin.setEmail(editText_email.getText().toString().trim());
        admin.setNum(editText_phone.getText().toString().trim());
        admin.setUsername(editText_username.getText().toString().trim());
        admin.setPassword(password);
        if(!editText_busId.getText().toString().trim().equals("")){
            admin.setBus(editText_busId.getText().toString().trim());
        }else {
            admin.setBus("0");
        }


        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("admin_pending").child(utype);
        final boolean[] check = {true};
        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                check[0] = error == null;

            }
        };
        reff.child(editText_username.getText().toString().trim()).setValue(admin, completionListener);

        return check[0];
    }

    public Boolean verifyData(String utype) {
        if (editText_username.getText().toString().equals("")) {
            Toast.makeText(signup.this, "username required", Toast.LENGTH_LONG).show();
            return false;
        }
        if (editText_name.getText().toString().equals("")) {
            Toast.makeText(signup.this, "name required", Toast.LENGTH_LONG).show();
            return false;
        }
        if (editText_email.getText().toString().equals("")) {
            Toast.makeText(signup.this, "email required", Toast.LENGTH_LONG).show();
            return false;
        }
        if (editText_phone.getText().toString().equals("")) {
            Toast.makeText(signup.this, "phone number required", Toast.LENGTH_LONG).show();
            return false;
        }
        if(utype=="Conductor" && editText_busId.getText().toString().equals("")){
            Toast.makeText(signup.this, "bus id required", Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }

    }


}
