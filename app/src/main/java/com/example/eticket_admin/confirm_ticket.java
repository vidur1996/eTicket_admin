package com.example.eticket_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class confirm_ticket extends AppCompatActivity {
String scandata;
String to="eror";

    String from="eror";
    String user="eror";
    String price="eror";

EditText to_txt,from_txt,price_txt,user_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_ticket);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            scandata = extras.getString("scandata");
        }

       // data = uname1+"*"+town_to1+"*"+town_from1+"*"+price1;

        user_txt = findViewById(R.id.uname_txtcon);
        to_txt = findViewById(R.id.to_txtcon);
        from_txt = findViewById(R.id.from_txtcon);
        price_txt = findViewById(R.id.price_txtcon);
        breakdata(scandata);
        to_txt.setText(to)  ;
        from_txt.setText(from);
        user_txt.setText(user) ;
        price_txt.setText(price);


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
        user = arr[0];
        price= x;
    }
}
