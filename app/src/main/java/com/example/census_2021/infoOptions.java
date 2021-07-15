package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class infoOptions extends AppCompatActivity {
    String uID;
    String surveyName;
    CardView garph,entries;
    TextView nameuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_options);
        surveyName=getIntent().getStringExtra("name");
        garph=(CardView)findViewById(R.id.EntriesGraph);
        entries=(CardView)findViewById(R.id.Entries);
        nameuser=(TextView)findViewById(R.id.textView2);
        nameuser.setText(surveyName);

        garph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(infoOptions.this, selectQueForGraph.class);
                intent.putExtra("name",surveyName);
                startActivity(intent);
            }
        });
        entries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(infoOptions.this, selectUser.class);
                intent.putExtra("name",surveyName);
                startActivity(intent);
            }
        });
    }
}