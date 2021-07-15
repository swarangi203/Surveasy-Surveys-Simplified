package com.example.census_2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;

public class Add_survey extends AppCompatActivity {


    TextInputLayout name;
    Button btn;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_survey);
        String uID=getIntent().getStringExtra("uid").toString();
        bar=(ProgressBar) findViewById(R.id.progressBar2);
        btn = (Button) findViewById(R.id.img2);
        name = (TextInputLayout)findViewById(R.id.editTextTextSurveyName);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setError(null);
                bar.setVisibility(View.VISIBLE);
                String survey_name = name.getEditText().getText().toString();
                if (survey_name.isEmpty()) {
                    bar.setVisibility(View.INVISIBLE);
                    name.setError("Please Enter an Valid Name");
                    name.requestFocus();
                }
                else
                {
                    bar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(Add_survey.this, Add_questions.class);
                    intent.putExtra("survey_name",survey_name);
                    intent.putExtra("uid",uID);
                    startActivity(intent);
                    finish();
                }
            }

        });

    }
}