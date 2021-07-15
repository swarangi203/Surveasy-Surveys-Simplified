package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class deleteSurvey extends AppCompatActivity {
    FirebaseDatabase rootnode;
    DatabaseReference reference,refdelete;
    ArrayList<String> SurveyNames = new ArrayList<>();
    LinearLayout  surveyListLayout;
    String uID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_survey);
         uID=getIntent().getStringExtra("uid").toString();
        surveyListLayout=(LinearLayout)findViewById(R.id.xyz);
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("surveys");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CollectSurveyNames((Map<String,Object>) snapshot.getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(deleteSurvey.this, "Error Fetching Survey Names..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CollectSurveyNames(Map<String, Object> Surveys) {
        for (Map.Entry<String, Object> entry : Surveys.entrySet())
        {
            SurveyNames.add(entry.getKey());
            String Name=entry.getKey();
            Button b=new Button(getApplicationContext());
            b.setText(Name);
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP,20f);
            b.setTextColor(Color.parseColor("#FFFFFF"));
            b.setBackgroundColor(Color.parseColor("#FF871DB6"));
            b.setGravity(Gravity.CENTER_HORIZONTAL);
            b.setPadding(5,50,5,50);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(deleteSurvey.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setCancelable(true);
                    alertDialog.setMessage("Do you want to permenantly delete "+Name+" Survey?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    refdelete=rootnode.getReference("surveys").child(Name);
                                    refdelete.removeValue();
                                    Toast.makeText(deleteSurvey.this, Name+" Survey Deleted Successfully..", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(deleteSurvey.this, HomeActivity.class);
                                    intent.putExtra("uid",uID);
                                    dialog.dismiss();
                                    startActivity(intent);
                                    finish();

                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
            });
            surveyListLayout.addView(b);
        }

    }
}