package com.example.census_2021;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeNumber extends AppCompatActivity {
    LinearLayout recyclerView;
    FirebaseDatabase rootnode;
    DatabaseReference reference,ref2, reference3, reference2;
    Integer integer = 1;
    RadioGroup grp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_number);
        recyclerView=findViewById(R.id.mainlayout);
        reference=FirebaseDatabase.getInstance().getReference("Queries").child("Data_number_change_query");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        String uid = dataSnapshot.getKey();
                     //   Toast.makeText(ChangeNumber.this, uid, Toast.LENGTH_SHORT).show();
                        reference2 = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("name");
                        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                LinearLayout quelayout = new LinearLayout(getApplicationContext());
                                quelayout.setOrientation(LinearLayout.VERTICAL);
                                quelayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                               //  Toast.makeText(ChangeNumber.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                                recyclerView.addView(quelayout);
                                if(dataSnapshot.exists())
                                {
                                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                    {
                                        TextView textView = new TextView(getApplicationContext());
                                        textView.setPadding(10, 10, 10, 30);
                                        textView.setText("Name: "+ snapshot.getValue().toString());
                                        textView.setTextSize(18);
                                        EditText value = new EditText(getApplicationContext());
                                        value.setText(dataSnapshot1.getValue().toString());
                                        value.setPadding(10, 10, 10, 30);
                                        value.setClickable(false);
                                        value.setFocusable(false);
                                        value.setTextIsSelectable(false);
                                        value.setClickable(false);
                                        value.setFocusable(false);
                                        value.setCursorVisible(false);
                                        Button btn = new Button(getApplicationContext());
                                        btn.setText("Approve?");
                                        btn.setPadding(10, 10, 10, 10);
                                        btn.setBackgroundColor(Color.parseColor("#FF871DB6"));
                                        btn.setTextColor(Color.parseColor("#FFFFFF"));
                                        btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                DatabaseReference reference2 =  FirebaseDatabase.getInstance().getReference().child("users").child(dataSnapshot.getKey().toString());
                                                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        // Toast.makeText(data_changes_queries.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                                        for(DataSnapshot dataSnapshot2 : snapshot.getChildren())
                                                        {
                                                            if(dataSnapshot2.exists()) {
                                                                if (dataSnapshot2.getKey().toString().equals("mobile_No")) {
                                                                    DatabaseReference reference1 = dataSnapshot2.getRef();
                                                                    reference1.setValue(value.getText().toString());
                                                                    // Toast.makeText(ChangeNumber.this, dataSnapshot2.getValue().toString(), Toast.LENGTH_SHORT).show();
                                                                    Toast.makeText(ChangeNumber.this, "Number Changed Successfully", Toast.LENGTH_SHORT).show();
                                                                    reference.child(uid).removeValue();
                                                                    recyclerView.removeView(quelayout);

                                                                }
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                                //   reference.setValue(dataSnapshot1.getValue());
                                            }
                                        });
                                        LinearLayout layout = new LinearLayout(getApplicationContext());
                                        layout.setOrientation(LinearLayout.VERTICAL);
                                        quelayout.addView(layout);
                                        layout.addView(textView);
                                        layout.addView(value);
                                        layout.addView(btn);
                                        LinearLayout layoute = new LinearLayout(getApplicationContext());
                                        layoute.setOrientation(LinearLayout.VERTICAL);
                                        layoute.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 130));
                                        layout.addView(layoute);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}