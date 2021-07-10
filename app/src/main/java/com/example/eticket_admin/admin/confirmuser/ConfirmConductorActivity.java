package com.example.eticket_admin.admin.confirmuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.eticket_admin.MainActivity;
import com.example.eticket_admin.R;
import com.example.eticket_admin.admin.confirmuser.adapter.UserConductorAdapter;
import com.example.eticket_admin.data.Admin;
import com.example.eticket_admin.data.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ConfirmConductorActivity extends AppCompatActivity implements UserConductorAdapter.onClickConductorAdapter{
    DatabaseReference databaseReference;
    ArrayList<User> list = new ArrayList<User>();
    UserConductorAdapter adapter2;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_conductor);
        recycler();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("admin_pending").child("Conductor");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    User value2 = snapshot.getValue(User.class);
                    list.add(value2);
                    adapter2.notifyDataSetChanged();
                }
                else{

                    showAlert();
                }

            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
    public void showAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Alert")
                .setMessage("No pending request")
                .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(it);
                        finish();

                    }
                }).show();

    }

    public void recycler(){
        recyclerView = (RecyclerView) findViewById(R.id.rv_confirm_conductor);
         adapter2 = new UserConductorAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter2);
        adapter2.onClickConductorAdapter(this);
    }
    public void Alert(String title,String message,String positve_btn) {
        new MaterialAlertDialogBuilder(ConfirmConductorActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positve_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).show();

    }

    @Override
    public void onAcceptClick(User acceptUser, int index) {
        Admin admin = new Admin();
        admin.setName(acceptUser.name);
        admin.setEmail(acceptUser.email);
        admin.setNum(acceptUser.num);
        admin.setUsername(acceptUser.username);
        admin.setPassword(acceptUser.password);
        admin.setBus(acceptUser.bus);
        admin.setType("conductor");

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Alert("Success","Passenger authorized","OK");
            }
        };
        reff.child("admin").child(acceptUser.username).setValue(admin, completionListener);
        reff.child("admin_pending").child("Conductor").child(acceptUser.username).removeValue();
        list.remove(index);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void onDeclineClick(User declineUser, int index) {
        new MaterialAlertDialogBuilder(ConfirmConductorActivity.this)
                .setTitle("Alert")
                .setMessage("Are you sure you want to reject "+declineUser.name)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

                        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            }
                        };

                        reff.child("admin_pending").child("Conductor").child(declineUser.username).removeValue();
                        list.remove(index);
                        adapter2.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }) .show();
    }
}