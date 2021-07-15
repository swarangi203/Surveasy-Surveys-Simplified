package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Graph0 extends AppCompatActivity {
    String qStatement,surveyName;
    FirebaseDatabase rootnode;
    DatabaseReference ref1,ref2;
    HashMap<String,Integer> map= new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph0);
        qStatement=getIntent().getStringExtra("name");
        surveyName=getIntent().getStringExtra("surveyName");
        rootnode=FirebaseDatabase.getInstance();
        ref1=rootnode.getReference("data").child(surveyName);
        ref2=rootnode.getReference("graph").child(surveyName).child(qStatement);
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot data: snapshot.getChildren())
                    {
                        if(data.exists())
                        {
                            for(DataSnapshot entryname:data.getChildren())
                            {
                                if(entryname.exists())
                                {
                                    for(DataSnapshot que:entryname.getChildren())
                                    {
                                        String Que=que.getKey();
                                        String ans=que.getValue(String.class);
                                        if(Que.equals(qStatement))
                                        {
                                            if(map.containsKey(ans))
                                            {
                                                Integer val=map.get(ans)+1;
                                                map.put(ans,val);
                                            }
                                            else
                                            {
                                                map.put(ans,1);
                                            }
                                            ref2.setValue(map);
                                        }


                                    }
                                }
                                else {
                                    Toast.makeText(Graph0.this, "Data for This Survey not exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(Graph0.this, "Data for This Survey not exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(Graph0.this, "Data for This Survey not exists", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Graph0.this, "Failed to read user UIDs", Toast.LENGTH_SHORT).show();
            }
        });

        Intent i=new Intent(Graph0.this, SelectGraphType.class);
        i.putExtra("name",surveyName);
        i.putExtra("qsts",qStatement);
        startActivity(i);
        finish();
    }
}