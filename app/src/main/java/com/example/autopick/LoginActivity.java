package com.example.autopick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText username,password;
    private TextView signUp;
    private Button login;
    private ProgressBar loading;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        signUp=findViewById(R.id.signUp);
        login=findViewById(R.id.signUp);
        loading=findViewById(R.id.loading);
        mAuth=FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.VISIBLE);
                String strUser=username.getText().toString();
                String strPass=username.getText().toString();
                if (TextUtils.isEmpty(strUser) && TextUtils.isEmpty(strPass)){
                    Toast.makeText(LoginActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    return;
                }else {
                    mAuth.signInWithEmailAndPassword(strUser,strPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loading.setVisibility(View.GONE);
                                if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                                    Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                    Intent i= new Intent(LoginActivity.this,LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Please Verify the mail", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });

    }


    public void change(View view){
        Intent i= new Intent(this,RegistrationActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            if(user.isEmailVerified()){
                Intent i=new Intent(LoginActivity.this,ListActivity.class);
                Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
                startActivity(i);
                this.finish();
            }
            else {
                Toast.makeText(this, "Please Verify the email", Toast.LENGTH_SHORT).show();
            }
        }
    }
}