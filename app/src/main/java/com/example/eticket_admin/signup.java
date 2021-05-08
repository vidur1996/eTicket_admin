package com.example.eticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class signup extends AppCompatActivity {
    EditText password_edit, editText_email  ;
    EditText confirm_password_edit;
    TextView password_error,email_error ;
    Button signup ;
    String password;
    Spinner spinner_userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        spinner_userType = findViewById(R.id.spinner_userType);
         password_edit  = findViewById(R.id.password_edit);
         confirm_password_edit=findViewById(R.id.password_confirm_edit);
        password_error = findViewById(R.id.text_password_invalid);
        signup = findViewById(R.id.button_signup);
        editText_email = findViewById(R.id.editText_email);
        email_error = findViewById(R.id.text_email_error);

        ArrayAdapter<String> myadapter  = new ArrayAdapter<String>(signup.this
                ,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.user_type));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_userType.setAdapter(myadapter);


        editText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    email_error.setText("invalid email");
                }else{
                    email_error.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String utype = spinner_userType.getSelectedItem().toString();
                String pass1 = password_edit.getText().toString().trim();
                String pass2 = confirm_password_edit.getText().toString().trim();
                if(comparePassword(pass1,pass2)){
                    Toast.makeText(signup.this,"valid password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public boolean comparePassword(String pass1,String pass2)
    {



        if(pass1.equals(pass2)){
            this.password = pass1;
            password_error.setText("");
            return true;
        }else {
            password_error.setText("Both password dont match");
            return   false;
        }
    }

    public Boolean saveData(String utype,String name,String email,String pNo,String username,String password){
        return true;

    }

    public Boolean verifyData(){
        if

    }




}
