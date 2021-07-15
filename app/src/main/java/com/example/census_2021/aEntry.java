package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class aEntry extends AppCompatActivity {
String SurveyName,UID,EntryName,answer;
TextView surveynaame,entryname;
    DatabaseReference reference, reference2, reference3;
    ScrollView scrollView;
    TextView nameDisplay, entrynametext, q;
    Integer integer = 1, i = 1, num = 0;
    LinearLayout mainLayout;
    Map<String,String> queans=new HashMap<String,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_entry);
        SurveyName = getIntent().getStringExtra("name");
        UID = getIntent().getStringExtra("uid");
        EntryName = getIntent().getStringExtra("entryName");
        surveynaame = (TextView) findViewById(R.id.SurveyNameText);
        surveynaame.setText(SurveyName);
        entryname = (TextView) findViewById(R.id.entrynametext);
        entryname.setText(EntryName);
        mainLayout = (LinearLayout) findViewById(R.id.mainlayout);
        reference = FirebaseDatabase.getInstance().getReference("data").child(SurveyName).child(UID).child(EntryName);    //reference to get question
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CollectQuestions((Map<String, Object>) snapshot.getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(aEntry.this, "Error Fetching Questions..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CollectQuestions(Map<String, Object> questions) {
        for (Map.Entry<String, Object> entry : questions.entrySet()) {
            String que = entry.getKey();
            EditText editText=new EditText(aEntry.this);
            editText.setHint(integer.toString()+". "+que);
            editText.setPadding(10, 40, 10, 40);
            editText.setTextSize(20f);
            editText.setId(integer);
            reference2 = FirebaseDatabase.getInstance().getReference("data").child(SurveyName).child(UID).child(EntryName).child(que).child("ans");
            reference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String ans = entry.getValue().toString();
                    int i = ans.length();
//                    answer = ans.substring(5,i-1) ;
                    queans.put(que, ans);
                    String text= integer.toString();
                    text=text+"."+que+"\n"+ ans;
                    editText.setText(text);
                    editText.setTextIsSelectable(false);
                    editText.setClickable(false);
                    editText.setFocusable(false);
                    editText.setCursorVisible(false);
                    integer++;
                    mainLayout.addView(editText);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }
}
