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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText username,password,confirmPassword;
    private Button signUp;
    private Button login;
    private ProgressBar loading;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirmPassword);
        signUp=findViewById(R.id.signUp);
        loading=findViewById(R.id.loading);
        login=findViewById(R.id.login);
        mAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.VISIBLE);
                String strUser=username.getText().toString();
                String strPass=username.getText().toString();
                String strConPass=username.getText().toString();
                if(!strPass.equals(strConPass)){
                    Toast.makeText(RegistrationActivity.this, "Check your Password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(strUser) && TextUtils.isEmpty(strPass)) {
                    Toast.makeText(RegistrationActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.createUserWithEmailAndPassword(strUser,strPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loading.setVisibility(View.GONE);
                                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegistrationActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                            Intent i=new Intent(RegistrationActivity.this,LoginActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(RegistrationActivity.this, "Verification mail sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }else{
                                loading.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}