package com.example.census_2021;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePhoneNo extends AppCompatActivity {
    TextInputLayout monNo;
    String uID;
    Button btn;
    TextView nameView;
    DatabaseReference reference;
    FirebaseDatabase rootnode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_no);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uID=user.getUid().toString();
        btn=(Button)findViewById(R.id.img2) ;
        nameView=(TextView)findViewById(R.id.textView5);
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users").child(uID);
        nameView.setText("UserName");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass userdata=snapshot.getValue(UserHelperClass.class);
                nameView.setText(userdata.Name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        monNo=(TextInputLayout)findViewById(R.id.editTextTextMobileNo);
        reference= FirebaseDatabase.getInstance().getReference("users");
        reference=reference.child(uID);

        reference.child("mobile_No").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mobile_No=snapshot.getValue(String.class);
                mobile_No=mobile_No.substring(3);
                monNo.getEditText().setText(mobile_No);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChangePhoneNo.this, "Failed to get Mobile Number ", Toast.LENGTH_SHORT).show();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monNo.setError(null);
                String tempmob = monNo.getEditText().getText().toString();
                if (tempmob.isEmpty()) {
                    monNo.setError("Please Enter Mobile No");
                    monNo.requestFocus();
                } else if (tempmob.length() != 10) {
                    monNo.setError(" Mobile No.Should Be 10 Digit");
                    monNo.requestFocus();
                } else {
                    String numbertemp = tempmob;
                    int i = 0;
                    for (i = 0; i < numbertemp.length(); i++) {
                        if (!(numbertemp.charAt(i) >= '0' && numbertemp.charAt(i) <= '9')) {
                            break;
                        }
                    }
                    if (i == numbertemp.length()) {
                        tempmob="+91"+tempmob;
                            reference.child("mobile_No").setValue(tempmob);
                            Toast.makeText(ChangePhoneNo.this, "Data Changed Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePhoneNo.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                    } else {
                        monNo.setError("Mobile No.Should Contain Only Digits");
                        monNo.requestFocus();
                    }
                }
            }
        });

    }
}