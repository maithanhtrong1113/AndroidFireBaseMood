package com.example.lab_08_android_firebase_mood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvRegister;
    Button btnSignIn;
    EditText txtEmail, txtPassword;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvRegister = findViewById(R.id.tvRegister);
        btnSignIn = findViewById(R.id.btnSignInM);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        progressBar = findViewById(R.id.pgrSignin);
        progressBar.setVisibility(View.GONE);

        tvRegister.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvRegister:{
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                SignInActivity.this.startActivity(intent);
                break;
            }
            case R.id.btnSignInM:{
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                        Toast.makeText(SignInActivity.this, "Cant't sign in", Toast.LENGTH_LONG).show();

                                } else {
                                    userId = firebaseAuth.getCurrentUser().getUid();
                                    Intent intent = new Intent(SignInActivity.this, MoodActivity.class);
                                    intent.putExtra("USERID", userId);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                break;
            }

        }

    }
}