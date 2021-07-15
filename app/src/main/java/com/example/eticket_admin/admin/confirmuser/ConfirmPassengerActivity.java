package com.example.eticket_admin.admin.confirmuser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eticket_admin.MainActivity;
import com.example.eticket_admin.R;
import com.example.eticket_admin.admin.confirmuser.adapter.UserPassengerAdapter;
import com.example.eticket_admin.data.Member;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ConfirmPassengerActivity extends AppCompatActivity implements UserPassengerAdapter.onClickPassengerAdapter {
    DatabaseReference databaseReference;
    ArrayList<Member> list = new ArrayList<Member>();
    UserPassengerAdapter adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_registration);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("temp_member");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if (snapshot.exists()) {
                    Member value3 = snapshot.getValue(Member.class);
                    list.add(value3);
                    adapter3.notifyDataSetChanged();
                } else {

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


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_confirm_user);
        adapter3 = new UserPassengerAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter3);
        adapter3.onClickPassengerAdapter(this);

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

    public void Alert(String title, String message, String positve_btn) {
        new MaterialAlertDialogBuilder(ConfirmPassengerActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positve_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).show();

    }

    @Override
    public void onAcceptClick(Member acceptUser, int index) {

        Member mem = new Member();
        mem.setName(acceptUser.getName());
        mem.setEmail(acceptUser.getEmail());
        mem.setNum(acceptUser.getNum());
        mem.setUsername(acceptUser.getUsername());
        mem.setPassword(acceptUser.getPassword());
        mem.setLock(acceptUser.getLock());

        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Alert("Success", "Passenger authorized", "OK");
            }
        };
        reff.child("member").child(acceptUser.getUsername()).setValue(mem, completionListener);
        reff.child("temp_member").child(acceptUser.getUsername()).removeValue();
        list.remove(index);
        adapter3.notifyDataSetChanged();
    }

    @Override
    public void onDeclineClick(Member declineUser, int index) {
        new MaterialAlertDialogBuilder(ConfirmPassengerActivity.this)
                .setTitle("Alert")
                .setMessage("Are you sure you want to reject " + declineUser.getName())
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

                        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            }
                        };

                        reff.child("temp_member").child(declineUser.getUsername()).removeValue();
                        list.remove(index);
                        adapter3.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).show();
    }
}