package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class EditSurvey extends AppCompatActivity {

    String uid,survey_name,question;
    DatabaseReference reference;
    LinearLayout queListLayout;
    Button addQue;
    TextView nameDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_survey);
        queListLayout=(LinearLayout)findViewById(R.id.xyz);
        addQue=(Button)findViewById(R.id.addQue);
        uid=getIntent().getStringExtra("uid").toString();
        survey_name=getIntent().getStringExtra("name").toString();
        nameDisplay=(TextView)findViewById(R.id.SurveyNameText);
        nameDisplay.setText(survey_name);
        reference=FirebaseDatabase.getInstance().getReference("surveys").child(survey_name);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CollectQuestions((Map<String,Object>) snapshot.getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditSurvey.this, "Error Fetching Survey Names..", Toast.LENGTH_SHORT).show();
            }
        });
        addQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditSurvey.this, Add_questions.class);
                                    intent.putExtra("uid", uid);
                                    intent.putExtra("survey_name", survey_name);
                                    startActivity(intent);
                                    finish();
            }
        });
    }
    private void CollectQuestions(Map<String, Object> questions) {
        for (Map.Entry<String, Object> entry : questions.entrySet()) {
            String que = entry.getKey();
            Button b = new Button(getApplicationContext());
            b.setText(que);
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
            b.setTextColor(Color.parseColor("#FFFFFF"));
            b.setBackgroundColor(Color.parseColor("#FF871DB6"));
            b.setGravity(Gravity.CENTER_HORIZONTAL);
            b.setPadding(5, 50, 5, 50);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(EditSurvey.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setCancelable(true);
                    alertDialog.setMessage("Do you want to edit/delete question:-\n \n" + que);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(EditSurvey.this, EditQuestion.class);
                                    intent.putExtra("uid", uid);
                                    intent.putExtra("survey_name", survey_name);
                                    intent.putExtra("Question_Statement", que);
                                    dialog.dismiss();
                                    startActivity(intent);

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
            queListLayout.addView(b);
        }
    }
    }
