package com.example.autopick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView deviceName,deviceID,deviceAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deviceName=findViewById(R.id.deviceName);
        deviceID=findViewById(R.id.deviceID);
        deviceAbout=findViewById(R.id.deviceAbout);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String str=bundle.getString("name");
        deviceName.setText(str);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String username=user.getUid();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(username).child(str);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                deviceID.setText(snapshot.child("deviceID").getValue().toString());
                deviceAbout.setText(snapshot.child("about").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}