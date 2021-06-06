package com.example.eticket_admin.admin.confirmuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.eticket_admin.R;

public class ConfirmConductorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_conductor);

        User[] myListData = new User[] {
                new User("Email","puser","pemail","ppassword","p12334"),
                new User("Info","puser","pemail","ppassword","p12334"),
                new User("Delete","puser","pemail","ppassword","p12334"),
                new User("Dialer","puser","pemail","ppassword","p12334"),
                new User("Alert","puser","pemail","ppassword","p12334"),
                new User("Map","puser","pemail","ppassword","p12334"),
                new User("Email","puser","pemail","ppassword","p12334"),
                new User("Info","puser","pemail","ppassword","p12334"),
                new User("Delete","puser","pemail","ppassword","p12334"),
                new User("Dialer","puser","pemail","ppassword","p12334"),
                new User("Alert","puser","pemail","ppassword","p12334"),
                new User("Map","puser","pemail","ppassword","p12334")
        };
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_confirm_conductor);
        UserAdapter adapter = new UserAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}