package com.example.arcadia;

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

public class LogInActivity extends AppCompatActivity {

    EditText usermail,userpass;
    TextView textView;
    Button loginbutton,newaccount_btn;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        usermail=findViewById(R.id.user_mail_sign_in);
        userpass=findViewById(R.id.user_pass_login);
        loginbutton=findViewById(R.id.log_in_button);
        progressBar=findViewById(R.id.progressBar_login);
        newaccount_btn=findViewById(R.id.donthaveaccount_login);
        firebaseAuth=FirebaseAuth.getInstance();
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=usermail.getText().toString().trim();
                String pass=userpass.getText().toString().trim();
                if(TextUtils.isEmpty(mail))
                {
                    usermail.setError("Email is required");
                    return ;
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
               // progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LogInActivity.this,"Logged in succesfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(LogInActivity.this,"Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        newaccount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUp_activity.class));
            }
        });

    }
}