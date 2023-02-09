package com.example.autopick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddActivity extends AppCompatActivity {
    EditText name,id,about;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name=findViewById(R.id.name);
        id=findViewById(R.id.id);
        about=findViewById(R.id.about);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strID=id.getText().toString();
                String strName=name.getText().toString();
                if(TextUtils.isEmpty(strID) && TextUtils.isEmpty(strName)){
                    Toast.makeText(AddActivity.this, "Fill Up the form", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    String username=user.getUid();
                    HashMap<String,Object> map= new HashMap<>();
                    map.put("deviceName",name.getText().toString());
                    map.put("deviceID",id.getText().toString());
                    map.put("about",about.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child(username).child(strName).updateChildren(map);
                    Intent i=new Intent(AddActivity.this,ListActivity.class);
                    startActivity(i);
                }

            }
        });

    }
}