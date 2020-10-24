package com.example.eticket_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class confirm_ticket extends AppCompatActivity {
String scandata,conname;
String to="eror";
    String cos_balance="eror";
String from="eror";
String cos_user="eror";
String price="eror";
Button confirm1,reject1;
    DatabaseReference reffer1;

EditText to_txt,from_txt,price_txt,user_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_ticket);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            scandata = extras.getString("scandata");
            conname = extras.getString("uname");
        }


        confirm1 = findViewById(R.id.confirm_btn);
        reject1 = findViewById(R.id.reject_btn);
        user_txt = findViewById(R.id.uname_txtcon);
        to_txt = findViewById(R.id.to_txtcon);
        from_txt = findViewById(R.id.from_txtcon);
        price_txt = findViewById(R.id.price_txtcon);
        breakdata(scandata);
        to_txt.setText(to)  ;
        from_txt.setText(from);
        user_txt.setText(cos_user) ;
        price_txt.setText(price);


            confirm1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reffer1 = FirebaseDatabase.getInstance().getReference().child("member").child(cos_user);
                    reffer1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            cos_balance = snapshot.child("balance").getValue().toString();
                            if(Integer.parseInt(cos_balance)>Integer.parseInt(price))
                            {
                                int new_balance;
                                new_balance = (Integer.parseInt(cos_balance)-Integer.parseInt(price));
                                cos_balance = String.valueOf(new_balance);

                                 reffer1.child("balance").setValue(cos_balance);
                                Toast.makeText(confirm_ticket.this,"ticket successful",Toast.LENGTH_SHORT);
                            }
                            else
                            {
                                Toast.makeText(confirm_ticket.this,"balance insufficient",Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });

    }


    public void breakdata(String scandata)
    {
        int len = scandata.length();
        String[] arr  = new String[4];
        String x = "";
        int count =0;
        for(int i =0;i<len;i++)
        {
            char c = scandata.charAt(i);
            if(c =='*')
            {
                arr[count] = x;
                count++;
                x="";
            }
            else
            {
                x = x + c;
            }
        }
        to  = arr[1];
        from= arr[2];
        cos_user = arr[0];
        price= x;
    }
}
