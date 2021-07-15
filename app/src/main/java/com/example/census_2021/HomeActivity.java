package com.example.census_2021;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    Task<Void> ref1;
    DatabaseReference ref2;
    CardView logout,signUpBtn,signUpadmin,addsurvey,deleteSurvey,editSurvey,deleteUser,deleteAdmin,settings,changeData,userQueries, analyze;
    String uid;
    TextView nameView;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nameView=(TextView)findViewById(R.id.textView2);

        uid=getIntent().getStringExtra("uid").toString();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users").child(uid);
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
        logout =(CardView)findViewById(R.id.logout);
        addsurvey=(CardView)findViewById(R.id.changepass);
        signUpBtn= (CardView)findViewById(R.id.adduser);
        signUpadmin=(CardView)findViewById(R.id.addadmin);
        deleteSurvey=(CardView)findViewById(R.id.changeMob);
        editSurvey=(CardView)findViewById(R.id.editInfo);
        deleteUser=(CardView)findViewById(R.id.deleteuser);
        deleteAdmin=(CardView)findViewById(R.id.deleteadmin);
        settings=(CardView)findViewById(R.id.settings);
        changeData=(CardView)findViewById(R.id.edituser);
        userQueries=(CardView)findViewById(R.id.view_query);
        analyze=(CardView)findViewById(R.id.analyze);
        userQueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, view_query_section.class);
                startActivity(intent);
            }
        });
        changeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, EditUserInfo.class);
                startActivity(intent);
            }
        });
        deleteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, manageAdmin.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserSettings.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        editSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SelectSurvey.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, manageUser.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        deleteSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, deleteSurvey.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        addsurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Add_survey.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signUpadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SignUpAdmin.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });
        analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, selectSurveyforInfo.class);
                intent.putExtra("uid",uid);
                startActivity(intent);

            }
        });
    }
}