package com.example.census_2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class manageUser extends AppCompatActivity {
    String uID;
    CardView changepost,changestate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        uID = getIntent().getStringExtra("uid").toString();
        changepost =(CardView)findViewById(R.id.changeMob);
        changestate=(CardView)findViewById(R.id.changepass);

        changepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(manageUser.this, UserToAdmin.class);
                intent.putExtra("uid",uID);
                startActivity(intent);
            }
        });

        changestate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(manageUser.this, DeleteUser.class);
                intent.putExtra("uid",uID);
                startActivity(intent);
            }
        });

    }
}