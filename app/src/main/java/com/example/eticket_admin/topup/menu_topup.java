package com.example.eticket_admin.topup;

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
import com.example.eticket_admin.signup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class menu_topup extends AppCompatActivity {
    EditText editText_username,editText_amount;
    TextView username_error;
    Button signout,clear,pay,change_pass;
    String topname,pastvalue,amount;
    Boolean check = false;

    DatabaseReference topup_reffer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_topup);
        editText_amount = findViewById(R.id.amount_txt);
        editText_username=  findViewById(R.id.editText_username_topup);
        username_error = findViewById(R.id.text_username_invalid_topup);
        clear=findViewById(R.id.clear_pay_btn);
        pay=findViewById(R.id.confirm_pay_btn);
        change_pass=findViewById(R.id.change_password_topup);
        signout  = findViewById(R.id.out_btn3);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            topname = extras.getString("topname");
        }

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in1);
                menu_topup.this.finish();
            }
        });
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(getApplicationContext(),TopUpChangePasswordActivity.class);
                in1.putExtra("topname",topname);
                startActivity(in1);
                menu_topup.this.finish();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_amount.setText("");
                editText_username.setText("");
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_value()){
                    if(topup()){
                                showAlert();
                    }

                }
            }
        });

    }
    public boolean check_value(){
        if(editText_username.getText().toString()!=""){
            username_error.setText("");
            if(editText_amount.getText().toString()!=""){
                return true;

            }else {
                Toast.makeText(menu_topup.this, "enter amount", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else{
            username_error.setText("username is empty");
            return false;
        }
    }

    public boolean topup(){
        String username = editText_username.getText().toString().trim();
        amount = editText_amount.getText().toString().trim();
        topup_reffer = FirebaseDatabase.getInstance().getReference().child("member").child(username);



        topup_reffer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pastvalue = snapshot.child("balance").getValue().toString().trim();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        int newvalue=0;
    if(pastvalue!=null && amount !=null){
        newvalue = Integer.parseInt(pastvalue)+Integer.parseInt(amount);
    }


        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                check = true;
            }
        };
        topup_reffer.child("balance").setValue(newvalue, completionListener);

            return check;

    }

    public void showAlert() {
        new MaterialAlertDialogBuilder(menu_topup.this)
                .setTitle("Top-up success")
                .setMessage("You have top-up Rs."+amount)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editText_amount.setText("");
                        editText_username.setText("");
                        check = false;

                    }
                }).show();

    }
}
