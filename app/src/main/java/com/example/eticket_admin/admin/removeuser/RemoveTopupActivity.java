package com.example.eticket_admin.admin.removeuser;

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
import com.example.eticket_admin.admin.removeuser.adapter.RemoveConductorAdapter;
import com.example.eticket_admin.admin.removeuser.adapter.RemoveTopupAdapter;
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

public class RemoveTopupActivity extends AppCompatActivity implements RemoveTopupAdapter.onClickTopupRemoveAdapter {
    DatabaseReference databaseReference;
    ArrayList<Admin> list = new ArrayList<Admin>();
    RemoveTopupAdapter adapter2;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_topup);
        recycler();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("admin");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    Admin value2 = snapshot.getValue(Admin.class);
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
        recyclerView = (RecyclerView) findViewById(R.id.rv_remove_topup);
        adapter2 = new RemoveTopupAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter2);
        adapter2.onClickTopupRemoveAdapter(this );
    }



    @Override
    public void onDeleteClick(Admin deleteUser, int index) {
        new MaterialAlertDialogBuilder(RemoveTopupActivity.this)
                .setTitle("Alert")
                .setMessage("Are you sure you want to remove  "+deleteUser.getName()+" from the database")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

                        DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            }
                        };

                        reff.child("admin").child(deleteUser.getUsername()).removeValue();
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