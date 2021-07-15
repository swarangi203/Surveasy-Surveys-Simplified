package com.example.census_2021;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Add_questions extends AppCompatActivity {

  String survey_name;
  LinearLayout optionLayout;
  LinearLayout addOptionRadio,removeOption,addOptionTextBox;
  Button nextQue,submitSurvey;
  Integer i=1;
  int textcount=0;
  int textinputid=-1;
  FirebaseAuth mFirebaseAuth;
  FirebaseDatabase rootnode;
  DatabaseReference reference;
  TextView namedisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);
        mFirebaseAuth = FirebaseAuth.getInstance();
        String uID=getIntent().getStringExtra("uid").toString();
        survey_name  = getIntent().getStringExtra("survey_name").toString();
        optionLayout=(LinearLayout)findViewById(R.id.optionlayout);
        addOptionRadio=(LinearLayout)findViewById(R.id.button1);
        removeOption=(LinearLayout)findViewById(R.id.button5);
        nextQue=(Button)findViewById(R.id.button3);
        namedisplay=(TextView)findViewById(R.id.SurveyNameText);
        namedisplay.setText(survey_name);
        namedisplay.setGravity(Gravity.CENTER);
        submitSurvey=(Button)findViewById(R.id.button4);
        addOptionTextBox=(LinearLayout)findViewById(R.id.button2);
        addOptionRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=new TextView(getApplicationContext());
                textView.setPadding(0,5,0,0);
                textView.setText("Option"+i);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18f);
                textView.setTextColor(Color.parseColor("#FF2E096A"));
                EditText edittext=new EditText(getApplicationContext());
                edittext.setHint("Enter Option"+i);
                edittext.requestFocus();
                edittext.setId(i);
                textView.setId((i*1000));
                i++;
                optionLayout.addView(textView);
                 optionLayout.addView(edittext);
            }
        });

        removeOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i>1) {
                    i--;
                    View delview = (View) findViewById(i);
                    ((ViewGroup) delview.getParent()).removeView(delview);
                    delview = (View) findViewById(i * 1000);
                    ((ViewGroup) delview.getParent()).removeView(delview);
                    if(i==textinputid)
                    {
                        textcount--;
                    }
                }
                else {
                    Toast.makeText(Add_questions.this, " No Options To delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addOptionTextBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textcount==0) {
                    TextView textView = new TextView(getApplicationContext());
                    textView.setPadding(0, 5, 0, 0);
                    textView.setText("Option" + i);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                    textView.setTextColor(Color.parseColor("#FF2E096A"));
                    EditText edittext = new EditText(getApplicationContext());
                    edittext.setText("InputFromUser");
                    edittext.requestFocus();
                    edittext.setFocusable(false);
                    edittext.setEnabled(false);
                    edittext.setClickable(false);
                    edittext.setId(i);
                    textinputid=i;
                    textcount++;
                    textView.setId((i * 1000));
                    i++;
                    optionLayout.addView(textView);
                    optionLayout.addView(edittext);
                }
                else
                {
                    Toast.makeText(Add_questions.this, "Only one option with text input can be added ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        submitSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextInputLayout questsLayout=(TextInputLayout)findViewById(R.id.editTextTextEmail);
                String queSts=questsLayout.getEditText().getText().toString();
                questsLayout.setError(null);
                int j=0;
                String arr[]=new String[i-1];
                for(j=0;j<i-1;j++)
                {
                    EditText text=(EditText)findViewById((j+1));
                    arr[j]=text.getText().toString();
                    text.setError(null);
                }
                if(queSts.isEmpty())
                {
                    questsLayout.setError("Please Enter a Question Statement");
                    questsLayout.requestFocus();
                }
                else if(i==1)
                {
                    Toast.makeText(Add_questions.this, "No options are present ..Add a option ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!arr[0].equals("InputFromUser")&&i==2)
                        Toast.makeText(Add_questions.this, "At at least two options..", Toast.LENGTH_SHORT).show();
                    else
                    {
                        for(j=0;j<i-1;j++)
                        {
                            EditText text=(EditText)findViewById((j+1));
                            arr[j]=text.getText().toString();
                            if(arr[j].isEmpty())
                            {
                                text.setError("Please Enter a Option Value");
                                text.requestFocus();
                                break;
                            }
                        }
                        if(j==i-1||(arr[0].equals("InputFromUser")&&i==2))
                        {
                            List<String> arrList = new ArrayList<>();
                            for(j=0;j<i-1;j++)
                            {
                                arrList.add(arr[j]);
                            }
                            AlertDialog alertDialog = new AlertDialog.Builder(Add_questions.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setCancelable(true);
                            alertDialog.setMessage("Do you want Submit Survey? click yes to confirm ");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            rootnode = FirebaseDatabase.getInstance();
                                            reference = rootnode.getReference("surveys");
                                            reference.child(survey_name).child(queSts).setValue(arrList);
                                            Toast.makeText(Add_questions.this, "Survey Created successfully..", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Add_questions.this, HomeActivity.class);
                                            intent.putExtra("uid",uID);
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
//                            rootnode = FirebaseDatabase.getInstance();
//                            reference = rootnode.getReference("surveys");
//                            reference.child(survey_name).child(queSts).setValue(arrList);
//                            Toast.makeText(Add_questions.this, "Question Added Successfully", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(Add_questions.this, HomeActivity.class);
//                            intent.putExtra("survey_name",survey_name);
//                            intent.putExtra("uid",uID);
//                            startActivity(intent);
//                            finish();

                        }
                    }
                }


            }
        });

        nextQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout questsLayout=(TextInputLayout)findViewById(R.id.editTextTextEmail);
                String queSts=questsLayout.getEditText().getText().toString();
                questsLayout.setError(null);
                int j=0;
                String[] arr =new String[i-1];
                for(j=0;j<i-1;j++)
                {
                    EditText text=(EditText)findViewById((j+1));
                    arr[j]=text.getText().toString();
                    text.setError(null);
                }
                if(queSts.isEmpty())
                {
                    questsLayout.setError("Please Enter a Question Statement");
                    questsLayout.requestFocus();
                }
                else if(i==1)
                {
                    Toast.makeText(Add_questions.this, "No options are present ..Add a option ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(!arr[0].equals("InputFromUser")&&i==2)
                       Toast.makeText(Add_questions.this, "At at least two options..", Toast.LENGTH_SHORT).show();
                    else
                    {
                        for(j=0;j<i-1;j++)
                        {
                            EditText text=(EditText)findViewById((j+1));
                            arr[j]=text.getText().toString();
                            if(arr[j].isEmpty())
                            {
                                text.setError("Please Enter a Option Value");
                                text.requestFocus();
                                break;
                            }
                        }
                        if(j==i-1||(arr[0].equals("InputFromUser")&&i==2))
                        {
                            List<String> arrList = new ArrayList<>();
                            for(j=0;j<i-1;j++)
                            {
                               arrList.add(arr[j]);
                            }
                            AlertDialog alertDialog = new AlertDialog.Builder(Add_questions.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setCancelable(true);
                            alertDialog.setMessage("Do you want to add new Question? click yes to confirm ");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            rootnode = FirebaseDatabase.getInstance();
                                            reference = rootnode.getReference("surveys");
                                            reference.child(survey_name).child(queSts).setValue(arrList);
                                            Toast.makeText(Add_questions.this, "Question Added Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Add_questions.this, Add_questions.class);
                                            intent.putExtra("survey_name",survey_name);
                                            intent.putExtra("uid",uID);
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
                    }
                }


            }
        });
    }
}