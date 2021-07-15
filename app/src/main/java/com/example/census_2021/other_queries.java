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

public class other_queries extends AppCompatActivity {

    String uid;
    List<String> hashlist;
    LinearLayout recyclerView;
    FirebaseDatabase rootnode;
    DatabaseReference reference, ref2, reference3, reference2, ref;
    Integer integer = 1;
    RadioGroup grp;
    Boolean aBoolean = false;
    private MyAdapter adapter;
    private ArrayList<Userqueries> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_queries);
        hashlist = new ArrayList<>();
        rootnode = FirebaseDatabase.getInstance();

        reference = FirebaseDatabase.getInstance().getReference("Queries").child("OtherQueries");
        recyclerView = findViewById(R.id.mainlayout);
        reference3 = FirebaseDatabase.getInstance().getReference("Queries").child("OtherQueries");
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        if (dataSnapshot1.exists()) {
                            String uid = dataSnapshot1.getKey();
                            // Toast.makeText(other_queries.this, uid, Toast.LENGTH_SHORT).show();
                            ref = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("name");
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot abcd) {
                                    String name = abcd.getValue(String.class);
                                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                        if (dataSnapshot2.exists()) {
                                            Boolean b = false;
                                            aBoolean = false;

                                            String hash = dataSnapshot2.getKey();
                                            Userqueries query = dataSnapshot2.getValue(Userqueries.class);
                                            if(query.getStatus().equals("unsolved")) {
                                                LinearLayout quelayout = new LinearLayout(getApplicationContext());
                                                quelayout.setOrientation(LinearLayout.VERTICAL);
                                                quelayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                                TextView textView = new TextView(getApplicationContext());
                                                textView.setPadding(10, 10, 10, 30);
                                                textView.setText("Name: " + name);
                                                textView.setTextSize(18);
                                                recyclerView.addView(quelayout);
                                                quelayout.addView(textView);
                                                if (!query.equals(null)) {
                                                    if (!query.getQtitle().equals("")) {
                                                        TextView key = new TextView(getApplicationContext());
                                                        key.setText("Title");
                                                        key.setPadding(10, 10, 10, 20);
                                                        String title = query.getTitle();
                                                        EditText value = new EditText(getApplicationContext());
                                                        value.setText(title.toString());
                                                        value.setPadding(10, 10, 10, 20);
                                                        value.setClickable(false);
                                                        value.setFocusable(false);
                                                        value.setTextIsSelectable(false);
                                                        value.setClickable(false);
                                                        value.setFocusable(false);
                                                        value.setCursorVisible(false);
                                                        LinearLayout layout = new LinearLayout(getApplicationContext());
                                                        layout.setOrientation(LinearLayout.VERTICAL);
                                                        quelayout.addView(layout);
                                                        layout.addView(key);
                                                        layout.addView(value);
                                                    }
                                                    if (!query.getQuery().equals("")) {
                                                        TextView key = new TextView(getApplicationContext());
                                                        key.setText("Query");
                                                        key.setPadding(10, 10, 10, 20);
                                                        String title = query.getQuery();
                                                        EditText value = new EditText(getApplicationContext());
                                                        value.setText(title.toString());
                                                        value.setPadding(10, 10, 10, 20);
                                                        value.setClickable(false);
                                                        value.setFocusable(false);
                                                        value.setTextIsSelectable(false);
                                                        value.setClickable(false);
                                                        value.setFocusable(false);
                                                        value.setCursorVisible(false);
                                                        LinearLayout layout = new LinearLayout(getApplicationContext());
                                                        layout.setOrientation(LinearLayout.VERTICAL);
                                                        quelayout.addView(layout);
                                                        layout.addView(key);
                                                        layout.addView(value);
                                                    }
                                                    if (!query.getStatus().equals("")) {
                                                        TextView key = new TextView(getApplicationContext());
                                                        key.setText("Status");
                                                        key.setPadding(10, 10, 10, 20);
                                                        RadioButton solved = new RadioButton(getApplicationContext());
                                                        solved.setText("solved");
                                                        solved.setId(integer++);
                                                        LinearLayout layout = new LinearLayout(getApplicationContext());
                                                        layout.setOrientation(LinearLayout.VERTICAL);
                                                        quelayout.addView(layout);
                                                        layout.addView(key);
                                                        layout.addView(solved);
                                                        solved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                            @Override
                                                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                                AlertDialog alertDialog = new AlertDialog.Builder(other_queries.this).create();
                                                                alertDialog.setTitle("Alert");
                                                                alertDialog.setCancelable(true);
                                                                alertDialog.setMessage("Solved the Query? This query will be removed !. \n");
                                                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                                                        new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                DatabaseReference reference1 = dataSnapshot2.child("status").getRef();
                                                                                reference1.setValue("solved");
                                                                                recyclerView.removeView(quelayout);

                                                                            }
                                                                        });

                                                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                                                                        new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                dialog.dismiss();
                                                                                finish();
                                                                            }
                                                                        });
                                                                alertDialog.show();

                                                            }
                                                        });

                                                    }
                                                    TextView key = new TextView(getApplicationContext());
                                                    key.setText("Submit");
                                                    key.setPadding(10, 10, 10, 20);
                                                    Button submit = new Button(getApplicationContext());
                                                    submit.setText("Submit");
                                                    submit.setBackgroundColor(Color.parseColor("#FF871DB6"));
                                                    submit.setTextColor(Color.parseColor("#FFFFFF"));
                                                    EditText value = new EditText(getApplicationContext());
                                                    value.setText(query.getFeedback().toString());
                                                    value.setPadding(10, 10, 10, 20);
                                                    LinearLayout layout = new LinearLayout(getApplicationContext());
                                                    layout.setOrientation(LinearLayout.VERTICAL);
                                                    quelayout.addView(layout);
                                                    layout.addView(key);
                                                    layout.addView(value);
                                                    layout.addView(submit);
                                                    submit.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            if (value.getText().toString().equals("")) {
                                                                Toast.makeText(other_queries.this, "No Feedback Provided", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                
                                                                DatabaseReference reference = dataSnapshot2.child("feedback").getRef();
                                                                reference.setValue(value.getText().toString());

                                                            }

                                                        }
                                                    });
                                                }
                                                LinearLayout layoute = new LinearLayout(getApplicationContext());
                                                layoute.setOrientation(LinearLayout.VERTICAL);
                                                layoute.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 240));
                                                quelayout.addView(layoute);
                                            }
                                        }

//                                         }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}










//                                      /*
//
//                                             for(DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren())
//                                             {
//
//                                                 if(dataSnapshot3.exists())
//                                                 {
//                                                     grp = new RadioGroup(getApplicationContext());
//                                                     TextView key = new TextView(getApplicationContext());
//                                                     key.setText(dataSnapshot3.getKey());
//                                                     key.setPadding(10, 10, 10, 20);
//                                                     if(!(dataSnapshot3.getKey().toString().equals("feedback")  || dataSnapshot3.getKey().toString().equals("status")))
//                                                     {
//                                                         EditText value = new EditText(getApplicationContext());
//                                                         value.setText(dataSnapshot3.getValue().toString());
//                                                         value.setPadding(10, 10, 10, 20);
//                                                         value.setClickable(false);
//                                                         value.setFocusable(false);
//                                                         value.setTextIsSelectable(false);
//                                                         value.setClickable(false);
//                                                         value.setFocusable(false);
//                                                         value.setCursorVisible(false);
//                                                         LinearLayout layout = new LinearLayout(getApplicationContext());
//                                                         layout.setOrientation(LinearLayout.VERTICAL);
//                                                         quelayout.addView(layout);
//                                                         layout.addView(key);
//                                                         layout.addView(value);
//                                                     }
//                                                     else  if (dataSnapshot3.getKey().toString().equals("feedback")) {
//                                                         Button submit = new Button(getApplicationContext());
//                                                         submit.setText("Submit");
//                                                         submit.setBackgroundColor(Color.parseColor("#FF871DB6"));
//                                                         submit.setTextColor(Color.parseColor("#FFFFFF"));
//                                                         EditText value = new EditText(getApplicationContext());
//                                                         value.setText(dataSnapshot3.getValue().toString());
//                                                         value.setPadding(10, 10, 10, 20);
//                                                         LinearLayout layout = new LinearLayout(getApplicationContext());
//                                                         layout.setOrientation(LinearLayout.VERTICAL);
//                                                         quelayout.addView(layout);
//                                                         layout.addView(key);
//                                                         layout.addView(value);
//                                                         layout.addView(submit);
//                                                         submit.setOnClickListener(new View.OnClickListener() {
//                                                             @Override
//                                                             public void onClick(View view) {
//                                                                 if (value.getText().toString().equals("")) {
//                                                                     Toast.makeText(other_queries.this, "No Feedback Provided", Toast.LENGTH_SHORT).show();
//                                                                 } else {
//                                                                     DatabaseReference reference = dataSnapshot3.getRef();
//                                                                     reference.setValue(value.getText().toString());
//                                                                 }
//
//                                                             }
//                                                         });
//                                                     } else if (dataSnapshot3.getKey().toString().equals("status")) {
//
//                                                         RadioButton solved = new RadioButton(getApplicationContext());
//                                                         solved.setText("solved");
//                                                         solved.setId(integer++);
//                                                         LinearLayout layout = new LinearLayout(getApplicationContext());
//                                                         layout.setOrientation(LinearLayout.VERTICAL);
//                                                         quelayout.addView(layout);
//                                                         layout.addView(key);
//                                                         layout.addView(solved);
//                                                         solved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                                                             @Override
//                                                             public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                                                 AlertDialog alertDialog = new AlertDialog.Builder(other_queries.this).create();
//                                                                 alertDialog.setTitle("Alert");
//                                                                 alertDialog.setCancelable(true);
//                                                                 alertDialog.setMessage("Solved the Query? This query will be removed !. \n");
//                                                                 alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
//                                                                         new DialogInterface.OnClickListener() {
//                                                                             public void onClick(DialogInterface dialog, int which) {
//                                                                                 DatabaseReference reference = dataSnapshot3.getRef();
//                                                                                 reference.setValue(solved.getText().toString());
//                                                                                 recyclerView.removeView(quelayout);
//                                                                                 reference2=FirebaseDatabase.getInstance().getReference().child("Queries").child("OtherQueries");
//                                                                                 reference2.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                     @Override
//                                                                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                                                         if(snapshot.exists()) {
//                                                                                             for (DataSnapshot dataSnapshot : snapshot.getChildren())
//                                                                                             {
//                                                                                                 String uid = dataSnapshot.getKey().toString();
//                                                                                                 // Toast.makeText(other_queries.this, uid, Toast.LENGTH_SHORT).show();
//                                                                                                 ref2 = reference2.child(uid).child(hash);
//                                                                                                 ref2.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                     @Override
//                                                                                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                                                                         DatabaseReference reference1 = snapshot.getRef();
//                                                                                                         reference1.removeValue();
//                                                                                                         //  Toast.makeText(other_queries.this, snapshot.getKey(), Toast.LENGTH_SHORT).show();
//                                                                                                     }
//
//                                                                                                     @Override
//                                                                                                     public void onCancelled(@NonNull DatabaseError error) {
//
//                                                                                                     }
//                                                                                                 });
//                                                                                             }
//                                                                                         }
//                                                                                     }
//
//                                                                                     @Override
//                                                                                     public void onCancelled(@NonNull DatabaseError error) {
//
//                                                                                     }
//                                                                                 });
//                                                                             }
//                                                                         });
//
//                                                                 alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
//                                                                         new DialogInterface.OnClickListener() {
//                                                                             public void onClick(DialogInterface dialog, int which) {
//                                                                                 dialog.dismiss();
//                                                                                 finish();
//                                                                             }
//                                                                         });
//                                                                 alertDialog.show();
//
//                                                             }
//                                                         });
//
//                                                     }
//
//                                                 }
//                                             }
//                                             LinearLayout layoute = new LinearLayout(getApplicationContext());
//                                             layoute.setOrientation(LinearLayout.VERTICAL);
//                                             layoute.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 240));
//                                             quelayout.addView(layoute);
//                                         }
//                                     }
//                                 }
//
//                                 @Override
//                                 public void onCancelled(@NonNull DatabaseError error) {
//
//                                 }
//                             });
//
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//}   */