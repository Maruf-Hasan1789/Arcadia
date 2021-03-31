package com.example.arcadia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_activity extends AppCompatActivity {

    EditText username,userpass,usermail;
    Button signupbtn;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);
        usermail=findViewById(R.id.user_mail_sign_up);
        username=findViewById(R.id.username_signup);
        userpass=findViewById(R.id.user_pass_sign_up);
        signupbtn=findViewById(R.id.signup_button);
        progressBar=findViewById(R.id.signup_progressbar);
        firebaseAuth=FirebaseAuth.getInstance();



        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=usermail.getText().toString().trim();
                String pass=userpass.getText().toString().trim();
                String name=username.getText().toString().trim();
                if(TextUtils.isEmpty(mail))
                {
                    usermail.setError("Email is required");
                    return ;
                }
                if(TextUtils.isEmpty(name))
                {
                    username.setError("User Name is required");
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    userpass.setError("User password is required");
                    return ;
                }
                if(pass.length()<6)
                {
                    userpass.setError("Password must be at least 6 characters long");
                    return ;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(SignUp_activity.this,"account created",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(SignUp_activity.this,"Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    }
                });
            }
        });

    }
}