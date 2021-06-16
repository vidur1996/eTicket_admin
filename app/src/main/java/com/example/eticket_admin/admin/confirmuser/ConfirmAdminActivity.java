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
import com.example.eticket_admin.admin.confirmuser.adapter.UserAdminAdapter;
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

public class ConfirmAdminActivity extends AppCompatActivity implements UserAdminAdapter.onClickAdminAdapter {
    DatabaseReference databaseReference;
    ArrayList<User> list = new ArrayList<User>();
    UserAdminAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_admin);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("admin_pending").child("Administrator");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    User value= snapshot.getValue(User.class);
                    list.add(value);
                    adapter.notifyDataSetChanged();
                }
                else{

                    showAlert("Alert","No pending request","GOT IT");
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




        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_confirm_admin);
        adapter = new UserAdminAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.onClickAdminAdapter(this);
    }

    public void showAlert(String title,String message,String positve_btn) {
        new MaterialAlertDialogBuilder(ConfirmAdminActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positve_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(it);
                        finish();

                    }
                }).show();

    }

    public void Alert(String title,String message,String positve_btn) {
        new MaterialAlertDialogBuilder(ConfirmAdminActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positve_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }).show();

    }

      @Override
    public void onAcceptClick(User acceptUser,int index) {
          Admin admin = new Admin();
          admin.setName(acceptUser.name);
          admin.setEmail(acceptUser.email);
          admin.setNum(acceptUser.num);
          admin.setUsername(acceptUser.username);
          admin.setPassword(acceptUser.password);
          admin.setType("admin");

          DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

          DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
              @Override
              public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                  Alert("Success","Passenger authorized","OK");
              }
          };
          reff.child("admin").child(acceptUser.username).setValue(admin, completionListener);
          reff.child("admin_pending").child("Administrator").child(acceptUser.username).removeValue();
          list.remove(index);
          adapter.notifyDataSetChanged();
    }

    @Override
    public void onDeclineClick(User declineUser,int index) {
        new MaterialAlertDialogBuilder(ConfirmAdminActivity.this)
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

                        reff.child("admin_pending").child("Administrator").child(declineUser.username).removeValue();
                        list.remove(index);
                        adapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }) .show();
    }
}