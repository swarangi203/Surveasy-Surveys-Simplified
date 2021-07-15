package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDataEntryOptions extends AppCompatActivity {
    String uID;
    String surveyName;
    CardView garph,entries;
    TextView nameuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_entry_options);
        uID=getIntent().getStringExtra("uid").toString();
        surveyName=getIntent().getStringExtra("name");
        garph=(CardView)findViewById(R.id.EntriesGraph);
        entries=(CardView)findViewById(R.id.Entries);
        nameuser=(TextView)findViewById(R.id.textView2);
        FirebaseDatabase.getInstance().getReference("users").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass data=snapshot.getValue(UserHelperClass.class);
                String Username=data.getName();
                nameuser.setText(Username);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserDataEntryOptions.this, "Error Fetching User Name", Toast.LENGTH_SHORT).show();
            }
        });

        garph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(UserDataEntryOptions.this, .class);
//                startActivity(intent);
            }
        });
        entries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDataEntryOptions.this, UserEntries.class);
                intent.putExtra("uid",uID);
                intent.putExtra("name",surveyName);
                startActivity(intent);
            }
        });
    }
}