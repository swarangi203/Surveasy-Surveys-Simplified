package com.example.census_2021;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    TextInputLayout email, password,monNo,nameText,passwordRetype;
    ProgressBar bar;
    String mail ,pass,MobileNo,name,passRetype;
    Button btn;
    TextView signin;
    String uID;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    FirebaseUser muser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        uID=getIntent().getStringExtra("uid").toString();
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = (TextInputLayout)findViewById(R.id.editTextTextEmailAddress);
        password =(TextInputLayout)findViewById(R.id.editTextTextPassword);
        nameText =(TextInputLayout)findViewById(R.id.editTextTextName);
        monNo=(TextInputLayout)findViewById(R.id.editTextTextMobileNo);
        passwordRetype=(TextInputLayout)findViewById(R.id.editTextTextPasswordRetype);
        // signin = findViewById(R.id.textView);
        btn =(Button)findViewById(R.id.button);
        bar=(ProgressBar)findViewById(R.id.progressBar2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email.setError(null);
                password.setError(null);
                passwordRetype.setError(null);
                nameText.setError(null);
                monNo.setError(null);
                mail = email.getEditText().getText().toString();
                pass = password.getEditText().getText().toString();
                MobileNo = monNo.getEditText().getText().toString();
                name = nameText.getEditText().getText().toString();
                passRetype = passwordRetype.getEditText().getText().toString();
                if (mail.isEmpty()) {
                    email.setError("Please Enter an Email");
                    email.requestFocus();
                } else if (pass.isEmpty()) {
                    password.setError("Please Enter a Password");
                    password.requestFocus();
                } else if (mail.isEmpty() && pass.isEmpty()) {
                    email.setError("Please Enter an Email");
                    password.setError("Please Enter a Password");
                    email.requestFocus();
                    password.requestFocus();
                }
                else if (pass.length()<6) {
                    password.setError("Password Should Be Greater Than 5 Char.");
                    password.requestFocus();
                }
                else if (passRetype.isEmpty()) {
                    passwordRetype.setError("Please Retype Password");
                    passwordRetype.requestFocus();
                }
                else if (!passRetype.equals(pass)) {
                    passwordRetype.setError("Retype Password Did not match");
                    passwordRetype.requestFocus();
                }
                else if (name.isEmpty()) {
                    nameText.setError("Please Enter a Name");
                    nameText.requestFocus();
                } else if (MobileNo.isEmpty()) {
                    monNo.setError("Please Enter Mobile No");
                    monNo.requestFocus();
                } else if (MobileNo.length() != 10) {
                    monNo.setError(" Mobile No.Should Be 10 Digit");
                    monNo.requestFocus();
                } else {
                    String numbertemp = MobileNo;
                    int i = 0;
                    for (i = 0; i < numbertemp.length(); i++) {
                        if (!(numbertemp.charAt(i) >= '0' && numbertemp.charAt(i) <= '9')) {
                            break;
                        }
                    }
                    if (i == numbertemp.length()) {
                        signupmethod();
                    } else {
                        monNo.setError("Mobile No.Should Contain Only Digits");
                        monNo.requestFocus();
                    }
                }

            }
            public void signupmethod()
            {
                bar.setVisibility(View.VISIBLE);
                mFirebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUpActivity.this, "User Registration Unsuccessful..May Be Not a Valid Email Id", Toast.LENGTH_SHORT).show();
                        } else {
                            String mail = email.getEditText().getText().toString();
                            String pass = password.getEditText().getText().toString();
                            rootnode = FirebaseDatabase.getInstance();
                            reference = rootnode.getReference("users");
                            muser = mFirebaseAuth.getCurrentUser();
                            String uid = muser.getUid();
                            MobileNo="+91"+MobileNo;
                            UserHelperClass newuser = new UserHelperClass(name, MobileNo,"user");
                            reference.child(uid).setValue(newuser);
                            Toast.makeText(SignUpActivity.this, "User Registration Successful", Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            intent.putExtra("uid",uID);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }

        });
//        signin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}


