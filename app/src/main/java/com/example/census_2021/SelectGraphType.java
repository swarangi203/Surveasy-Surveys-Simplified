package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectGraphType extends AppCompatActivity implements Serializable {
    CardView resendEmail,changeEmail,userinfo,editinfo;
    String qStatement,surveyName;
    FirebaseDatabase rootnode;
    DatabaseReference ref1,ref2;
    ArrayList<DataEntry> data = new ArrayList<>();
    List<String> keylist = new ArrayList();
    List<Integer> valuelist = new ArrayList<>();
    Integer values=0;
    Long nochild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_graph_type);
        surveyName=getIntent().getStringExtra("name");
        qStatement=getIntent().getStringExtra("qsts");
        resendEmail=(CardView)findViewById(R.id.changepass);
        changeEmail=(CardView)findViewById(R.id.changeMob);
        userinfo=(CardView)findViewById(R.id.userinfoDisplay);
        editinfo=(CardView)findViewById(R.id.editInfo);
//Pie Chart
        editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String graph = "pie";
                Intent i=new Intent(SelectGraphType.this,GraphActivity.class);
                i.putExtra("name",surveyName);
                i.putExtra("qsts",qStatement);
                i.putExtra("graph", graph);
                startActivity(i);
            }
        });
        //Bar Graph
        userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String graph = "bar";
                Intent i=new Intent(SelectGraphType.this,GraphActivity.class);
                i.putExtra("name",surveyName);
                i.putExtra("qsts",qStatement);
                i.putExtra("graph", graph);
                startActivity(i);
            }
        });
        //
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String graph = "column";
                Intent i=new Intent(SelectGraphType.this,GraphActivity.class);
                i.putExtra("name",surveyName);
                i.putExtra("qsts",qStatement);
                i.putExtra("graph", graph);
                startActivity(i);
            }
        });
        resendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String graph = "line";
                Intent i=new Intent(SelectGraphType.this,GraphActivity.class);
                i.putExtra("name",surveyName);
                i.putExtra("qsts",qStatement);
                i.putExtra("graph", graph);
                startActivity(i);
            }
        });
    }
}