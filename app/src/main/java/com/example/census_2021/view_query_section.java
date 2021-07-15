package com.example.census_2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class view_query_section extends AppCompatActivity {
    Button btn1,btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_query_section);
        btn1=findViewById(R.id.btn_data_change);
        btn2=findViewById(R.id.btn_other_query);
        btn3=findViewById(R.id.numberquery);

      //Name change activity
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_query_section.this, data_changes_queries.class);
                startActivity(intent);
            }
        });
    //other queries
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_query_section.this, other_queries.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view_query_section.this, ChangeNumber.class);
                startActivity(intent);
            }
        });


    }
}