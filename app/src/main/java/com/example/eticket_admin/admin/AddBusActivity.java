package com.example.eticket_admin.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eticket_admin.R;
import com.example.eticket_admin.data.Bus;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddBusActivity extends AppCompatActivity {
    Button btn_create_bus;
    EditText et_id, et_number, et_owner, et_conductor;
    String uname;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);
        btn_create_bus = (Button) findViewById(R.id.btn_create_bus);
        et_id = (EditText) findViewById(R.id.et_bus_id);
        et_conductor = (EditText) findViewById(R.id.et_bus_conductor);
        et_number = (EditText) findViewById(R.id.et_bus_number);
        et_owner = (EditText) findViewById(R.id.et_bus_owner);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            uname = extras.getString("uname");
        }
        et_id.setText(genarateId());

        btn_create_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validdata()) {
                    if (saveData()) {
                        showAlert();
                    }
                }
            }
        });

    }

    public Boolean validdata() {
        if (et_owner.getText().toString().equals("")) {
            Toast.makeText(this, "owner name required", Toast.LENGTH_LONG).show();
            return false;
        } else if (et_conductor.getText().toString().equals("")) {
            Toast.makeText(this, "conductor name required", Toast.LENGTH_LONG).show();
            return false;
        } else if (et_number.getText().toString().equals("")) {
            Toast.makeText(this, "bus number required", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public String genarateId() {
       /* final Boolean[] newId = {true};
        final String[] id = {UUID.randomUUID().toString().toUpperCase().substring(0, 6)};
        reff = FirebaseDatabase.getInstance().getReference().child("bus");
        while(newId[0]){
            String  iid = UUID.randomUUID().toString().toUpperCase().substring(0,6);
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(iid)){
                        newId[0] =true;

                    }else {
                        id[0] =iid;
                        newId[0] =false;
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }

          return id[0];*/
        return UUID.randomUUID().toString().toUpperCase().substring(0, 6);
    }

    public void showAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Registration success")
                .setMessage("Bus added Successful")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(getApplicationContext(), main_menu.class);
                        it.putExtra("adminname", uname);
                        startActivity(it);
                        finish();

                    }
                }).show();

    }

    public Boolean saveData() {
        Bus bus = new Bus();
        String id = et_id.getText().toString().trim();
        bus.setBusId(id);
        bus.setBusNumber(et_number.getText().toString().trim());
        bus.setConductor(et_conductor.getText().toString().trim());
        bus.setOwner(et_owner.getText().toString().trim());
        bus.setRevenue(0);


        DatabaseReference reff2 = FirebaseDatabase.getInstance().getReference().child("bus");
        final boolean[] check = {true};
        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                check[0] = error == null;
            }
        };
        reff2.child(id).setValue(bus, completionListener);

        return check[0];
    }

}