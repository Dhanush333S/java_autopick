package com.example.autopick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {

    ImageView signal;
    ImageView about,live;
    final Handler handler=new Handler();
    Runnable compare=new Runnable() {
        @Override
        public void run() {
            signal.setColorFilter(Color.RED);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String str=bundle.getString("name");


        signal=findViewById(R.id.signal);
        about=findViewById(R.id.about);
        live=findViewById(R.id.live);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(str).child("signal");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               signal.setColorFilter(Color.GREEN);
               handler.removeCallbacks(compare);
                handler.postDelayed(compare,6000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard=new Intent(Dashboard.this,MainActivity.class);
                dashboard.putExtra("name",str);
                startActivity(dashboard);
            }
        });

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashboard=new Intent(Dashboard.this,LiveActivity.class);
                dashboard.putExtra("name",str);
                startActivity(dashboard);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(compare);
    }

}