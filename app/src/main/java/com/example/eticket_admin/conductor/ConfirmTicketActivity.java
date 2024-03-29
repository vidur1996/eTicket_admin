package com.example.eticket_admin.conductor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket_admin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfirmTicketActivity extends AppCompatActivity {
    String scandata, conname, bus_name;

    String to = "eror";
    int customer_bal = 0;
    String from = "eror";
    String cos_user = "eror";
    String price = "0";
    Button confirm1, reject1;
    DatabaseReference reffer1;

    TextView id1;

    EditText to_txt, from_txt, price_txt, user_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_ticket);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            scandata = extras.getString("scandata");
            conname = extras.getString("conname");
            bus_name = extras.getString("bus_name");
        }

        id1 = findViewById(R.id.id1);
        confirm1 = findViewById(R.id.confirm_btn);
        reject1 = findViewById(R.id.reject_btn);
        user_txt = findViewById(R.id.uname_txtcon);
        to_txt = findViewById(R.id.to_txtcon);
        from_txt = findViewById(R.id.from_txtcon);
        price_txt = findViewById(R.id.price_txtcon);
        breakdata(scandata);
        to_txt.setText(to);
        from_txt.setText(from);
        user_txt.setText(cos_user);
        price_txt.setText(price);

        final int iprice = Integer.parseInt(price);

        reffer1 = FirebaseDatabase.getInstance().getReference().child("member").child(cos_user);
        reffer1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String customer_balance = snapshot.child("balance").getValue().toString();
                    customer_bal = Integer.parseInt(customer_balance);
                } else {

                    Toast.makeText(ConfirmTicketActivity.this, "database error", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        confirm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customer_bal > iprice) {
                    customer_bal = customer_bal - iprice;
                    reffer1.child("balance").setValue(customer_bal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ConfirmTicketActivity.this, "transaction succesful", Toast.LENGTH_SHORT);

                            Intent i2 = new Intent(getApplicationContext(), TimeOutScreenActivity.class);
                            i2.putExtra("conname", conname);
                            i2.putExtra("price", price);
                            i2.putExtra("bus_name", bus_name);
                            i2.putExtra("pass_name", cos_user);
                            i2.putExtra("pass_to", to);
                            i2.putExtra("pass_from", from);
                            startActivity(i2);
                            finish();
                        }
                    });
                } else {
                   // id1.setText("value less ");
                    showAlert();
                }
            }
        });


    }


    public void breakdata(String scandata) {
        int len = scandata.length();
        String[] arr = new String[4];
        String x = "";
        int count = 0;
        for (int i = 0; i < len; i++) {
            char c = scandata.charAt(i);
            if (c == '*') {
                arr[count] = x;
                count++;
                x = "";
            } else {
                x = x + c;
            }
        }
        to = arr[1];
        from = arr[2];
        cos_user = arr[0];
        price = x;
    }

    public void showAlert() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        new MaterialAlertDialogBuilder(this)
                .setTitle("low Balance")
                .setMessage("low Balance")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).show();

    }
}
