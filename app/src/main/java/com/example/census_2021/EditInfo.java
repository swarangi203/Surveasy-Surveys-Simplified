package com.example.census_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
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

public class EditInfo extends AppCompatActivity {
    TextInputLayout  name,monNo;
    String mail,uID;
    Switch aSwitch;
    Button btn;
    boolean issettings;
    TextView nameView,text;
    DatabaseReference reference;
    FirebaseDatabase rootnode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uID=getIntent().getStringExtra("uid").toString();
        if(uID.equals(""))
        {
            uID=user.getUid().toString();
            issettings=true;
        }
        else {
            issettings=false;
        }
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
        text=(TextView)findViewById(R.id.textView2);
        name =(TextInputLayout)findViewById(R.id.editTextTextName);
        monNo=(TextInputLayout)findViewById(R.id.editTextTextMobileNo);
        aSwitch=(Switch)findViewById(R.id.switch2);
        Button btn=(Button)findViewById(R.id.img2);
        reference= FirebaseDatabase.getInstance().getReference("users");
        reference=reference.child(uID);

        if(!issettings)
        {
            aSwitch.setVisibility(View.INVISIBLE);
            aSwitch.setEnabled(false);
            text.setVisibility(View.INVISIBLE);
        }
        reference.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username=snapshot.getValue(String.class);
                name.getEditText().setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditInfo.this, "Failed to get Name ", Toast.LENGTH_SHORT).show();
            }
        });

        reference.child("mobile_No").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mobile_No=snapshot.getValue(String.class);
                mobile_No=mobile_No.substring(3);
                monNo.getEditText().setText(mobile_No);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditInfo.this, "Failed to get Mobile Number ", Toast.LENGTH_SHORT).show();
            }
        });

      btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          name.setError(null);
          monNo.setError(null);
          String tempmob = monNo.getEditText().getText().toString();
          String tempname = name.getEditText().getText().toString();
          if (tempname.isEmpty()) {
              name.setError("Please Enter a Name");
              name.requestFocus();
          }
          else if (tempmob.isEmpty()) {
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
                  Boolean isUser = aSwitch.isChecked();
                  if (isUser) {
                      String finalTempmob = tempmob;
                      rootnode.getReference("users").child(uID).child("state").addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String  state=snapshot.getValue(String.class);
                            if(state.equals("main"))
                            {
                                Toast.makeText(EditInfo.this, "Main Admin can not be user/surveyor", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                AlertDialog alertDialog = new AlertDialog.Builder(EditInfo.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setCancelable(true);
                                alertDialog.setMessage("Do you want to Change Account as User/Surveyor?\n");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                reference.child("mobile_No").setValue(finalTempmob);
                                                reference.child("name").setValue(tempname);
                                                reference.child("post").setValue("user");
                                                AlertDialog alertDialog = new AlertDialog.Builder(EditInfo.this).create();
                                                alertDialog.setTitle("Alert");
                                                alertDialog.setCancelable(false);
                                                alertDialog.setMessage("Changed Account..\n\nLog-in into User/Surveyor Application");
                                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                FirebaseAuth.getInstance().signOut();
                                                                finishAffinity();
                                                                System.exit(0);
                                                            }
                                                        });
                                                alertDialog.show();
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

                          @Override
                          public void onCancelled(@NonNull DatabaseError error) {
                              Toast.makeText(EditInfo.this, "Failed to get admin status ", Toast.LENGTH_SHORT).show();
                          }
                      });

                  } else
                  {
                      reference.child("mobile_No").setValue(tempmob);
                      reference.child("name").setValue(tempname);
                      Toast.makeText(EditInfo.this, "Data Changed Successfully", Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(EditInfo.this, EditInfo.class);
                      if(issettings)
                      {
                          uID="";
                      }
                      intent.putExtra("uid",uID);
                      startActivity(intent);
                      finish();
                  }

              } else {
                  monNo.setError("Mobile No.Should Contain Only Digits");
                  monNo.requestFocus();
              }
          }
      }
  });

    }
}